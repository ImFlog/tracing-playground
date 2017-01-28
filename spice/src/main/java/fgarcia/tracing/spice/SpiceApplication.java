package fgarcia.tracing.spice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpiceApplication.class, args);
    }

    @RequestMapping(value = "/addSpice", method = RequestMethod.GET)
    public String addSpice() throws InterruptedException {
        Thread.sleep(100);
        return "Some spice !";
    }
}