package ru.espada.ep.iptip.course.user_course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourseEntity, Long> {
    UserCourseEntity findUserCourseEntityByUserIdNotNull();

    UserCourseEntity findUserCourseEntityByUserId(Long userId);

    List<UserCourseEntity> findUserCourseEntitiesByUserId(Long userId);

    List<UserCourseEntity> findUserCourseEntitiesByUserIdAndSemester(Long userId, int semester);

    UserCourseEntity findUserCourseEntityByUserIdAndCourseId(Long userId, Long courseId);
}
