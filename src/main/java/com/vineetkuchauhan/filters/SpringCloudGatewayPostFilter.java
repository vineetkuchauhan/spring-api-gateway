package com.vineetkuchauhan.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SpringCloudGatewayPostFilter extends AbstractGatewayFilterFactory<SpringCloudGatewayPostFilter.Config> {

    public SpringCloudGatewayPostFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        System.out.println("Spring Cloud Gateway Post Filter -> executed apply method");

        return ((exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(
                    () -> {
                        ServerHttpResponse response = exchange.getResponse();
                        HttpHeaders httpHeaders = response.getHeaders();
                        httpHeaders.forEach((k, v) -> {
                            System.out.println(k + " " + v);
                        });
                    }));
        });
    }

    public static class Config {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
