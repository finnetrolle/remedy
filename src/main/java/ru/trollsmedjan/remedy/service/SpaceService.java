package ru.trollsmedjan.remedy.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.model.dao.ConstellationRepository;
import ru.trollsmedjan.remedy.model.dao.SolarSystemRepository;
import ru.trollsmedjan.remedy.model.entity.Constellation;
import ru.trollsmedjan.remedy.model.entity.SolarSystem;

import java.util.Collection;
import java.util.List;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@Service
public class SpaceService {

    private static final Logger log = Logger.getLogger(SpaceService.class);

    @Autowired
    private ConstellationRepository constellationRepository;

    @Autowired
    private SolarSystemRepository solarSystemRepository;

    public Constellation getConstellation(String name) {
        return constellationRepository.findOne(name);
    }

    public SolarSystem getSolarSystem(String name) {
        return solarSystemRepository.findOne(name);
    }

    public Collection<SolarSystem> getSolarSystems(Constellation constellation) {
        return solarSystemRepository.findByConstellation(constellation);
    }

    public void saveConstellation(Constellation constellation) {
        constellationRepository.save(constellation);
    }

    public void removeConstellation(Constellation constellation) {
        constellationRepository.delete(constellation);
    }

    public List<Constellation> getAllConstellations() {
        return constellationRepository.findAll();
    }

    public void saveSolarSystem(SolarSystem solarSystem) {
        solarSystemRepository.save(solarSystem);
    }

    public List<SolarSystem> getAllSolarSystems() {
        return solarSystemRepository.findAll();
    }
}
