package ru.trollsmedjan.remedy.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.SolarSystemDTO;
import ru.trollsmedjan.remedy.dto.request.CreateSolarSystemDTO;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.services.OptionalDataProvider;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/constellation/{constname}/solarsystem")
public class SolarSystemResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OptionalDataProvider db;

    @RequestMapping(value = "/{solarname}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response removeSolarSystem(@PathVariable String constname, @PathVariable String solarname)
            throws RemedyDataLayerException {
        log.info("POST remove solar system {} from {}", solarname, constname);

        return Response.ok()
                .entity(db.removeSolarSystem(solarname))
                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Response createSolarSystem(@PathVariable String constname, @RequestBody CreateSolarSystemDTO data)
            throws  RemedyDataLayerException {
        log.info("POST create solar system {} in {}", data.getSolarSystemName(), constname);

        return Response.ok()
                .entity(new SolarSystemDTO(db.createSolarSystem(data.getSolarSystemName(), constname)))
                .build();
    }

}
