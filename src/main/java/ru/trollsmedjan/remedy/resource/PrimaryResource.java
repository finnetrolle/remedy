package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.BeaconDTO;
import ru.trollsmedjan.remedy.dto.PrimaryDTO;
import ru.trollsmedjan.remedy.dto.input.CreatePrimaryDTO;
import ru.trollsmedjan.remedy.dto.input.RemovePrimaryDTO;
import ru.trollsmedjan.remedy.model.entity.ActionType;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.PrimaryGoal;
import ru.trollsmedjan.remedy.service.BeaconService;
import ru.trollsmedjan.remedy.service.CampaignService;
import ru.trollsmedjan.remedy.service.LogService;
import ru.trollsmedjan.remedy.service.PrimaryService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by finnetrolle on 29.07.2015.
 */
@Api(basePath = "/primary", value = "Primary", description = "primary!!!", produces = "application/json")
@RestController
@RequestMapping("/primary")
public class PrimaryResource {

    private static final Logger log = Logger.getLogger(PrimaryResource.class);

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private PrimaryService primaryService;

    @Autowired
    private BeaconService beaconService;

    @Autowired
    private LogService logService;


    @RequestMapping(value = "/{campaignid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<PrimaryDTO>> getPrimaries(@PathVariable Long campaignid) {
        log.info("GET primaries for " + campaignid);
        Campaign campaign = campaignService.getCampaign(campaignid);
        if (campaign == null) {
            log.warn("campaign not found");
            return new ResponseEntity<List<PrimaryDTO>>(HttpStatus.NOT_FOUND);
        }
        List<PrimaryGoal> primaryGoals = primaryService.get(campaign);
        return new ResponseEntity<List<PrimaryDTO>>(primaryGoals
                .stream()
                .map(p -> new PrimaryDTO(p.getName(), p.getId()))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{campaignid}/{primaryid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<PrimaryDTO>> getPrimary(@PathVariable Long campaignid, @PathVariable Long primaryid) {
        log.info("GET beacons for campaign " + campaignid + " & primary " +primaryid);
        Campaign campaign = campaignService.getCampaign(campaignid);
        if (campaign == null) {
            log.warn("campaign not found");
            return new ResponseEntity<List<PrimaryDTO>>(HttpStatus.NOT_FOUND);
        }
        PrimaryGoal primaryGoal = primaryService.get(primaryid);
        if (primaryGoal == null) {
            log.warn("primary not found");
            return new ResponseEntity<List<PrimaryDTO>>(HttpStatus.NOT_FOUND);
        }
        List<BeaconDTO> beaconDTOs = beaconService.getBeacons(primaryGoal)
                .stream()
                .map(b -> BeaconResource.createBeaconDTO(b))
                .collect(Collectors.toList());

        return new ResponseEntity<List<PrimaryDTO>>(primaryService.get(campaign)
                .stream()
                .map(p -> new PrimaryDTO(p.getName(), p.getId(), beaconDTOs))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PrimaryDTO> createPrimary(@RequestBody CreatePrimaryDTO createPrimaryDTO) {
        log.info("create primary " + createPrimaryDTO);
        Campaign campaign = campaignService.getCampaign(createPrimaryDTO.getCampaignId());
        if (campaign == null) {
            log.warn("campaign not found");
            return new ResponseEntity<PrimaryDTO>(HttpStatus.BAD_REQUEST);
        }
        PrimaryGoal primaryGoal = new PrimaryGoal();
        primaryGoal.setName(createPrimaryDTO.getName());
        primaryGoal.setCampaign(campaign);
        primaryService.save(primaryGoal);
        logService.info(ActionType.PRIMARY_ADD, createPrimaryDTO.getUsername(), campaign, primaryGoal.toString());
        return new ResponseEntity<PrimaryDTO>(new PrimaryDTO(primaryGoal.getName(), primaryGoal.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PrimaryDTO> removePrimary(@RequestBody RemovePrimaryDTO removePrimaryDTO) {
        log.info("remove primary " + removePrimaryDTO);
        Campaign campaign = campaignService.getCampaign(removePrimaryDTO.getCampaignId());
        if (campaign == null) {
            log.warn("campaign not found");
            return new ResponseEntity<PrimaryDTO>(HttpStatus.BAD_REQUEST);
        }
        PrimaryGoal primaryGoal = primaryService.get(removePrimaryDTO.getPrimaryId());
        if (primaryGoal == null) {
            log.warn("primary not found");
            return new ResponseEntity<PrimaryDTO>(HttpStatus.NOT_FOUND);
        }
        PrimaryDTO dto = new PrimaryDTO(primaryGoal.getName(), primaryGoal.getId());
        logService.info(ActionType.PRIMARY_REMOVE, removePrimaryDTO.getUsername(), campaign, primaryGoal.toString());
        primaryService.delete(primaryGoal);
        return new ResponseEntity<PrimaryDTO>(dto, HttpStatus.OK);
    }



}
