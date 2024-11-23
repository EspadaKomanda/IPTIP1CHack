package ru.espada.ep.iptip.user.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProfileRequest {

    private Long userId;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String patronymic;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    private String birthDate;
    private int semester;
    private String studentIdCard;

}
