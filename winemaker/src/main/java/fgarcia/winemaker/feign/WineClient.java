package fgarcia.winemaker.feign;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Flo on 17/01/2017.
 */
@FeignClient(name = "pourer", url = "localhost:8081")
public interface WineClient {

    @RequestMapping(value = "/pourWine", method = RequestMethod.GET)
    HystrixCommand<String> pourWine();
}
