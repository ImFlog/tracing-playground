package fgarcia.barman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class BarmanApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarmanApplication.class, args);
    }
}
