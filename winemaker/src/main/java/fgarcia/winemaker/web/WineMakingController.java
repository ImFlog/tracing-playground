package fgarcia.winemaker.web;

import fgarcia.winemaker.feign.WarmerClient;
import fgarcia.winemaker.feign.SpiceClient;
import fgarcia.winemaker.feign.WineClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Flo on 17/01/2017.
 */
@RestController
public class WineMakingController {

    private WineClient wineClient;
    private SpiceClient spiceClient;
    private WarmerClient warmerClient;

    @Autowired
    public WineMakingController(WineClient wineClient, SpiceClient spiceClient, WarmerClient warmerClient) {
        this.wineClient = wineClient;
        this.spiceClient = spiceClient;
        this.warmerClient = warmerClient;
    }

    /**
     * Prepare mulled wine.
     *
     * @return the recipe to prepare the wine and the elapsed time.
     */
    @RequestMapping(value = "/make", method = RequestMethod.GET)
    public String serveMulledWine() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        StringBuilder res = new StringBuilder();

        // Call add winemaker
        Future<String> fullGlass = wineClient.pourWine().queue();

        // Call add spices
        Future<String> spicedWine = spiceClient.addSpice().queue();
        res.append(fullGlass.get()).append(spicedWine.get());

        // heat the wine
        res.append(warmerClient.heatWine());
        return res + "Total prepare time = " + (System.currentTimeMillis() - startTime);
    }
}
