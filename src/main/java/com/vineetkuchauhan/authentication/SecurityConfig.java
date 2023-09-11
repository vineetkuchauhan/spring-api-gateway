package com.vineetkuchauhan.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

    @Bean
    public MapReactiveUserDetailsService users() {
        UserDetails user1 = User.builder()
                .username("user1")
                .password("{noop}user1")
                .roles("USER")
                .build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password("{noop}user2")
                .roles("USER")
                .build();
        UserDetails user3 = User.builder()
                .username("user3")
                .password("{noop}user3")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user1, user2, user3, admin);
    }
}
