package ru.espada.ep.iptip.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "s3")
public class S3Config {

    String endpoint_admin;
    String endpoint_user;
    String accessKey;
    String secretKey;
    int imageExpiryTime;

}
