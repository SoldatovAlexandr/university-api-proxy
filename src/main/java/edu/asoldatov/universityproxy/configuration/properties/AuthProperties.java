package edu.asoldatov.universityproxy.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.omstu.auth")
public class AuthProperties {

    private String url;
    private String login;
    private String auth;
}
