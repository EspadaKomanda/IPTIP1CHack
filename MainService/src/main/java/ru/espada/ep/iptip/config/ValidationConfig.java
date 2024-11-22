package ru.espada.ep.iptip.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "validation")
public class ValidationConfig {

    private String[] domains;

}
