package ru.trollsmedjan.remedy.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.request.EngageDTO;
import ru.trollsmedjan.remedy.exception.RemedyAuthException;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.dao.SessionRepository;
import ru.trollsmedjan.remedy.services.EngageService;
import ru.trollsmedjan.remedy.services.SessionService;

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

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/engage", method = RequestMethod.POST)
    @ResponseBody
    public Response engage(@PathVariable Long campaignId,
                           @RequestBody EngageDTO data, @RequestHeader("authToken") String token)
            throws RemedyDataLayerException, RemedyServiceLayerException, RemedyAuthException {
        log.debug("POST engage query {} to campaign {}", data, campaignId);

        if (!sessionService.canEngageBeacons(token)) {
            throw new RemedyAuthException("This user can't engage");
        }

        engageService.engage(data.getEntoserId(), data.getBeaconId());

        return Response.ok().build();
    }

    @RequestMapping(value = "/disengage", method = RequestMethod.POST)
    @ResponseBody
    public Response disengage(@PathVariable Long campaignId,
                              @RequestBody EngageDTO data, @RequestHeader("authToken") String token)
            throws RemedyDataLayerException, RemedyServiceLayerException, RemedyAuthException {
        log.debug("POST disengage query {} to campaign {}", data, campaignId);

        if (!sessionService.canEngageBeacons(token)) {
            throw new RemedyAuthException("This user can't disengage");
        }

        engageService.disengage(data.getEntoserId(), data.getBeaconId());

        return Response.ok().build();
    }
}
