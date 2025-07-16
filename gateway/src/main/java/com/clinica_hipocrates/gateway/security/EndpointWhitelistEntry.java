package com.clinica_hipocrates.gateway.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointWhitelistEntry {
    private String path;
    private List<HttpMethod> methods;
}
