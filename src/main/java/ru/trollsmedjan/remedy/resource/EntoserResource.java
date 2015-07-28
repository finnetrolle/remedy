package ru.trollsmedjan.remedy.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.EntoserDTO;
import ru.trollsmedjan.remedy.dto.input.BaseEntoserData;
import ru.trollsmedjan.remedy.dto.input.ExtendedEntoserData;
import ru.trollsmedjan.remedy.model.entity.Beacon;
import ru.trollsmedjan.remedy.model.entity.BeaconStatus;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Entoser;
import ru.trollsmedjan.remedy.service.BeaconService;
import ru.trollsmedjan.remedy.service.CampaignService;
import ru.trollsmedjan.remedy.service.EntoserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by finnetrolle on 28.07.2015.
 */
@RestController
@RequestMapping("/entosers")
public class EntoserResource {

    @Autowired
    private EntoserService entoserService;

    @Autowired
    private BeaconService beaconService;

    @Autowired
    private CampaignService campaignService;

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<EntoserDTO> unregisterEntoser(@RequestBody BaseEntoserData data) {

        Campaign campaign = campaignService.getCampaign(data.getCampaignId());
        if (campaign == null) {
            return getBadRequest();
        }

        Entoser entoser = entoserService.getEntoser(data.getId());
        if (entoser == null) {
            return getBadRequest();
        }

        Beacon beacon = entoser.getEngaging();
        if (beacon != null) {
            beacon.setStatus(BeaconStatus.EMPTY);
            beacon.setTimeToCapture(0);
            beacon.setStartTime(0);
            beaconService.save(beacon);
        }

        entoserService.remove(entoser);

        return new ResponseEntity<EntoserDTO>(getEntoserDTO(entoser), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<EntoserDTO> registerEntoser(@RequestBody ExtendedEntoserData data) {

        Campaign campaign = campaignService.getCampaign(data.getCampaignId());
        if (campaign == null) {
            return getBadRequest();
        }

        Entoser entoser = entoserService.getEntoser(data.getUsername(), campaign);
        if (entoser != null) {
            return getBadRequest();
        }

        entoser = new Entoser();
        entoser.setCampaign(campaign);
        entoser.setCapitalShip(data.isCapitalShip());
        entoser.setEngaging(null);
        entoser.setName(data.getUsername());
        entoser.setShip(data.getShip());
        entoser.setT2EntosisModule(data.isT2EntosisModule());
        entoserService.save(entoser);

        return new ResponseEntity<EntoserDTO>(getEntoserDTO(entoser), HttpStatus.OK);
    }

    @RequestMapping(value = "/{campaignid}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<EntoserDTO>> getEntosers(@PathVariable Long campaignid) {
        Campaign campaign = campaignService.getCampaign(campaignid);
        if (campaign == null) {
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
