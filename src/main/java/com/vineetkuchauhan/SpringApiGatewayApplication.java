package com.vineetkuchauhan;

import com.vineetkuchauhan.service.RedisConnectionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApiGatewayApplication implements CommandLineRunner {

    @Autowired
    private RedisConnectionTestService redisConnectionTestService;

    public static void main(String[] args) {
        SpringApplication.run(SpringApiGatewayApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Call the Redis connection test method
        redisConnectionTestService.testRedisConnection();
    }
}
