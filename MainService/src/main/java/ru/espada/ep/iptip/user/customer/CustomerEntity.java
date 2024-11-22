package ru.espada.ep.iptip.user.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.CourseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class CustomerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String icon;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    private Date birthDate;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    private boolean phoneConfirmed;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private boolean emailConfirmed;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "course_customer",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<CourseEntity> courses;

}
