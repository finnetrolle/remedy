package ru.trollsmedjan.remedy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return Optional.of(constellationRepository.findOne(name));
    }

    public Optional<Campaign> getCampaign(Long id) {
        return Optional.of(campaignRepository.findOne(id));
    }

    public Optional<PrimaryGoal> getPrimaryGoal(Long id) {
        return Optional.of(primaryGoalRepository.findOne(id));
    }

    public Optional<Entoser> getEntoser(String name, Campaign campaign) {
        return Optional.of(entoserRepository.findOneByNameAndCampaign(name, campaign));
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
        Beacon beacon = beaconRepository.findOneByNameAndPrimaryGoalAndSolarSystem(name, primaryGoal, solarSystem);
        if (beacon == null) {
            return false;
        }
        return true;
    }

    public Optional<Entoser> getEntoser(Long id) {
        return Optional.of(entoserRepository.findOne(id));
    }

    public Optional<Beacon> getBeacon(Long id) {
        return Optional.of(beaconRepository.findOne(id));
    }

    public Optional<SolarSystem> getSolarSystem(String name) {
        return Optional.of(solarSystemRepository.findOne(name));
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
    
}
