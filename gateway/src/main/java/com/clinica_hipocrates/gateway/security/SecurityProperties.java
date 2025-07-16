package com.clinica_hipocrates.gateway.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {
    private List<EndpointWhitelistEntry> whitelist;
}
