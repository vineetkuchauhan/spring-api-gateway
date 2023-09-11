package com.vineetkuchauhan.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class SpringCloudGatewayPreFilter extends AbstractGatewayFilterFactory<SpringCloudGatewayPreFilter.Config> {

    public SpringCloudGatewayPreFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(SpringCloudGatewayPreFilter.Config config) {
        System.out.println("Spring Cloud Gateway Pre Filter -> executed apply method");
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest().mutate().header("scgw-pre-header", Math.random() * 10 + "").build();
            return chain.filter(exchange.mutate().request(request).build());
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
