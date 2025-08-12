package com.clinica_hipocrates.common.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private List<WhitelistEntry> whitelist = new ArrayList<>();

    @Data
    public static class WhitelistEntry {
        private String pathRegex;
        private List<String> methods;
    }
}
