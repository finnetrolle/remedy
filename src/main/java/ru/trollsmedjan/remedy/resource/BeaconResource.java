package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.BeaconDTO;
import ru.trollsmedjan.remedy.dto.SolarSystemDTO;
import ru.trollsmedjan.remedy.dto.input.BaseBeaconData;
import ru.trollsmedjan.remedy.dto.input.CreateBeaconDTO;
import ru.trollsmedjan.remedy.dto.input.EngageBeaconDTO;
import ru.trollsmedjan.remedy.model.entity.*;
import ru.trollsmedjan.remedy.service.BeaconService;
import ru.trollsmedjan.remedy.service.CampaignService;
import ru.trollsmedjan.remedy.service.EntoserService;
import ru.trollsmedjan.remedy.service.SpaceService;

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
    private SpaceService spaceService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private EntoserService entoserService;

    @Autowired
    private BeaconService beaconService;

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Remove beacon", notes = "Remove beacon")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad input data"),
            @ApiResponse(code = 201, message = "") })
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> removeBeacon(@RequestBody BaseBeaconData baseBeaconData) {
        Beacon beacon = beaconService.getBeacon(baseBeaconData.getBeaconId());
        if (beacon == null) {
            return getBadRequest();
        }
        Campaign campaign = campaignService.getCampaign(baseBeaconData.getCampaignId());
        if (campaign == null) {
            return getBadRequest();
        }

        Entoser entoser = beacon.getEntoser();
        if (entoser != null) {
            entoser.setEngaging(null);
            entoserService.save(entoser);
        }

        beaconService.delete(beacon);
        return new ResponseEntity<BeaconDTO>(HttpStatus.OK);
    }

    @RequestMapping(value = "/stopengage", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> stopEngageBeacon(@RequestBody BaseBeaconData baseBeaconData) {
        Beacon beacon = beaconService.getBeacon(baseBeaconData.getBeaconId());
        if (beacon == null) {
            return getBadRequest();
        }
        Campaign campaign = campaignService.getCampaign(baseBeaconData.getCampaignId());
        if (campaign == null) {
            return getBadRequest();
        }

        Entoser entoser = beacon.getEntoser();
        if (entoser != null) {
            entoser.setEngaging(null);
            entoserService.save(entoser);
        }

        beacon.setStatus(BeaconStatus.EMPTY);
        beacon.setTimeToCapture(0);
        beacon.setStartTime(0);

        beaconService.delete(beacon);
        return new ResponseEntity<BeaconDTO>(HttpStatus.OK);
    }

    @RequestMapping(value = "/reportenemyattack", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> engageBeacon(@RequestBody BaseBeaconData baseBeaconData) {
        Beacon beacon = beaconService.getBeacon(baseBeaconData.getBeaconId());
        if (beacon == null) {
            return getBadRequest();
        }
        Campaign campaign = campaignService.getCampaign(baseBeaconData.getCampaignId());
        if (campaign == null) {
            return getBadRequest();
        }

        Entoser entoser = beacon.getEntoser();
        if (entoser != null) {
            entoser.setEngaging(null);
            entoserService.save(entoser);
        }

        beacon.setStatus(BeaconStatus.ATTACKED);
        beaconService.save(beacon);
        return new ResponseEntity<BeaconDTO>(HttpStatus.OK);
    }

    @RequestMapping(value = "/engage", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> engageBeacon(@RequestBody EngageBeaconDTO engageBeaconDTO) {
        Beacon beacon = beaconService.getBeacon(engageBeaconDTO.getBeaconId());
        if (beacon == null) {
            return getBadRequest();
        }
        Campaign campaign = campaignService.getCampaign(engageBeaconDTO.getCampaignId());
        if (campaign == null) {
            return getBadRequest();
        }
        Entoser entoser = entoserService.getEntoser(engageBeaconDTO.getEntoser(), campaign);
        if (entoser == null) {
            return getBadRequest();
        }
        beacon.setStatus(BeaconStatus.WARMINGUP);
        beacon.setEntoser(entoser);
        beacon.setStartTime(System.currentTimeMillis());
        beacon.setTimeToCapture(getEndTime(beacon.getStartTime(), entoser.isT2EntosisModule(), entoser.isCapitalShip(), 2.5F));
        entoser.setEngaging(beacon);
        beaconService.save(beacon);
        entoserService.save(entoser);
        return new ResponseEntity<BeaconDTO>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{campaignid}")
    public @ResponseBody
    ResponseEntity<List<BeaconDTO>> getBeacons(@PathVariable Long campaignid) {
        Campaign campaign = campaignService.getCampaign(campaignid);
        if (campaign == null) {
            return new ResponseEntity<List<BeaconDTO>>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<BeaconDTO>>(beaconService.getBeacons(campaign).stream()
                .map(b -> {
                    return createBeaconDTO(b);
                })
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BeaconDTO> createBeacon(@RequestBody CreateBeaconDTO createBeaconDTO) {
        Campaign campaign = campaignService.getCampaign(createBeaconDTO.getCampaignId());
        if (campaign == null) {
            return getBadRequest();
        }

        SolarSystem location = spaceService.getSolarSystem(createBeaconDTO.getLocation());
        if (location == null) {
            return getBadRequest();
        }

        SolarSystem affectingOn = spaceService.getSolarSystem(createBeaconDTO.getAffectingSystem());
        if (affectingOn == null) {
            return getBadRequest();
        }

        if (!location.getConstellation().equals(campaign.getConstellation())) {
            return getBadRequest();
        }
        if (!affectingOn.getConstellation().equals(campaign.getConstellation())) {
            return getBadRequest();
        }

        Beacon beacon = new Beacon();
        beacon.setAffectingSystem(affectingOn);
        beacon.setCampaign(campaign);
        beacon.setLocation(location);
        beacon.setName(createBeaconDTO.getName());
        beacon.setStatus(BeaconStatus.EMPTY);
        beaconService.save(beacon);
        return new ResponseEntity<BeaconDTO>(createBeaconDTO(beacon), HttpStatus.OK);
    }

    private BeaconDTO createBeaconDTO(Beacon beacon) {
        BeaconDTO beaconDTO = new BeaconDTO();
        beaconDTO.setId(beacon.getId());
        beaconDTO.setLocation(new SolarSystemDTO(beacon.getLocation().getName()));
        beaconDTO.setAffectingSystem(new SolarSystemDTO(beacon.getAffectingSystem().getName()));
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
