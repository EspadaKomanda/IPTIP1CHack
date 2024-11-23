package ru.espada.ep.iptip.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.espada.ep.iptip.audit.Auditable;
import ru.espada.ep.iptip.course.CourseEntity;
import ru.espada.ep.iptip.study_groups.StudyGroupEntity;
import ru.espada.ep.iptip.user.permission.UserPermissionEntity;
import ru.espada.ep.iptip.user.profile.ProfileEntity;
import ru.espada.ep.iptip.user.permission.annotation.Permission;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_user")
@Permission(children = {}, value = "user")
public class UserEntity extends Auditable implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=3, message = "Не меньше 3 знаков")
    @Column(unique = true, nullable = false)
    private String username;
    @JsonIgnore
    @Size(min=3, message = "Не меньше 3 знаков")
    private String password;
    @Transient
    private String passwordConfirm;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_study_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "study_group_id"))
    private Set<StudyGroupEntity> studyGroups;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_responsible",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<CourseEntity> responsibleCourses;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private List<UserPermissionEntity> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}