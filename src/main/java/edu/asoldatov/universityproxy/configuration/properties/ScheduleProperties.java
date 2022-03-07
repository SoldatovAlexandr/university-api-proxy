package edu.asoldatov.universityproxy.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.omstu.data")
public class ScheduleProperties {

    private String url;
    private String schedule;
    private String type;
}
