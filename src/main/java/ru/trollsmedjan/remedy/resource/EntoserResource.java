package ru.trollsmedjan.remedy.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.EntoserDTO;
import ru.trollsmedjan.remedy.dto.request.AuthDTO;
import ru.trollsmedjan.remedy.dto.request.CreateEntoserDTO;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.*;
import ru.trollsmedjan.remedy.services.EntoserService;
import ru.trollsmedjan.remedy.services.OptionalDataProvider;


import javax.ws.rs.core.Response;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by finnetrolle on 28.07.2015.
 */
@RestController
@RequestMapping("/campaign/{campaignId}/entoser")
public class EntoserResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EntoserService entoserService;

    @Autowired
    private OptionalDataProvider db;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response getEntosers(@PathVariable Long campaignId) throws RemedyDataLayerException{
        log.info("GET list of entosers for campaign {}", campaignId);

        Campaign campaign = db.getCampaign(campaignId)
                .orElseThrow(RemedyDataLayerException::new);

        return Response.ok()
                .entity(db.findEntoserByCampaign(campaign).stream().map(entoserDTOFunction).collect(Collectors.toList()))
                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Response createOrUpdateEntoser(@PathVariable Long campaignId, @RequestBody CreateEntoserDTO data)
            throws RemedyDataLayerException, RemedyServiceLayerException {
        if (data.getId() == 0) {
            log.info("POST create entoser {} for campaign {}", data, campaignId);
            return Response.ok()
                    .entity(buildEntoserDTO(entoserService.createEntoser(data.getUsername(), data.getShip(), data.isT2EntosisModule(),
                            data.isCapitalShip(), campaignId)))
                    .build();
        } else {
            log.info("POST update entoser {} for campaign {}", data, campaignId);
            return Response.ok()
                    .entity(buildEntoserDTO(entoserService.updateEntoser(data.getId(), data.getUsername(), data.getShip(), data.isT2EntosisModule(),
                            data.isCapitalShip(), campaignId)))
                    .build();
        }




    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response removeEntoser(@PathVariable Long campaignId, @PathVariable Long id, @RequestBody AuthDTO auth)
            throws RemedyDataLayerException, RemedyServiceLayerException {
        log.info("POST remove entoser {} from campaign {} by {}", id, campaignId, auth);

        return Response.ok()
                .entity(entoserService.removeEntoser(id))
                .build();
    }

    private static EntoserDTO buildEntoserDTO(Entoser entoser) {
        EntoserDTO dto = new EntoserDTO();
        dto.setCapitalShip(entoser.isCapitalShip());
        if (entoser.getEngaging() != null) {
            dto.setEngaging(entoser.getEngaging().getName());
        }
        dto.setId(entoser.getId());
        dto.setName(entoser.getName());
        dto.setShip(entoser.getShip());
        dto.setT2EntosisModule(entoser.isT2EntosisModule());
        return dto;
    }

    private static Function<Entoser, EntoserDTO> entoserDTOFunction = e -> buildEntoserDTO(e);

}
