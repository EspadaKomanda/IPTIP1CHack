package ru.espada.ep.iptip.user.permission;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import ru.espada.ep.iptip.user.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_permissions")
public class UserPermissionEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "user_id")
    private Long userId;

    private Long startTime;
    private Long endTime;

    @Override
    public String getAuthority() {
        return name;
    }
}