package ru.espada.ep.iptip.user.profile;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.espada.ep.iptip.audit.Auditable;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class ProfileEntity  extends Auditable implements Serializable {

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
    private int semester;
    private String studentIdCard;

}
