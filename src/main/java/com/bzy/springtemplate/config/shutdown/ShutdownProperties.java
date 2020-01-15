package com.bzy.springtemplate.config.shutdown;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("shutdown")
@Getter
@Setter
public class ShutdownProperties {
    private boolean entireServiceEnabled = true;
}
