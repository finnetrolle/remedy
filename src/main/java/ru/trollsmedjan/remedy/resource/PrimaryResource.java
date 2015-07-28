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
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Primary;
import ru.trollsmedjan.remedy.service.BeaconService;
import ru.trollsmedjan.remedy.service.CampaignService;
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

    @RequestMapping(value = "/{campaignid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<PrimaryDTO>> getPrimaries(@PathVariable Long campaignid) {
        Campaign campaign = campaignService.getCampaign(campaignid);
        if (campaign == null) {
            return new ResponseEntity<List<PrimaryDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<PrimaryDTO>>(primaryService.get(campaign)
                .stream()
                .map(p -> new PrimaryDTO(p.getName(), p.getId()))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{campaignid}/{primaryid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<PrimaryDTO>> getPrimaries(@PathVariable Long campaignid, @PathVariable Long primaryid) {
        Campaign campaign = campaignService.getCampaign(campaignid);
        if (campaign == null) {
            return new ResponseEntity<List<PrimaryDTO>>(HttpStatus.NOT_FOUND);
        }
        Primary primary = primaryService.get(primaryid);
        if (primary == null) {
            return new ResponseEntity<List<PrimaryDTO>>(HttpStatus.NOT_FOUND);
        }
        List<BeaconDTO> beaconDTOs = beaconService.getBeacons(primary)
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
        Primary primary = primaryService.get(createPrimaryDTO.getName(), campaign);
        if (primary != null) {
            return new ResponseEntity<PrimaryDTO>(HttpStatus.BAD_REQUEST);
        }
        primary = new Primary();
        primary.setName(createPrimaryDTO.getName());
        primaryService.save(primary);
        return new ResponseEntity<PrimaryDTO>(new PrimaryDTO(primary.getName(), primary.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PrimaryDTO> removePrimary(@RequestBody RemovePrimaryDTO removePrimaryDTO) {
        Campaign campaign = campaignService.getCampaign(removePrimaryDTO.getCampaignId());
        if (campaign == null) {
            return new ResponseEntity<PrimaryDTO>(HttpStatus.BAD_REQUEST);
        }
        Primary primary = primaryService.get(removePrimaryDTO.getPrimaryId());
        if (primary == null) {
            return new ResponseEntity<PrimaryDTO>(HttpStatus.NOT_FOUND);
        }
        primaryService.delete(primary);
        return new ResponseEntity<PrimaryDTO>(HttpStatus.OK);
    }



}
