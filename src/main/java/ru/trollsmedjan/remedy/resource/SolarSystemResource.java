package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.SolarSystemDTO;
import ru.trollsmedjan.remedy.dto.input.CreateSolarSystemDTO;
import ru.trollsmedjan.remedy.model.entity.Constellation;
import ru.trollsmedjan.remedy.model.entity.SolarSystem;
import ru.trollsmedjan.remedy.service.SpaceService;

import java.util.List;
import java.util.stream.Collectors;

@Api(basePath = "/solarsystems", value = "solarsystems", description = "Operations with solarsystems", produces = "application/json")
@RestController
@RequestMapping("solarsystems")
public class SolarSystemResource {

    @Autowired
    private SpaceService spaceService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<SolarSystemDTO> getSolarSystems() {
        return spaceService.getAllSolarSystems()
                .stream()
                .map(s -> new SolarSystemDTO(s.getName()))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/solarsystems/{constname}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<SolarSystemDTO>> getSolarSystems(@PathVariable String constname) {
        Constellation constellation = spaceService.getConstellation(constname);
        if (constellation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<SolarSystemDTO>>(spaceService.getSolarSystems(constellation)
                .stream()
                .map(s -> new SolarSystemDTO(s.getName()))
                .collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<SolarSystemDTO> createSolarSystem(@RequestBody CreateSolarSystemDTO solarSystemDTO) {
        Constellation constellation = spaceService.getConstellation(solarSystemDTO.getConstellation());
        if (constellation == null) {
            return new ResponseEntity<SolarSystemDTO>(HttpStatus.NOT_FOUND);
        }
        SolarSystem solarSystem = spaceService.getSolarSystem(solarSystemDTO.getName());
        if (solarSystem != null) {
            return new ResponseEntity<SolarSystemDTO>(HttpStatus.BAD_REQUEST);
        }
        solarSystem = new SolarSystem();
        solarSystem.setConstellation(constellation);
        solarSystem.setName(solarSystemDTO.getName());
        constellation.getSolarSystems().add(solarSystem);
        spaceService.saveConstellation(constellation);
        spaceService.saveSolarSystem(solarSystem);
        return new ResponseEntity<SolarSystemDTO>(HttpStatus.OK);
    }

}
