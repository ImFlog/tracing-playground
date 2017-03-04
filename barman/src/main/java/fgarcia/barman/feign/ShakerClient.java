package fgarcia.barman.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Flo on 19/01/2017.
 */
@FeignClient(name = "shaker", url = "localhost:8083")
public interface ShakerClient {

    @RequestMapping(value = "/shake", method = RequestMethod.GET)
    String shake();
}