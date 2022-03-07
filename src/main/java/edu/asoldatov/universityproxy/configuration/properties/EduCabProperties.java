package edu.asoldatov.universityproxy.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.omstu.educab")
public class EduCabProperties {
    private String url;
}
