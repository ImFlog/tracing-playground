package fgarcia.winemaker.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Flo on 19/01/2017.
 */
@FeignClient(name = "warmer", url = "localhost:8083")
public interface WarmerClient {

    @RequestMapping(value = "/warm", method = RequestMethod.GET)
    String heatWine();
}