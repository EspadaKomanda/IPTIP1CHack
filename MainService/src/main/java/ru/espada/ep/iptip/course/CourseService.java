package ru.espada.ep.iptip.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.course.model.CourseResponseModel;
import ru.espada.ep.iptip.course.model.CreateCourseModel;
import ru.espada.ep.iptip.course.test.TestEntity;
import ru.espada.ep.iptip.user.UserEntity;
import ru.espada.ep.iptip.user.UserRepository;
import ru.espada.ep.iptip.user.UserService;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private UserService userService;

    public void createCourse(Principal principal, CreateCourseModel createCourseModel) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        CourseEntity courseEntity = CourseEntity.builder()
                .name(createCourseModel.getName())
                .description(createCourseModel.getDescription())
                .build();
        courseRepository.save(courseEntity);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public CourseEntity getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
