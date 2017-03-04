package fgarcia.barman.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Flo on 17/01/2017.
 */
@FeignClient(name = "clerk", url = "localhost:8082")
public interface ClerkClient {

    @RequestMapping(value = "/fetchIngredients", method = RequestMethod.GET)
    String fetchIngredients();
}
