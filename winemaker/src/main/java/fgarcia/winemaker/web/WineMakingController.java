package fgarcia.winemaker.web;

import fgarcia.winemaker.feign.SpiceClient;
import fgarcia.winemaker.feign.WarmerClient;
import fgarcia.winemaker.feign.WineClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
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

    private static final Logger logger = LoggerFactory.getLogger(WineMakingController.class);

    private WineClient wineClient;
    private SpiceClient spiceClient;
    private WarmerClient warmerClient;

    private Tracer tracer;

    @Autowired
    public WineMakingController(WineClient wineClient, SpiceClient spiceClient, WarmerClient warmerClient, Tracer tracer) {
        this.wineClient = wineClient;
        this.spiceClient = spiceClient;
        this.warmerClient = warmerClient;
        this.tracer = tracer;
    }

    /**
     * Prepare mulled wine.
     *
     * @return the recipe to prepare the wine and the elapsed time.
     */
    @RequestMapping(value = "/make", method = RequestMethod.GET)
    public String serveMulledWine() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        getABottle();

        // Call add winemaker
        Future<String> fullGlass = wineClient.pourWine().queue();
        // Call add spices
        Future<String> spicedWine = spiceClient.addSpice().queue();

        fullGlass.get();
        spicedWine.get();

        // heat the wine
        warmerClient.heatWine();

        //return res + "Total prepare time = " + ;
        return "Served in " + (System.currentTimeMillis() - startTime) + " by " + tracer.getCurrentSpan().traceIdString();
    }

    private void getABottle() throws InterruptedException {
        Span s = tracer.createSpan("Bottle Span");
        logger.info("Am I sampled ?");
        tracer.addTag("red", "wine");
        Thread.sleep(100);
        tracer.close(s);
    }
}
