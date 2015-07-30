package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.CampaignDTO;
import ru.trollsmedjan.remedy.dto.request.AuthDTO;
import ru.trollsmedjan.remedy.dto.request.StartCampaignDTO;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.services.CampaignService;
import ru.trollsmedjan.remedy.services.OptionalDataProvider;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@Api(basePath = "/campaign", value = "Campaigns", description = "Operations with campaigns", produces = "application/json")
@RestController
@RequestMapping("/campaign")
public class CampaignResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
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
                .entity(db.findCampaigns().stream().map(c -> new CampaignDTO(c.getName(), c.getConstellation().getName(), c.getId())).collect(Collectors.toList()))
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response stopCampaign(@PathVariable Long id, @RequestBody AuthDTO data)
            throws RemedyDataLayerException, RemedyServiceLayerException {
        log.info("Stopping campaign " + id);

        if (!checkHardcodedUser(data.getUsername())) {
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
