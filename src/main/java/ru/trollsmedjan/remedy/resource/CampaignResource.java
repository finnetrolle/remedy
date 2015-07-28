package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.CampaignDTO;
import ru.trollsmedjan.remedy.dto.ConstellationDTO;
import ru.trollsmedjan.remedy.dto.input.StartCampaignDTO;
import ru.trollsmedjan.remedy.dto.input.StopCampaignDTO;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Constellation;
import ru.trollsmedjan.remedy.service.CampaignService;
import ru.trollsmedjan.remedy.service.SpaceService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@Api(basePath = "/campaign", value = "Campaigns", description = "Operations with campaigns", produces = "application/json")
@RestController
@RequestMapping("/campaign")
public class CampaignResource {

    private Set<String> authorisedUsers = new HashSet<>();
    private final static String KARER1 = "Karer I";
    private final static String KARER2 = "Karer II";
    private final static String KARER3 = "Karer III";
    private final static String KARER4 = "Karer IV";
    private final static String KARER5 = "Karer V";

    private final static String FINNETROLLE = "Finne Trolle";

    @PostConstruct
    private void init() {
        authorisedUsers.add(KARER1);
        authorisedUsers.add(KARER2);
        authorisedUsers.add(KARER3);
        authorisedUsers.add(KARER4);
        authorisedUsers.add(KARER5);
        authorisedUsers.add(FINNETROLLE);
    }

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private SpaceService spaceService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<CampaignDTO>> getCampaigns() {
        return new ResponseEntity<List<CampaignDTO>>(campaignService.getCampaigns()
                .stream()
                .map(c -> new CampaignDTO(c.getName(), c.getConstellation().getName(), c.getId()))
                .collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<CampaignDTO> startCampaign(@RequestBody StartCampaignDTO startCampaignDTO) {
        if (!checkHardcodedUser(startCampaignDTO.getUsername())) {
            return new ResponseEntity<CampaignDTO>(HttpStatus.UNAUTHORIZED);
        }
        Constellation constellation = spaceService.getConstellation(startCampaignDTO.getConstellation());
        if (constellation == null) {
            return new ResponseEntity<CampaignDTO>(HttpStatus.NOT_FOUND);
        }
        Campaign campaign = campaignService.createNewCampaign(startCampaignDTO.getName(), constellation);
        CampaignDTO campaignDTO = new CampaignDTO();

        return new ResponseEntity<CampaignDTO>(new CampaignDTO(campaign.getName(), campaign.getConstellation().getName(), campaign.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<CampaignDTO> stopCampaign(@RequestBody StopCampaignDTO stopCampaignDTO) {
        if (!checkHardcodedUser(stopCampaignDTO.getUsername())) {
            return new ResponseEntity<CampaignDTO>(HttpStatus.UNAUTHORIZED);
        }
        Campaign campaign = campaignService.getCampaign(stopCampaignDTO.getCampaignId());
        if (campaign == null) {
            return new ResponseEntity<CampaignDTO>(HttpStatus.BAD_REQUEST);
        }
        campaignService.stopCampaign(campaign);

        return new ResponseEntity<CampaignDTO>(HttpStatus.OK);
    }


    private boolean checkHardcodedUser(String username) {
        if (authorisedUsers.contains(username)) {
            return true;
        }
        return false;
    }

}
