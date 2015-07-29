package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.BeaconDTO;
import ru.trollsmedjan.remedy.dto.input.BaseBeaconData;
import ru.trollsmedjan.remedy.dto.input.CreateBeaconDTO;
import ru.trollsmedjan.remedy.dto.input.EngageBeaconDTO;
import ru.trollsmedjan.remedy.model.entity.*;
import ru.trollsmedjan.remedy.service.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by finnetrolle on 28.07.2015.
 */
@Api(basePath = "/beacons", value = "beacons", description = "Operations with Beacons", produces = "application/json")
@RestController
@RequestMapping(value = "/beacons")
public class BeaconResource {

    @Autowired
    private PrimaryService primaryService;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private EntoserService entoserService;

    @Autowired
    private BeaconService beaconService;

    @Autowired
    private EveSovApiService eveSovApiService;

    private static final Logger log = Logger.getLogger(BeaconResource.class);

    @Autowired
    private LogService logService;

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Remove beacon", notes = "Remove beacon")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad input data"),
            @ApiResponse(code = 201, message = "") })
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> removeBeacon(@RequestBody BaseBeaconData baseBeaconData) {
        log.info("Remove beacon query for " + baseBeaconData);
        Beacon beacon = beaconService.getBeacon(baseBeaconData.getBeaconId());
        if (beacon == null) {
            log.warn("Beacon is not found");
            return getBadRequest();
        }
        Campaign campaign = campaignService.getCampaign(baseBeaconData.getCampaignId());
        if (campaign == null) {
            log.warn("Campaign is not found");
            return getBadRequest();
        }

        Entoser entoser = beacon.getEntoser();
        if (entoser != null) {
            entoser.setEngaging(null);
            entoserService.save(entoser);
            log.debug("Updating entoser " + entoser);
            logService.info(ActionType.ENTOSER_DISENGAGE, baseBeaconData.getUsername(), campaign, entoser.toString());
        }

        log.debug("Deleting beacon");
        logService.info(ActionType.BEACON_REMOVE, baseBeaconData.getUsername(), campaign, beacon.toString());
        beaconService.delete(beacon);
        return new ResponseEntity<BeaconDTO>(HttpStatus.OK);
    }

    @RequestMapping(value = "/stopengage", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> stopEngageBeacon(@RequestBody BaseBeaconData baseBeaconData) {
        log.info("Stop engaging beacon query for " + baseBeaconData);
        Beacon beacon = beaconService.getBeacon(baseBeaconData.getBeaconId());
        if (beacon == null) {
            log.warn("Beacon not found");
            return getBadRequest();
        }
        Campaign campaign = campaignService.getCampaign(baseBeaconData.getCampaignId());
        if (campaign == null) {
            log.warn("Campaign not found");
            return getBadRequest();
        }

        Entoser entoser = beacon.getEntoser();
        if (entoser != null) {
            entoser.setEngaging(null);
            log.debug("Updating entoser " + entoser);
            entoserService.save(entoser);
            logService.info(ActionType.ENTOSER_DISENGAGE, baseBeaconData.getUsername(), campaign, entoser.toString());
        }

        log.debug("Updating beacon " + beacon);
        beacon.setStatus(BeaconStatus.EMPTY);
        beacon.setTimeToCapture(0);
        beacon.setStartTime(0);

        beaconService.save(beacon);
        logService.info(ActionType.BEACON_STOP_ENGAGE, baseBeaconData.getUsername(), campaign, beacon.toString());
        return new ResponseEntity<BeaconDTO>(HttpStatus.OK);
    }

    @RequestMapping(value = "/defended", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> defendBeacon(@RequestBody BaseBeaconData baseBeaconData) {
        log.info("Defend beacon query " + baseBeaconData);
        Beacon beacon = beaconService.getBeacon(baseBeaconData.getBeaconId());
        if (beacon == null) {
            log.warn("beacon not found");
            return getBadRequest();
        }
        Campaign campaign = campaignService.getCampaign(baseBeaconData.getCampaignId());
        if (campaign == null) {
            log.warn("campaign not found");
            return getBadRequest();
        }

        log.debug("updating beacon " + beacon);
        beacon.setStatus(BeaconStatus.EMPTY);
        beacon.setTimeToCapture(0);
        beacon.setStartTime(0);

        beaconService.save(beacon);
        logService.info(ActionType.BEACON_DEFENDED, baseBeaconData.getUsername(), campaign, beacon.toString());
        return new ResponseEntity<BeaconDTO>(createBeaconDTO(beacon), HttpStatus.OK);
    }

    @RequestMapping(value = "/reportenemyattack", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> reportBeacon(@RequestBody BaseBeaconData baseBeaconData) {
        log.info("Report enemy attack " + baseBeaconData);
        Beacon beacon = beaconService.getBeacon(baseBeaconData.getBeaconId());
        if (beacon == null) {
            log.warn("beacon not found");
            return getBadRequest();
        }
        Campaign campaign = campaignService.getCampaign(baseBeaconData.getCampaignId());
        if (campaign == null) {
            log.warn("campaign not found");
            return getBadRequest();
        }

        Entoser entoser = beacon.getEntoser();
        if (entoser != null) {
            entoser.setEngaging(null);
            log.debug("updating entoser " + entoser);
            entoserService.save(entoser);
            logService.info(ActionType.ENTOSER_DISENGAGE, baseBeaconData.getUsername(), campaign, entoser.toString());
        }

        log.debug("updating beacon " + beacon);
        beacon.setStatus(BeaconStatus.ATTACKED);
        beaconService.save(beacon);
        logService.info(ActionType.BEACON_REPORT_ATTACK, baseBeaconData.getUsername(), campaign, beacon.toString());
        return new ResponseEntity<BeaconDTO>(HttpStatus.OK);
    }

    @RequestMapping(value = "/engage", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> engageBeacon(@RequestBody EngageBeaconDTO engageBeaconDTO) {
        log.info("engage beacon " + engageBeaconDTO);
        Beacon beacon = beaconService.getBeacon(engageBeaconDTO.getBeaconId());
        if (beacon == null) {
            log.warn("beacon not found");
            return getBadRequest();
        }
        Campaign campaign = campaignService.getCampaign(engageBeaconDTO.getCampaignId());
        if (campaign == null) {
            log.warn("campaign not found");
            return getBadRequest();
        }
        Entoser entoser = entoserService.getEntoser(engageBeaconDTO.getEntoser(), campaign);
        if (entoser == null) {
            log.warn("entoser not found");
            return getBadRequest();
        }
        log.debug("updating beacon " + beacon);
        beacon.setStatus(BeaconStatus.WARMINGUP);
        beacon.setEntoser(entoser);
        beacon.setStartTime(System.currentTimeMillis());
        beacon.setTimeToCapture(beacon.getStartTime() + BeaconUtils.getTimeToEntose(entoser.isT2EntosisModule(), entoser.isCapitalShip(), eveSovApiService.getSystemSecurityModifier("")));
        log.debug("updating entoser " + entoser);
        entoser.setEngaging(beacon);
        beaconService.save(beacon);
        logService.info(ActionType.BEACON_ENGAGE, engageBeaconDTO.getUsername(), campaign, beacon.toString());
        entoserService.save(entoser);
        logService.info(ActionType.ENTOSER_ENGAGE, engageBeaconDTO.getUsername(), campaign, entoser.toString());
        return new ResponseEntity<BeaconDTO>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{campaignid}")
    public @ResponseBody
    ResponseEntity<List<BeaconDTO>> getBeacons(@PathVariable Long campaignid) {
        log.info("GET beacons for " + campaignid);
        Campaign campaign = campaignService.getCampaign(campaignid);
        if (campaign == null) {
            log.warn("campaign not found");
            return new ResponseEntity<List<BeaconDTO>>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<BeaconDTO>>(beaconService.getBeacons(campaign).stream()
                .map(b -> createBeaconDTO(b))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> createBeacon(@RequestBody CreateBeaconDTO createBeaconDTO) {
        log.info("create beacon " + createBeaconDTO);
        Campaign campaign = campaignService.getCampaign(createBeaconDTO.getCampaignId());
        if (campaign == null) {
            log.warn("campaign not found");
            return getBadRequest();
        }

        SolarSystem location = spaceService.getSolarSystem(createBeaconDTO.getLocation());
        if (location == null) {
            log.warn("solar system not found");
            return getBadRequest();
        }

        PrimaryGoal primaryGoal = primaryService.get(createBeaconDTO.getPrimaryId());
        if (primaryGoal == null) {
            log.warn("primary not found");
            return getBadRequest();
        }

        if (!location.getConstellation().equals(campaign.getConstellation())) {
            log.warn("solar system not in campaign's constellation");
            return getBadRequest();
        }


        Beacon beacon = new Beacon();
        beacon.setPrimaryGoal(primaryGoal);
        beacon.setCampaign(campaign);
        beacon.setLocation(location);
        beacon.setName(createBeaconDTO.getName());
        beacon.setStatus(BeaconStatus.EMPTY);
        log.debug("creating new beacon " + beacon);
        beaconService.save(beacon);
        logService.info(ActionType.BEACON_CREATE, createBeaconDTO.getUsername(), campaign, beacon.toString());
        return new ResponseEntity<BeaconDTO>(createBeaconDTO(beacon), HttpStatus.OK);
    }

    public static BeaconDTO createBeaconDTO(Beacon beacon) {
        BeaconDTO beaconDTO = new BeaconDTO();
        beaconDTO.setPrimary(beacon.getPrimaryGoal().getName());
        beaconDTO.setId(beacon.getId());
        beaconDTO.setLocation(beacon.getLocation().getName());
        beaconDTO.setName(beacon.getName());
        beaconDTO.setStatus(beacon.getStatus());
        if (beaconDTO.getStatus() == BeaconStatus.ENGAGED || beaconDTO.getStatus() == BeaconStatus.WARMINGUP) {
            beaconDTO.setEntoser(beacon.getEntoser().getName());
            beaconDTO.setStartTime(beacon.getStartTime());
            beaconDTO.setTimeToCapture(beacon.getTimeToCapture());
        }
        return beaconDTO;
    }

    private long getEndTime(long currentTime, boolean t2Module, boolean isCapital, float securityIndex) {
        return currentTime + 0;
    }

    private ResponseEntity<BeaconDTO> getBadRequest() {
        return new ResponseEntity<BeaconDTO>(HttpStatus.BAD_REQUEST);
    }

}
