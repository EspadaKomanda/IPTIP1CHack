package ru.espada.ep.iptip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class IptipApplication {

    public static void main(String[] args) {
        SpringApplication.run(IptipApplication.class, args);
    }

}
