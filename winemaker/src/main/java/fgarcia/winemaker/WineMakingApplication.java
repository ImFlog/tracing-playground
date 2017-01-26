package fgarcia.winemaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.Span;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class WineMakingApplication {

    public static void main(String[] args) {
        SpringApplication.run(WineMakingApplication.class, args);
    }

    @Bean
    public Sampler customSampler() {
        return span -> (Thread.currentThread().getId() % 2 == 0);
    }
}
