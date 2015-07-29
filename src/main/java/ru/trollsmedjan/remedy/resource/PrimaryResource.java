package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
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
        Campaign campaign = campaignService.getCampaign(campaignid);
        if (campaign == null) {
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
        Campaign campaign = campaignService.getCampaign(campaignid);
        if (campaign == null) {
            return new ResponseEntity<List<PrimaryDTO>>(HttpStatus.NOT_FOUND);
        }
        PrimaryGoal primaryGoal = primaryService.get(primaryid);
        if (primaryGoal == null) {
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
        Campaign campaign = campaignService.getCampaign(createPrimaryDTO.getCampaignId());
        if (campaign == null) {
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
        Campaign campaign = campaignService.getCampaign(removePrimaryDTO.getCampaignId());
        if (campaign == null) {
            return new ResponseEntity<PrimaryDTO>(HttpStatus.BAD_REQUEST);
        }
        PrimaryGoal primaryGoal = primaryService.get(removePrimaryDTO.getPrimaryId());
        if (primaryGoal == null) {
            return new ResponseEntity<PrimaryDTO>(HttpStatus.NOT_FOUND);
        }
        PrimaryDTO dto = new PrimaryDTO(primaryGoal.getName(), primaryGoal.getId());
        logService.info(ActionType.PRIMARY_REMOVE, removePrimaryDTO.getUsername(), campaign, primaryGoal.toString());
        primaryService.delete(primaryGoal);
        return new ResponseEntity<PrimaryDTO>(dto, HttpStatus.OK);
    }



}
