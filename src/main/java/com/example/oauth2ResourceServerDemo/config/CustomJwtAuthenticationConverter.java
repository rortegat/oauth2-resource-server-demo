package com.example.oauth2ResourceServerDemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final Logger log = LoggerFactory.getLogger(CustomJwtAuthenticationConverter.class);

    private final String resourceId;
    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    public CustomJwtAuthenticationConverter(String resourceId) {
        this.resourceId = resourceId;
    }

    private static Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt, final String resourceId) {
        log.info("Extracting user roles from JSON Web Token claims");
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (resourceAccess != null &&
                (resource = (Map<String, Object>) resourceAccess.get(resourceId)) != null &&
                (resourceRoles = (Collection<String>) resource.get("roles")) != null) {
            return resourceRoles.stream()
                    .map(x -> new SimpleGrantedAuthority("ROLE_" + x))
                    .collect(Collectors.toSet());
        } else
            return Collections.emptyList();
    }

    @Override
    public AbstractAuthenticationToken convert(final Jwt source) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                defaultGrantedAuthoritiesConverter.convert(source).stream(),
                extractResourceRoles(source, resourceId).stream()).collect(Collectors.toSet());
        return new JwtAuthenticationToken(source, authorities);
    }
}

