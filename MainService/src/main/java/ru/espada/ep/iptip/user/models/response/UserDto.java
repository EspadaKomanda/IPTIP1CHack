package ru.espada.ep.iptip.user.models.response;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {

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
    private int course;
    private String studyGroup;

}