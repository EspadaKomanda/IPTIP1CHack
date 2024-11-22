package ru.espada.ep.iptip.course.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.course.CourseEntity;
import ru.espada.ep.iptip.course.CourseStatus;
import ru.espada.ep.iptip.user.customer.CustomerEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course_customer")
public class CourseCustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    private Long startTime;
    private Long endTime;

    private CourseStatus status;
    private int result;

}
