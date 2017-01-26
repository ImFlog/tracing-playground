package fgarcia.winemaker.feign;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Flo on 17/01/2017.
 */
@FeignClient(name = "spice", url = "localhost:8082")
public interface SpiceClient {

    @RequestMapping(value = "/addSpice", method = RequestMethod.GET)
    HystrixCommand<String> addSpice();
}
