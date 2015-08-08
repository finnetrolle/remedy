package ru.trollsmedjan.remedy.resource;

import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.TokenDTO;
import ru.trollsmedjan.remedy.dto.request.CreateTokenDTO;

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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public TokenDTO createToken(@RequestBody CreateTokenDTO data) {
        TokenDTO token = new TokenDTO();
        if (data.getKeyword().equals("Aungverdality")) {
            token.setUsername(data.getUsername());
            token.setCreated(new Date());
            token.setTimeToLive(new Date());
            token.setToken(UUID.randomUUID().toString());
            return token;
        } else {
            throw new WebApplicationException(new Exception(), Response.Status.BAD_REQUEST);
        }
    }

}
