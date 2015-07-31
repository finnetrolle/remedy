package ru.trollsmedjan.remedy.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.services.ComplexService;

import javax.ws.rs.core.Response;

/**
 * Created by finnetrolle on 31.07.2015.
 */
@RestController
@RequestMapping("/complex/{campaignid}")
public class ComplexResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ComplexService complexService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response getComplexCampaign(@PathVariable Long campaignid) throws RemedyDataLayerException {
        log.info("GET COMPLEX campaign {}", campaignid);

        return Response.ok()
                .entity(complexService.getFullCampaignDTO(campaignid))
                .build();
    }

    @RequestMapping(value = "/beacons", method = RequestMethod.GET)
    @ResponseBody
    public Response getBeaconsForCampaign(@PathVariable Long campaignid) throws RemedyDataLayerException {
        log.info("GET COMPLEX beacons for campaign {}", campaignid);

        return Response.ok()
                .entity(complexService.getBeaconsForCampaign(campaignid))
                .build();
    }



}
