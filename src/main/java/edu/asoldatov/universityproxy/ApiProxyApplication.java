package edu.asoldatov.universityproxy;

import edu.asoldatov.universityproxy.configuration.OmstuProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({OmstuProperties.class})
@SpringBootApplication
public class ApiProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiProxyApplication.class, args);
    }
}
