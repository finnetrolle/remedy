package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.EntoserDTO;
import ru.trollsmedjan.remedy.dto.input.BaseEntoserData;
import ru.trollsmedjan.remedy.dto.input.ExtendedEntoserData;
import ru.trollsmedjan.remedy.model.entity.*;
import ru.trollsmedjan.remedy.service.BeaconService;
import ru.trollsmedjan.remedy.service.CampaignService;
import ru.trollsmedjan.remedy.service.EntoserService;
import ru.trollsmedjan.remedy.service.LogService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by finnetrolle on 28.07.2015.
 */
@Api(basePath = "/entosers", value = "Entosers", description = "Operations with entosers", produces = "application/json")
@RestController
@RequestMapping("/entosers")
public class EntoserResource {

    private static final Logger log = Logger.getLogger(EntoserResource.class);

    @Autowired
    private LogService logService;

    @Autowired
    private EntoserService entoserService;

    @Autowired
    private BeaconService beaconService;

    @Autowired
    private CampaignService campaignService;

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<EntoserDTO> unregisterEntoser(@RequestBody BaseEntoserData data) {
        log.info("unregister entoser " + data);
        Campaign campaign = campaignService.getCampaign(data.getCampaignId());
        if (campaign == null) {
            log.warn("campaign not found");
            return getBadRequest();
        }

        Entoser entoser = entoserService.getEntoser(data.getId());
        if (entoser == null) {
            log.warn("entoser not found");
            return getBadRequest();
        }

        Beacon beacon = entoser.getEngaging();
        if (beacon != null) {
            log.debug("updating beacon " + beacon);
            beacon.setStatus(BeaconStatus.EMPTY);
            beacon.setTimeToCapture(0);
            beacon.setStartTime(0);
            beaconService.save(beacon);
        }

        log.debug("updating entoser " + entoser);
        logService.info(ActionType.ENTOSER_REMOVE, data.getUsername(), campaign, entoser.toString());
        entoserService.remove(entoser);

        return new ResponseEntity<EntoserDTO>(getEntoserDTO(entoser), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<EntoserDTO> registerEntoser(@RequestBody ExtendedEntoserData data) {
        log.info("register entoser "+ data);
        Campaign campaign = campaignService.getCampaign(data.getCampaignId());
        if (campaign == null) {
            log.warn("campaign not found");
            return getBadRequest();
        }

        Entoser entoser = entoserService.getEntoser(data.getUsername(), campaign);
        if (entoser != null) {
            log.warn("entoser not found");
            return getBadRequest();
        }

        entoser = new Entoser();
        entoser.setCampaign(campaign);
        entoser.setCapitalShip(data.isCapitalShip());
        entoser.setEngaging(null);
        entoser.setName(data.getUsername());
        entoser.setShip(data.getShip());
        entoser.setT2EntosisModule(data.isT2EntosisModule());
        log.debug("saving entoser " + entoser);
        entoserService.save(entoser);
        logService.info(ActionType.ENTOSER_CREATE, data.getUsername(), campaign, entoser.toString());

        return new ResponseEntity<EntoserDTO>(getEntoserDTO(entoser), HttpStatus.OK);
    }

    @RequestMapping(value = "/{campaignid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<EntoserDTO>> getEntosers(@PathVariable Long campaignid) {
        log.info("GET entosers for " + campaignid);
        Campaign campaign = campaignService.getCampaign(campaignid);
        if (campaign == null) {
            log.warn("campaign not found");
            return new ResponseEntity<List<EntoserDTO>>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<EntoserDTO>>(entoserService.getEntosers(campaign)
                .stream()
                .map(e -> {
                    return getEntoserDTO(e);
                })
                .collect(Collectors.toList())
                , HttpStatus.OK);
    }

    private EntoserDTO getEntoserDTO(Entoser entoser) {
        EntoserDTO entoserDTO = new EntoserDTO();
        entoserDTO.setCapitalShip(entoser.isCapitalShip());
        if (entoser.getEngaging() != null) {
            entoserDTO.setEngaging(entoser.getEngaging().getName());
        }
        entoserDTO.setId(entoser.getId());
        entoserDTO.setName(entoser.getName());
        entoserDTO.setShip(entoser.getShip());
        entoserDTO.setT2EntosisModule(entoser.isT2EntosisModule());
        return entoserDTO;
    }

    private ResponseEntity<EntoserDTO> getBadRequest() {
        return new ResponseEntity<EntoserDTO>(HttpStatus.BAD_REQUEST);
    }

}
