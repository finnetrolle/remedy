package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.BeaconDTO;
import ru.trollsmedjan.remedy.dto.input.CreateBeaconDTO;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.*;
import ru.trollsmedjan.remedy.services.BeaconService;
import ru.trollsmedjan.remedy.services.OptionalDataProvider;

import javax.ws.rs.core.Response;

/**
 * Created by finnetrolle on 28.07.2015.
 */
@Api(basePath = "/beacons", value = "beacons", description = "Operations with Beacons", produces = "application/json")
@RestController
@RequestMapping(value = "/campaign/{campaignId}/primary/{primaryId}/beacon")
public class BeaconResource {

    @Autowired
    private BeaconService beaconService;

    private static final Logger log = Logger.getLogger(BeaconResource.class);

    @Autowired
    private OptionalDataProvider db;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response getBeacons(@PathVariable Long campaignId,
                               @PathVariable Long primaryId)
                               throws RemedyDataLayerException {

        log.debug("GET beacons for c = " + campaignId + ", p = " + primaryId);

        PrimaryGoal primaryGoal = db.getPrimaryGoal(primaryId)
                .orElseThrow(() -> new RemedyDataLayerException());

        return Response.ok()
                .entity(db.findBeaconsByPrimary(primaryGoal))
                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Response createBeacon(@PathVariable Long campaignId,
                                 @PathVariable Long primaryId,
                                 @RequestBody CreateBeaconDTO data)
                                 throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("POST create beacon for c = " + campaignId + ", p = " + primaryId + " and data = " + data);
        Beacon beacon = beaconService.createBeacon(data.getName(), primaryId, data.getLocation());

        return Response.ok()
                .entity(new BeaconDTO(beacon.getId(), beacon.getName(), beacon.getLocation().getName(), beacon.getStatus(), beacon.getPrimaryGoal().getName()))
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deleteBeacon(@PathVariable Long campaignId,
                                 @PathVariable Long primaryId,
                                 @PathVariable Long id)
            throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("POST remove beacon for c = " + campaignId + ", p = " + primaryId + " and id = " + id);

        return Response.ok()
                .entity(beaconService.removeBeacon(id))
                .build();
    }

    @RequestMapping(value = "/{id}/reportenemyattack", method = RequestMethod.POST)
    @ResponseBody
    public Response reportEnemyAttack(@PathVariable Long campaignId,
                                      @PathVariable Long primaryId,
                                      @PathVariable Long id)
            throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("POST report enemy attack for c = " + campaignId + ", p = " + primaryId + " and deacon " + id);

        beaconService.reportEnemyAttack(id);

        return Response.ok().build();
    }

    @RequestMapping(value = "/{id}/defended", method = RequestMethod.POST)
    @ResponseBody
    public Response defendEnemyAttack(@PathVariable Long campaignId,
                                      @PathVariable Long primaryId,
                                      @PathVariable Long id)
            throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("POST defended for c = " + campaignId + ", p = " + primaryId + " and deacon " + id);

        beaconService.reportBeaconDefended(id);

        return Response.ok().build();
    }



}
