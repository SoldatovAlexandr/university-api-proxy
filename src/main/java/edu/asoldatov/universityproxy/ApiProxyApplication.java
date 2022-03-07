package edu.asoldatov.universityproxy;

import edu.asoldatov.universityproxy.configuration.properties.AuthProperties;
import edu.asoldatov.universityproxy.configuration.properties.EduCabProperties;
import edu.asoldatov.universityproxy.configuration.properties.ScheduleProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ScheduleProperties.class, AuthProperties.class, EduCabProperties.class})
@SpringBootApplication
public class ApiProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiProxyApplication.class, args);
    }
}
