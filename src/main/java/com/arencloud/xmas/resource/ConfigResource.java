package com.arencloud.xmas.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.Map;

@Path("/api/config")
@Produces(MediaType.APPLICATION_JSON)
public class ConfigResource {

    @Inject
    @ConfigProperty(name = "quarkus.oidc.auth-server-url")
    String authServerUrl;

    @Inject
    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @PermitAll
    @GET
    public Map<String, String> get() {
        Map<String, String> cfg = new HashMap<>();
        cfg.put("authServerUrl", authServerUrl);
        cfg.put("clientId", clientId);
        cfg.put("realm", realmFrom(authServerUrl));
        return cfg;
    }

    private String realmFrom(String url) {
        if (url == null) {
            return "xmas";
        }
        String[] parts = url.split("/");
        for (int i = 0; i < parts.length - 1; i++) {
            if ("realms".equals(parts[i])) {
                return parts[i + 1];
            }
        }
        return "xmas";
    }
}
