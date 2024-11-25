package ru.espada.ep.iptip.university.institute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteRepository extends JpaRepository<InstituteEntity, Long> {
}