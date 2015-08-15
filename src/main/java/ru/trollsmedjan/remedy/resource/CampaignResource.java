package ru.trollsmedjan.remedy.resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.CampaignDTO;
import ru.trollsmedjan.remedy.dto.request.AuthDTO;
import ru.trollsmedjan.remedy.dto.request.StartCampaignDTO;
import ru.trollsmedjan.remedy.exception.RemedyAuthException;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.services.CampaignService;
import ru.trollsmedjan.remedy.services.OptionalDataProvider;
import ru.trollsmedjan.remedy.services.SessionService;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private SessionService sessionService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response getCampaigns(@RequestHeader("authToken") String token) {
        log.info("GET campaigns");

        return Response.ok()
                .entity(db.findCampaigns().stream().map(c -> new CampaignDTO(c.getName(), c.getConstellation().getName(), c.getId())).collect(Collectors.toList()))
                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Response startCampaign(@RequestBody StartCampaignDTO startCampaignDTO, @RequestHeader("authToken") String token) throws RemedyDataLayerException, RemedyAuthException {
        log.info("start campaign " + startCampaignDTO);

        if (!sessionService.canCreateCampaigns(token)) {
            throw new RemedyAuthException("This user can't create campaign");
        }


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
    public Response stopCampaign(@PathVariable Long id, @RequestBody AuthDTO data, @RequestHeader("authToken") String token)
            throws RemedyDataLayerException, RemedyServiceLayerException, RemedyAuthException {
        log.info("Stopping campaign " + id);

        if (!sessionService.canDeleteCampaigns(token)) {
            throw new RemedyAuthException("This user can't stop campaigns");
        }

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
