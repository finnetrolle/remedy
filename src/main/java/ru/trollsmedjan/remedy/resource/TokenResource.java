package ru.trollsmedjan.remedy.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.TokenDTO;
import ru.trollsmedjan.remedy.dto.request.CreateTokenDTO;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Session;
import ru.trollsmedjan.remedy.model.entity.User;
import ru.trollsmedjan.remedy.services.OptionalDataProvider;
import ru.trollsmedjan.remedy.services.SessionService;
import ru.trollsmedjan.remedy.services.UserService;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.UUID;

/**
 * Created by finnetrolle on 08.08.2015.
 */

@RestController
@RequestMapping(value = "/token")
public class TokenResource {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private OptionalDataProvider db;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public TokenDTO createToken(@RequestBody CreateTokenDTO data) throws RemedyServiceLayerException, RemedyDataLayerException {
        if (!data.getKeyword().equals("Aungverdality")) {
            throw new WebApplicationException(new Exception(), Response.Status.BAD_REQUEST);
        }

        User user = userService.getUser(data.getUsername());
        if (user == null) {
            user = userService.createDefaultUser(data.getUsername());
        }

        Session session;
        Campaign campaign = db
                .getCampaign(data.getCampaignId()).orElse(null);
        if (db == null) {
            session = sessionService.createSession(user);
        } else {
            session = sessionService.createSession(user, campaign);
        }

        TokenDTO token = new TokenDTO();
        token.setUsername(user.getUsername());
        token.setCreated(new Date());
        token.setToken(session.getToken().toString());
        return token;
    }


}
