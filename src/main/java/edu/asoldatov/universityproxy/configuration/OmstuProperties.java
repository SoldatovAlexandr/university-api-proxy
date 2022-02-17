package edu.asoldatov.universityproxy.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.omstu")
public class OmstuProperties {

    private String url;
    private String schedule;
    private String type;
}
