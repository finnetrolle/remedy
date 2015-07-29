package ru.trollsmedjan.remedy.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.exception.*;
import ru.trollsmedjan.remedy.model.dao.BeaconRepository;
import ru.trollsmedjan.remedy.model.dao.EntoserRepository;
import ru.trollsmedjan.remedy.model.entity.*;

/**
 * Created by finnetrolle on 30.07.2015.
 */
@Service
public class BeaconServiceImpl implements BeaconService {

    @Autowired
    private OptionalDataProvider db;

    @Autowired
    private BeaconRepository beaconRepository;

    @Autowired
    private EngageService engageService;

    private static final Logger log = Logger.getLogger(BeaconServiceImpl.class);

    @Override
    public Beacon createBeacon(String name, Long primaryGoalId, String location) throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("Creating new beacon " + name + " in " + location + " for primary " + primaryGoalId);
        PrimaryGoal primaryGoal = db.getPrimaryGoal(primaryGoalId)
                .orElseThrow(() -> new PrimaryGoalNotFoundException());
        SolarSystem solarSystem = db.getSolarSystem(location)
                .orElseThrow(SolarSystemNotFoundException::new);
        if (db.isBeaconExists(name, primaryGoal, solarSystem)) {
            throw new RemedyServiceLayerException("Beacon with same name " + name + " already exists in solar system "
                    + solarSystem.getName() + " for primary " + primaryGoal.getName());
        }
        Beacon beacon = new Beacon();
        beacon.setCampaign(primaryGoal.getCampaign());
        beacon.setEntoser(null);
        beacon.setLocation(solarSystem);
        beacon.setName(name);
        beacon.setPrimaryGoal(primaryGoal);
        beacon.setStartTime(0);
        beacon.setTimeToCapture(0);
        beacon.setStatus(BeaconStatus.EMPTY);
        beaconRepository.save(beacon);
        log.debug("beacon created " + beacon);
        return beacon;
    }

    @Override
    public Long removeBeacon(long beaconId) throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("remove beacon " + beaconId);
        Beacon beacon = db.getBeacon(beaconId)
                .orElseThrow(BeaconNotFoundException::new);
        if (beacon.getEntoser() != null) {
            engageService.disengage(beaconId, beacon.getEntoser().getId());
        }
        beaconRepository.delete(beacon);
        return beaconId;
    }

    @Override
    public void reportEnemyAttack(Long beaconId) throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("Report enemy attack for " + beaconId);
        Beacon beacon = db.getBeacon(beaconId)
                .orElseThrow(BeaconNotFoundException::new);
        if (beacon.getStatus() == BeaconStatus.ATTACKED) {
            throw new RemedyServiceLayerException("Beacon " + beaconId + " is already under attack");
        }
        if (beacon.getEntoser() != null) {
            engageService.disengage(beacon.getEntoser().getId(), beaconId);
        }
        beacon.setStatus(BeaconStatus.ATTACKED);
        beaconRepository.save(beacon);
    }

    @Override
    public void reportBeaconDefended(Long beaconId) throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("Report beacon defended for " + beaconId);
        Beacon beacon = db.getBeacon(beaconId)
                .orElseThrow(BeaconNotFoundException::new);
        if (beacon.getStatus() != BeaconStatus.ATTACKED) {
            throw new RemedyServiceLayerException("Beacon " + beaconId + " should be under attack to be defended");
        }
        if (beacon.getEntoser() != null) {
            engageService.disengage(beacon.getEntoser().getId(), beaconId);
        }
        beacon.setStatus(BeaconStatus.EMPTY);
        beaconRepository.save(beacon);
    }
}
