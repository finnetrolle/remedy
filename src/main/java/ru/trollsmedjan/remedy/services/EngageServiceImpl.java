package ru.trollsmedjan.remedy.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.dao.BeaconRepository;
import ru.trollsmedjan.remedy.model.dao.EntoserRepository;
import ru.trollsmedjan.remedy.model.entity.Beacon;
import ru.trollsmedjan.remedy.model.entity.BeaconStatus;
import ru.trollsmedjan.remedy.model.entity.Entoser;
import ru.trollsmedjan.remedy.oldservice.BeaconUtils;
import ru.trollsmedjan.remedy.resource.exception.entity.BeaconNotFoundException;
import ru.trollsmedjan.remedy.resource.exception.entity.EntoserNotFoundException;

/**
 * Created by finnetrolle on 30.07.2015.
 */
@Service
public class EngageServiceImpl implements EngageService {

    @Autowired
    private OptionalDataProvider db;

    @Autowired
    private BeaconRepository beaconRepository;

    @Autowired
    private EntoserRepository entoserRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void engage(Long entoserId, Long beaconId) throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("engage entoser " + entoserId + " on " + beaconId);
        Entoser entoser = db.getEntoser(entoserId)
                .orElseThrow(EntoserNotFoundException::new);
        Beacon beacon = db.getBeacon(beaconId)
                .orElseThrow(BeaconNotFoundException::new);
        if (entoser.getEngaging() != null) {
            throw new RemedyServiceLayerException("Entoser is already engaging " + entoser.getEngaging().getName());
        }
        if (beacon.getEntoser() != null) {
            throw new RemedyServiceLayerException("Beacon is already engaged by " + beacon.getEntoser().getName());
        }
        if (beacon.getStatus() != BeaconStatus.EMPTY) {
            throw new RemedyServiceLayerException("Beacon statis " + beacon.getStatus() + " can not afford to engage");
        }
        if (!beacon.getCampaign().equals(entoser.getCampaign())) {
            throw new RemedyServiceLayerException("Beacon and entoser have different campaigns");
        }
        entoser.setEngaging(beacon);
        beacon.setEntoser(entoser);
        beacon.setStartTime(System.currentTimeMillis());
        beacon.setTimeToCapture(BeaconUtils.getTimeToEntose(entoser.isT2EntosisModule(), entoser.isCapitalShip(), 2.5));
        beacon.setStatus(BeaconStatus.ENGAGED);
        beaconRepository.save(beacon);
        entoserRepository.save(entoser);
        log.debug("Engage " + entoser + " on " + beacon + " is complete");
    }

    @Override
    public void disengage(Long entoserId, Long beaconId) throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("disengage entoser " + entoserId + " from " + beaconId);
        Entoser entoser = db.getEntoser(entoserId)
                .orElseThrow(EntoserNotFoundException::new);
        Beacon beacon = db.getBeacon(beaconId)
                .orElseThrow(BeaconNotFoundException::new);
        if (!entoser.getEngaging().equals(beacon) || !beacon.getEntoser().equals(entoser)) {
            throw new RemedyServiceLayerException("Entoser " + entoserId + " engaging not beacon " + beaconId);
        }
        if (!beacon.getCampaign().equals(entoser.getCampaign())) {
            throw new RemedyServiceLayerException("Beacon and entoser have different campaigns");
        }
        entoser.setEngaging(null);
        beacon.setEntoser(null);
        beacon.setStartTime(0);
        beacon.setStatus(BeaconStatus.EMPTY);
        beacon.setTimeToCapture(0);
        beaconRepository.save(beacon);
        entoserRepository.save(entoser);
        log.debug("Disengage " + entoser + " from " + beacon + " is complete");
    }
}
