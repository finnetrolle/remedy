package ru.trollsmedjan.remedy.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.request.EngageDTO;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.services.EngageService;

import javax.ws.rs.core.Response;

/**
 * Created by finnetrolle on 31.07.2015.
 */
@RestController
@RequestMapping("/campaign/{campaignId}")
public class EngageResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EngageService engageService;

    @RequestMapping(value = "/engage", method = RequestMethod.POST)
    @ResponseBody
    public Response engage(@PathVariable Long campaignId,
                           @RequestBody EngageDTO data)
            throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("POST engage query {} to campaign {}", data, campaignId);

        engageService.engage(data.getEntoserId(), data.getBeaconId());

        return Response.ok().build();
    }

    @RequestMapping(value = "/disengage", method = RequestMethod.POST)
    @ResponseBody
    public Response disengage(@PathVariable Long campaignId,
                              @RequestBody EngageDTO data)
            throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("POST disengage query {} to campaign {}", data, campaignId);

        engageService.disengage(data.getEntoserId(), data.getBeaconId());

        return Response.ok().build();
    }
}
