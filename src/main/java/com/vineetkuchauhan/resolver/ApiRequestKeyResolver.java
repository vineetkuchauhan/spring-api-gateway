package com.vineetkuchauhan.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class ApiRequestKeyResolver {

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestKeyResolver.class);

//    @Bean
//    public KeyResolver userKeyResolver() {
//        logger.info("Inside of key resolver");
//        return exchange -> Mono.just("1");
//    }

//    @Bean
//    public KeyResolver keyResolver() {
//        logger.info("Inside of key resolver");
//        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
//    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, KeyResolver keyResolver) {
        logger.info("In route locator");
        return builder.routes()
                .route("rate_limit_route", r -> r
                        .path("/ms2/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> config
                                        .setKeyResolver(keyResolver) // Set the key resolver
                                        .setRateLimiter(redisRateLimiter()) // Configure the rate limiter
                                )
                        )
                        .uri("http://localhost:8081")
                )
                .build();
    }

    @Bean
    public KeyResolver userKeyResolver() {
        logger.info("In key resolver");
        return exchange -> {
            // Get the authenticated user's username
            String userHeaderValue = exchange.getRequest().getHeaders().getFirst("user");

            // If 'user' header is not present or null, use a default value
            if (userHeaderValue == null) {
                userHeaderValue = "default";
            }
            logger.info("User header value : " + userHeaderValue);

            // Convert the key to a Mono<String>
            return Mono.just(userHeaderValue);
        };
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        logger.info("In redis limiter");
        // Configure the rate limiter, e.g., using Redis as a data store
        return new RedisRateLimiter(2, 5); // Allow 1 request per second, burst up to 2 requests
    }


//    @Bean
//    public KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(extractUsernameFromRequest(exchange));
//    }


//    private String extractUsernameFromRequest(ServerWebExchange exchange) {
//        HttpHeaders headers = exchange.getRequest().getHeaders();
//        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
//            // Extract and decode the Basic Authentication token
//            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
//            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
//
//            // Extract the username from the decoded credentials
//            String[] usernamePassword = credentials.split(":");
//            if (usernamePassword.length == 2) {
//                return "user:" + usernamePassword[0];
//            }
//        }
//
//        // If no valid Basic Authentication found, return a default value or behavior
//        return "anonymous-user";
//    }


//    private String extractUsernameFromRequest(ServerWebExchange exchange) {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        if (securityContext != null && securityContext.getAuthentication() != null) {
//            Object principal = securityContext.getAuthentication().getPrincipal();
//            if (principal instanceof UserDetails) {
//                logger.info("User : " + ((UserDetails) principal).getUsername());
//                return "user:" + ((UserDetails) principal).getUsername();
//            } else {
//                // If not UserDetails, you may need to adapt this logic based on your authentication setup
//                return "unknown-user";
//            }
//        } else {
//            // No authentication information found in the security context
//            return "anonymous-user";
//        }
//    }
}
