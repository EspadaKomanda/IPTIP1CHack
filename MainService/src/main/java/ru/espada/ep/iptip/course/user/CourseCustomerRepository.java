package ru.espada.ep.iptip.course.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseCustomerRepository extends JpaRepository<CourseCustomerEntity, Long> {
}
