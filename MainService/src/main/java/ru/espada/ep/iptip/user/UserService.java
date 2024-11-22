package ru.espada.ep.iptip.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.espada.ep.iptip.course.CourseEntity;
import ru.espada.ep.iptip.course.CourseRepository;
import ru.espada.ep.iptip.course.CourseStatus;
import ru.espada.ep.iptip.course.user.CourseCustomerEntity;
import ru.espada.ep.iptip.course.user.CourseCustomerRepository;
import ru.espada.ep.iptip.s3.S3Service;
import ru.espada.ep.iptip.user.customer.CustomerEntity;
import ru.espada.ep.iptip.user.customer.CustomerRepository;
import ru.espada.ep.iptip.user.models.request.AddRoleRequest;
import ru.espada.ep.iptip.user.models.request.CreateCustomerRequest;
import ru.espada.ep.iptip.user.models.response.CustomerCourseResponse;
import ru.espada.ep.iptip.user.permission.UserPermissionService;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    private UserRepository userRepository;
    private PasswordEncoder bCryptPasswordEncoder;
    private CourseCustomerRepository courseCustomerRepository;
    private UserPermissionService userPermissionService;
    @Value("${users.page-size}")
    private int pageSize;
    private CustomerRepository customerRepository;
    private CourseRepository courseRepository;
    private S3Service s3Service;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    @Cacheable(value = "users", key = "'users'", unless = "#result == null")
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "users", key = "#page - 'users'", unless = "#result == null")
    public List<UserEntity> allUsers(int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return userRepository.findAll(pageable).toList();
    }

    @CacheEvict(value = "users", key = "'users'")
    public void saveUser(UserEntity user) {
        UserEntity userFromDB = userRepository.findByUsername(user.getUsername()).orElse(null);

        if (userFromDB != null) {
            throw new IllegalArgumentException("exception.user.user_exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "'users'")
    public void deleteUser(String deleteUserId) {
        UserEntity user = userRepository.findByUsername(deleteUserId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public List<UserEntity> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM UserEntity u WHERE u.id > :paramId", UserEntity.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public CustomerEntity getCustomer(String name) {
        return getUser(name).getCustomer();
    }

    public CustomerEntity getCustomer(Long id) {
        return getUser(id).getCustomer();
    }

    public boolean hasResponsibleCourse(UserEntity user, Long courseId) {
        if (user.getResponsibleCourses() == null) {
            return false;
        }
        return user.getResponsibleCourses().stream()
                .anyMatch(course -> course.getId().equals(courseId));
    }

    @Transactional
    public void createCustomer(Principal principal, CreateCustomerRequest createCustomerRequest) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!Objects.equals(user.getId(), createCustomerRequest.getUserId())) {
            throw new IllegalArgumentException("exception.user.only_owner");
        }
        if (user.getCustomer() != null) {
            throw new IllegalArgumentException("exception.user.customer_exists");
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setName(createCustomerRequest.getName());
        customer.setSurname(createCustomerRequest.getSurname());
        customer.setPhone(createCustomerRequest.getPhone());
        customer.setEmail(createCustomerRequest.getEmail());
        if (createCustomerRequest.getPatronymic() != null) {
            customer.setPatronymic(createCustomerRequest.getPatronymic());
        }
        if (createCustomerRequest.getBirthDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                customer.setBirthDate(formatter.parse(createCustomerRequest.getBirthDate()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        customerRepository.save(customer);
        user.setCustomer(customer);
        userRepository.save(user);
    }

    public List<CourseEntity> getResponsibleCourses(Principal principal) {
        UserEntity user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getResponsibleCourses().stream().toList();
    }

    @Cacheable(value = "courses", key = "#username", unless = "#result == null")
    @Transactional
    public List<CustomerCourseResponse> getCustomerCourses(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getCustomer() == null) {
            throw new IllegalArgumentException("exception.user.customer_not_found");
        }
        return user.getCustomer().getCourses().stream().map(CustomerCourseResponse::fromCourse).toList();
    }

    @CacheEvict(value = "courses", key = "#username")
    public void attachCourseToCustomer(String username, Long courseId, Long startTime, Long endTime) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getCustomer() == null) {
            throw new IllegalArgumentException("exception.user.customer_not_found");
        }
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("exception.course.not_found"));
        user.getCustomer().getCourses().add(course);
        CourseCustomerEntity courseCustomer = CourseCustomerEntity.builder()
                .course(course)
                .customer(user.getCustomer())
                .startTime(startTime)
                .endTime(endTime)
                .status(CourseStatus.IN_PROGRESS)
                .build();
        courseCustomerRepository.save(courseCustomer);
    }

    @CacheEvict(value = "courses", key = "#username")
    public void detachCourseFromCustomer(Principal principal, String username, Long courseId) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getCustomer() == null) {
            throw new IllegalArgumentException("exception.user.customer_not_found");
        }
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("exception.course.not_found"));
        user.getCustomer().getCourses().remove(course);
        userRepository.save(user);
    }

    public CompletableFuture<String> uploadAvatar(String name, byte[] avatar) {
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return s3Service.uploadPng(avatar, "user-" + user.getId(), "user-avatar")
                .thenCompose(fileName -> {
                    if (fileName == null) {
                        throw new IllegalArgumentException("exception.user.avatar_upload_failed");
                    } else {
                        return s3Service.getFileUrl("user-avatar", fileName);
                    }
                }).thenApply(url -> url);
    }

    public String getAvatarUrl(String name) {
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return s3Service.getFileUrl("user-avatar", "user-" + user.getId()).join();
    }

    public void addRole(AddRoleRequest addRoleRequest) {
        String permission = addRoleRequest.getPermission();
        for (String key : addRoleRequest.getCredentials().keySet()) {
            permission = permission.replace("{" + key + "}", addRoleRequest.getCredentials().get(key));
        }
        userPermissionService.addPermission(addRoleRequest.getUsername(), permission, addRoleRequest.getStartTime(), addRoleRequest.getEndTime());
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    @Lazy
    public void setBCryptPasswordEncoder(PasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setS3Service(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Autowired
    public void setCourseCustomerRepository(CourseCustomerRepository courseCustomerRepository) {
        this.courseCustomerRepository = courseCustomerRepository;
    }

    @Autowired
    public void setUserPermissionService(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }
}
