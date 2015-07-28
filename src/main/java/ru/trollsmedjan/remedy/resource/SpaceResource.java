package ru.trollsmedjan.remedy.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.trollsmedjan.remedy.dto.ConstellationDTO;
import ru.trollsmedjan.remedy.dto.SolarSystemDTO;
import ru.trollsmedjan.remedy.dto.input.CreateConstellationDTO;
import ru.trollsmedjan.remedy.dto.input.CreateSolarSystemDTO;
import ru.trollsmedjan.remedy.model.entity.Constellation;
import ru.trollsmedjan.remedy.model.entity.SolarSystem;
import ru.trollsmedjan.remedy.service.SpaceService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by finnetrolle on 28.07.2015.
 */
@RestController
@RequestMapping("/space")
public class SpaceResource {

    @Autowired
    private SpaceService spaceService;

    @RequestMapping(value = "/constellations", method = RequestMethod.GET)
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

    @RequestMapping(value = "/constellations", method = RequestMethod.POST)
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

    @RequestMapping(value = "/solarsystems", method = RequestMethod.GET)
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

    @RequestMapping(value = "/solarsystems", method = RequestMethod.POST)
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
