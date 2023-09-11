package com.vineetkuchauhan.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class BasicAuthUsernameExtractorFilter extends AbstractGatewayFilterFactory<Object> {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingForAdminUserFilter.class);

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            // Get the Authorization header
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
                // Extract and decode the credentials
                String base64Credentials = authorizationHeader.substring("Basic ".length());
                String decodedCredentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

                // Split the credentials into username and password (if needed)
                String[] credentials = decodedCredentials.split(":");

                // The username is the first part of the credentials
                String username = credentials[0];

                // Add the username as a request attribute for further processing
                exchange.getAttributes().put("userName", username);
                logger.info("User Name: {}", username);
            }

            return chain.filter(exchange);
        };
    }
}

