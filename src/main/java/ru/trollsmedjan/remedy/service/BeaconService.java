package ru.trollsmedjan.remedy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.model.dao.BeaconRepository;
import ru.trollsmedjan.remedy.model.entity.Beacon;
import ru.trollsmedjan.remedy.model.entity.Campaign;

import java.util.List;

/**
 * Created by finnetrolle on 28.07.2015.
 */
@Service
public class BeaconService {

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
}
