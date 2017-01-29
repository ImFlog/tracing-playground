package fgarcia.tracing.warm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class WarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarmApplication.class, args);
    }

    @RequestMapping(value = "/warm", method = RequestMethod.GET)
    public synchronized String warm() throws InterruptedException {
        Thread.sleep(1000);
        return "It's hot in here";
    }
}