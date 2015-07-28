package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.ConstellationDTO;
import ru.trollsmedjan.remedy.dto.SolarSystemDTO;
import ru.trollsmedjan.remedy.dto.input.CreateConstellationDTO;
import ru.trollsmedjan.remedy.model.entity.Constellation;
import ru.trollsmedjan.remedy.service.SpaceService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Api(basePath = "/constellations", value = "constellations", description = "Operations with constellations", produces = "application/json")
@RestController
@RequestMapping("/constellations")
public class ConstellationResource {

    @Autowired
    private SpaceService spaceService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<ConstellationDTO> getConstellations() {
        return spaceService.getAllConstellations().stream()
                .map(constellationToDTO)
                .collect(Collectors.toList());
    }

    private static Function<Constellation, ConstellationDTO> constellationToDTO = c -> {
        ConstellationDTO dto = new ConstellationDTO();
        dto.setName(c.getName());
        dto.setSystemList(c.getSolarSystems()
                .stream()
                .map(s -> new SolarSystemDTO(s.getName()))
                .collect(Collectors.toList()));
        return dto;
    };

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<ConstellationDTO> createConstellation(@RequestBody CreateConstellationDTO constellationDTO) {
        Constellation constellation = spaceService.getConstellation(constellationDTO.getName());
        if (constellation != null) {
            return new ResponseEntity<ConstellationDTO>(HttpStatus.BAD_REQUEST);
        }
        constellation = new Constellation();
        constellation.setName(constellationDTO.getName());
        spaceService.saveConstellation(constellation);
        return new ResponseEntity<ConstellationDTO>(HttpStatus.MULTI_STATUS.OK);
    }

    @RequestMapping(value = "/constellations/{name}/remove", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<ConstellationDTO> removeConstellation(@RequestBody ConstellationDTO constellationDTO) {
        Constellation constellation = spaceService.getConstellation(constellationDTO.getName());
        if (constellation == null) {
            return new ResponseEntity<ConstellationDTO>(HttpStatus.BAD_REQUEST);
        }
        spaceService.removeConstellation(constellation);
        return new ResponseEntity<ConstellationDTO>(HttpStatus.OK);
    }

}
