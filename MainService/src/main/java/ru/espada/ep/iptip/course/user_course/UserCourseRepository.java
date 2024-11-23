package ru.espada.ep.iptip.course.user_course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourseEntity, Long> {
    UserCourseEntity findUserCourseEntityByUserIdNotNull();

    UserCourseEntity findUserCourseEntityByUserId(Long userId);
}
