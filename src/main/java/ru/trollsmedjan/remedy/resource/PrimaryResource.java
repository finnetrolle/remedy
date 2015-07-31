package ru.trollsmedjan.remedy.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.PrimaryDTO;
import ru.trollsmedjan.remedy.dto.request.AuthDTO;
import ru.trollsmedjan.remedy.dto.request.CreatePrimaryDTO;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.PrimaryGoal;
import ru.trollsmedjan.remedy.resource.exception.entity.CampaignNotFoundException;
import ru.trollsmedjan.remedy.services.OptionalDataProvider;
import ru.trollsmedjan.remedy.services.PrimaryGoalService;

import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

/**
 * Created by finnetrolle on 29.07.2015.
 */
@RestController
@RequestMapping("/campaign/{campaignid}/primary")
public class PrimaryResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PrimaryGoalService primaryGoalService;

    @Autowired
    private OptionalDataProvider db;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response getPrimaries(@PathVariable Long campaignid) throws RemedyDataLayerException {
        log.info("GET primaries for {}", campaignid);

        return Response.ok()
                .entity(db.findPrimaryByCampaign(db.getCampaign(campaignid)
                        .orElseThrow(CampaignNotFoundException::new))
                        .stream()
                        .map(p -> new PrimaryDTO(p.getName(), p.getId()))
                        .collect(Collectors.toList()))
                .build();
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public Response getPrimary(@PathVariable Long campaignid, @PathVariable Long id) throws RemedyDataLayerException {
//        log.info("GET primary {} for {}", id, campaignid);
//
//        PrimaryGoal p = db.getPrimaryGoal(id).orElseThrow(RemedyDataLayerException::new);
//
//        return Response.ok()
//                .entity(new PrimaryDTO(p.getName(), p.getId()))
//                .build();
//    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Response createPrimary(@PathVariable Long campaignid, @RequestBody CreatePrimaryDTO data)
            throws RemedyDataLayerException{
        log.info("POST create primary {} in campaign {}", data, campaignid);

        PrimaryGoal p = primaryGoalService.createPrimaryGoal(data.getPrimaryName(), campaignid);

        return Response.ok()
                .entity(new PrimaryDTO(p.getName(), p.getId()))
                .build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response removePrimary(@PathVariable Long campaignid, @PathVariable Long id, @RequestBody AuthDTO data)
            throws RemedyDataLayerException, RemedyServiceLayerException {
        log.info("POST remove primary {} from campaign {} by {}", id, campaignid, data);

        return Response.ok()
                .entity(primaryGoalService.removePrimaryGoal(id))
                .build();
    }

}
