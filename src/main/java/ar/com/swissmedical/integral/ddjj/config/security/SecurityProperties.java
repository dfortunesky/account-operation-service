package ar.com.swissmedical.integral.ddjj.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private String authorizedUsers;

    public List<String> getAuthorizedUsers() {
        if (authorizedUsers == null || authorizedUsers.isBlank()) return List.of();
        return Arrays.asList(authorizedUsers.split(","));
    }
}
