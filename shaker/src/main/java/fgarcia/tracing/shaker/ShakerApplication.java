package fgarcia.tracing.shaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class ShakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShakerApplication.class, args);
    }

    @RequestMapping(value = "/shake", method = RequestMethod.GET)
    public synchronized String shake() throws InterruptedException {
        Thread.sleep(1000);
        return "Shake It Shake It !";
    }
}