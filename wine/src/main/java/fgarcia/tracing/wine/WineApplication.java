package fgarcia.tracing.wine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Flo on 19/01/2017.
 */
@SpringBootApplication
@RestController
public class WineApplication {

    public static void main(String[] args) {
        SpringApplication.run(WineApplication.class, args);
    }

    @RequestMapping(value = "/pourWine", method = RequestMethod.GET)
    public String pourWine() throws InterruptedException {
        Thread.sleep(300);
        return "Here is wine";
    }
}
