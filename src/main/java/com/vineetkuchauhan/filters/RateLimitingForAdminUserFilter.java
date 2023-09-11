package com.vineetkuchauhan.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingForAdminUserFilter extends AbstractGatewayFilterFactory<RateLimitingForAdminUserFilter.Config> {


    private static final Logger logger = LoggerFactory.getLogger(RateLimitingForAdminUserFilter.class);
    private static final ConcurrentHashMap<String, AtomicInteger> requestCounters = new ConcurrentHashMap<>();

    public RateLimitingForAdminUserFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        logger.info("RateLimitingForAdminUserFilter is invoked");
        return (exchange, chain) -> {
            // Get the authenticated user's username and client header
            String username = exchange.getAttribute("userName");
            logger.info("User: {}", username);

            if ("admin".equals(username)) {
                logger.info("Rate limiting logic for admin user");
                // Implement your rate limiting logic here
                // For example, using RedisRateLimiter or a custom logic
                // For this example, we'll allow a limited number of requests per second for the "admin" user of the "client" client
                if (isRateLimited(username)) {
                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    return exchange.getResponse().setComplete();
                }
            }

            // Continue the filter chain for other users or clients
            return chain.filter(exchange);
        };
    }

    private boolean isRateLimited(String username) {
        // Implement rate limiting logic here
        AtomicInteger counter = requestCounters.computeIfAbsent(username, u -> new AtomicInteger(0));
        int currentCount = counter.incrementAndGet();
        if (currentCount > 10) { // Adjust the limit as needed
            return true;
        }
        return false;
    }

    public static class Config {
        // Configuration properties can be added here if needed
    }

}




