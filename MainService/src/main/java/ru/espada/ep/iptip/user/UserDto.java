package ru.espada.ep.iptip.user;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private Date createdAt;
    private String username;
    private String profileIcon;
    private String profileName;
    private String profileSurname;
    private String profilePatronymic;
    private Date profileBirthDate;
    private String profilePhone;
    private boolean profilePhoneConfirmed;
    private String profileEmail;
    private boolean profileEmailConfirmed;
    private int profileSemester;
    private String profileStudentIdCard;
    private Set<String> studyGroupNames;

}