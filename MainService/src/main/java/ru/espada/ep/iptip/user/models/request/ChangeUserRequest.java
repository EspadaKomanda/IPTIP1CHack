package ru.espada.ep.iptip.user.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeUserRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ChangeUserFieldRequest {

        private String field;
        private String value;

    }

    private long userId;
    private List<ChangeUserFieldRequest> fields;

}
