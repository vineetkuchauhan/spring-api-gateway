package com.vineetkuchauhan.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiLimiterConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ApiLimiterConfiguration.class);

//    @Bean
//    public KeyResolver userKeyResolver() {
//        return exchange -> {
//            // Get the authenticated user's username
//            String username = exchange.getRequest().getHeaders().getFirst("user");
//
//            // Form a key based on the username and client
//            String key = "user:" + username;
//            logger.info("key : "+ key);
//            // Convert the key to a Mono<String>
//            return Mono.just(key);
//        };
//    }



//    @Bean
//    public KeyResolver keyResolver() {
//        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
//    }




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
