package ru.espada.ep.iptip.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "git")
public class GitConfig {

    private int max_repo_size = 4096;
    private String save_dir = "app/git/";

}
