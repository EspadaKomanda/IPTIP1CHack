package ru.espada.ep.iptip.event;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}