package com.aarci.sb3.config.security.utility;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sb3.jwt")
public class JWTConfigurationProperties {

    private long expireDuration;
    private String secretKey;

}
