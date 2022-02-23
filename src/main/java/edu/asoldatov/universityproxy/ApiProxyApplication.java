package edu.asoldatov.universityproxy;

import edu.asoldatov.universityproxy.configuration.AuthProperties;
import edu.asoldatov.universityproxy.configuration.OmstuProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({OmstuProperties.class, AuthProperties.class})
@SpringBootApplication
public class ApiProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiProxyApplication.class, args);
    }
}
