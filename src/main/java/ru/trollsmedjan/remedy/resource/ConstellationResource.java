package ru.trollsmedjan.remedy.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.ConstellationDTO;
import ru.trollsmedjan.remedy.dto.request.CreateConstellationDTO;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.model.entity.Constellation;
import ru.trollsmedjan.remedy.model.entity.SolarSystem;
import ru.trollsmedjan.remedy.services.OptionalDataProvider;

import javax.ws.rs.core.Response;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/constellation")
public class ConstellationResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OptionalDataProvider db;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response getConstellations() throws RemedyDataLayerException {
        log.info("GET constellations");

        return Response.ok()
                .entity(db.findConstellations().stream().map(constellationsDTOFunction).collect(Collectors.toList()))
                .build();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Response getConstellation(@PathVariable String name) throws RemedyDataLayerException{
        log.info("GET constellation {}", name);

        return Response.ok()
                .entity(buildConstellationDTO(db.getConstellation(name)
                        .orElseThrow(RemedyDataLayerException::new)))
                .build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Response createConstellation(@RequestBody CreateConstellationDTO data) throws RemedyDataLayerException {
        log.info("POST create constellation {} by {}", data.getConstellationName(), data.getUsername());

        return Response.ok()
                .entity(buildConstellationDTO(db.createConstellation(data.getConstellationName())))
                .build();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response removeConstellation(@PathVariable String name) throws RemedyDataLayerException {
        log.info("POST remove constellation {}", name);

        return Response.ok()
                .entity(db.removeConstellation(name))
                .build();
    }

    private static Function<Constellation, ConstellationDTO> constellationsDTOFunction =
            c -> new ConstellationDTO(c.getName());

    private static ConstellationDTO buildConstellationDTO(Constellation constellation) {
        if (constellation.getSolarSystems() != null) {
            return new ConstellationDTO(constellation.getName(), constellation.getSolarSystems()
                    .stream()
                    .map(solarSystemStringFunction)
                    .collect(Collectors.toList()));
        } else {
            return new ConstellationDTO(constellation.getName(), null);
        }

    }

    public static Function<SolarSystem, String> solarSystemStringFunction =
            s -> s.getName();

}
