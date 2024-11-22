package ru.espada.ep.iptip.user.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRoleRequest {

    private String username;
    private String permission;
    private Map<String, String> credentials;
    private Long startTime;
    private Long endTime;

}
