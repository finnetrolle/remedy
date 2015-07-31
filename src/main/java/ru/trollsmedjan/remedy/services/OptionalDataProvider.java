package ru.trollsmedjan.remedy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.model.dao.*;
import ru.trollsmedjan.remedy.model.entity.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by finnetrolle on 30.07.2015.
 */
@Service
public class OptionalDataProvider {

    @Autowired
    private BeaconRepository beaconRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private EntoserRepository entoserRepository;

    @Autowired
    private PrimaryGoalRepository primaryGoalRepository;

    @Autowired
    private ConstellationRepository constellationRepository;

    @Autowired
    private SolarSystemRepository solarSystemRepository;

    public Optional<Constellation> getConstellation(String name) {
        return Optional.ofNullable(constellationRepository.findOne(name));
    }

    public Optional<Campaign> getCampaign(Long id) {
        return Optional.ofNullable(campaignRepository.findOne(id));
    }

    public Optional<PrimaryGoal> getPrimaryGoal(Long id) {
        return Optional.ofNullable(primaryGoalRepository.findOne(id));
    }

    public Optional<Entoser> getEntoser(String name, Campaign campaign) {
        return Optional.ofNullable(entoserRepository.findOneByNameAndCampaign(name, campaign));
    }

    public boolean isEntoserExists(String name, Campaign campaign) {
        Entoser entoser = entoserRepository.findOneByNameAndCampaign(name, campaign);
        if (entoser == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isBeaconExists(String name, PrimaryGoal primaryGoal, SolarSystem solarSystem) {
        Beacon beacon = beaconRepository.findOneByNameAndPrimaryGoalAndLocation(name, primaryGoal, solarSystem);
        if (beacon == null) {
            return false;
        }
        return true;
    }

    public Optional<Entoser> getEntoser(Long id) {
        return Optional.ofNullable(entoserRepository.findOne(id));
    }

    public Optional<Beacon> getBeacon(Long id) {
        return Optional.ofNullable(beaconRepository.findOne(id));
    }

    public Optional<SolarSystem> getSolarSystem(String name) {
        return Optional.ofNullable(solarSystemRepository.findOne(name));
    }

    public List<PrimaryGoal> findPrimaryByCampaign(Campaign campaign) {
        return primaryGoalRepository.findByCampaign(campaign);
    }

    public List<Entoser> findEntoserByCampaign(Campaign campaign) {
        return entoserRepository.findByCampaign(campaign);
    }

    public List<Beacon> findBeaconsByPrimary(PrimaryGoal primaryGoal) {
        return beaconRepository.findByPrimaryGoal(primaryGoal);
    }

    public List<Campaign> findCampaigns() {
        return campaignRepository.findAll();
    }

    public List<Constellation> findConstellations() {
        return constellationRepository.findAll();
    }

    public Constellation createConstellation(String constellationName) throws RemedyDataLayerException {
        Constellation constellation = constellationRepository.findOne(constellationName);
        if (constellation != null) {
            throw new RemedyDataLayerException();
        }
        constellation = new Constellation();
        constellation.setName(constellationName);
        return constellationRepository.save(constellation);
    }

    public String removeConstellation(String name) throws RemedyDataLayerException {
        Constellation constellation = getConstellation(name)
                .orElseThrow(RemedyDataLayerException::new);
        List<SolarSystem> solars = solarSystemRepository.findByConstellation(constellation);
        for (SolarSystem s : solars) {
            removeSolarSystem(s.getName());
        }
        constellationRepository.delete(constellation);
        return name;
    }

    public String createSolarSystem(String solarName, String constellationName) throws  RemedyDataLayerException {
        SolarSystem solarSystem = solarSystemRepository.findOne(solarName);
        if (solarSystem != null) {
            throw new RemedyDataLayerException();
        }
        solarSystem = new SolarSystem();
        solarSystem.setName(solarName);
        solarSystem.setConstellation(getConstellation(constellationName)
                .orElseThrow(() -> new RemedyDataLayerException()));
        solarSystemRepository.save(solarSystem);
        return solarName;
    }

    public String removeSolarSystem(String solarName) throws RemedyDataLayerException {
        SolarSystem solarSystem = solarSystemRepository.findOne(solarName);
        if (solarName == null) {
            throw new RemedyDataLayerException();
        }
        solarSystemRepository.delete(solarSystem);
        return solarName;
    }
}
