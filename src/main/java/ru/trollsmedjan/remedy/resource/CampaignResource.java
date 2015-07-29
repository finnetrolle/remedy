package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.CampaignDTO;
import ru.trollsmedjan.remedy.dto.input.StartCampaignDTO;
import ru.trollsmedjan.remedy.dto.input.StopCampaignDTO;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.ActionType;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Constellation;
import ru.trollsmedjan.remedy.services.CampaignService;
import ru.trollsmedjan.remedy.services.OptionalDataProvider;
import sun.awt.SunToolkit;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import java.util.Arrays;
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

    private static final Logger log = Logger.getLogger(CampaignResource.class);
    
    private final static String[] ALLOWED = new String[]{
        "Karer I","Karer II","Karer III","Karer IV","Karer V","Finne Trolle"
    };

    private final static Set<String> authorisedUsers = new HashSet<>();

    @PostConstruct
    private void init() {
        authorisedUsers.addAll(Arrays.asList(ALLOWED));
    }

    @Autowired
    private OptionalDataProvider db;

    @Autowired
    private CampaignService campaignService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response getCampaigns() {
        log.info("GET campaigns");
        return Response.ok()
                .entity(db.findCampaigns())
                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Response startCampaign(@RequestBody StartCampaignDTO startCampaignDTO) throws RemedyDataLayerException {
        log.info("start campaign " + startCampaignDTO);

        if (!checkHardcodedUser(startCampaignDTO.getUsername())) {
            log.debug("UNAUTHORIZED");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Campaign campaign = campaignService.createCampaign(startCampaignDTO.getName(), startCampaignDTO.getConstellation());
        return Response.ok()
                .entity(new CampaignDTO(campaign.getName(), campaign.getConstellation().getName(), campaign.getId()))
                .build();
    }

    @RequestMapping(value = "/{id}/stop", method = RequestMethod.POST)
    @ResponseBody
    public Response stopCampaign(@PathVariable Long id, @RequestBody StopCampaignDTO stopCampaignDTO)
            throws RemedyDataLayerException, RemedyServiceLayerException {
        log.info("Stopping campaign " + stopCampaignDTO);

        if (!checkHardcodedUser(stopCampaignDTO.getUsername())) {
            log.debug("UNAUTHORIZED");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        campaignService.stopCampaign(id);

        return Response.ok()
                .entity(id)
                .build();
    }


    private boolean checkHardcodedUser(String username) {
        if (authorisedUsers.contains(username)) {
            return true;
        }
        return false;
    }

}
