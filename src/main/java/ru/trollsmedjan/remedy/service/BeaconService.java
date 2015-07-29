package ru.trollsmedjan.remedy.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.model.dao.BeaconRepository;
import ru.trollsmedjan.remedy.model.entity.Beacon;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.PrimaryGoal;

import java.util.List;

/**
 * Created by finnetrolle on 28.07.2015.
 */
@Service
public class BeaconService {

    private static final Logger log = Logger.getLogger(BeaconService.class);

    @Autowired
    private BeaconRepository beaconRepository;

    public void save(Beacon beacon) {
        beaconRepository.save(beacon);
    }

    public Beacon getBeacon(long id) {
        return beaconRepository.findOne(id);
    }

    public void delete(Beacon beacon) {
        beaconRepository.delete(beacon);
    }

    public List<Beacon> getBeacons(Campaign campaign) {
        return beaconRepository.findByCampaign(campaign);
    }

    public List<Beacon> getBeacons(PrimaryGoal primaryGoal) {
        return beaconRepository.findByPrimaryGoal(primaryGoal);
    }
}
