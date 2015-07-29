package ru.trollsmedjan.remedy.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.exception.CampaignNotFoundException;
import ru.trollsmedjan.remedy.exception.PrimaryGoalNotFoundException;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.dao.PrimaryGoalRepository;
import ru.trollsmedjan.remedy.model.entity.Beacon;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.PrimaryGoal;

import java.util.List;

/**
 * Created by finnetrolle on 30.07.2015.
 */
@Service
public class PrimaryGoalServiceImpl implements PrimaryGoalService {

    @Autowired
    private OptionalDataProvider db;

    @Autowired
    private PrimaryGoalRepository primaryGoalRepository;

    @Autowired
    private BeaconService beaconService;

    private static final Logger log = Logger.getLogger(PrimaryGoalServiceImpl.class);

    @Override
    public PrimaryGoal createPrimaryGoal(String name, Long campaignId) throws RemedyDataLayerException {
        log.debug("Creating primary goal "+ name +" for campaign " + campaignId);
        Campaign campaign = db.getCampaign(campaignId)
                .orElseThrow(CampaignNotFoundException::new);
        PrimaryGoal primaryGoal = new PrimaryGoal();
        primaryGoal.setCampaign(campaign);
        primaryGoal.setName(name);
        primaryGoalRepository.save(primaryGoal);
        log.debug("Primary Goal created " + primaryGoal);
        return primaryGoal;
    }

    @Override
    public Long removePrimaryGoal(Long primaryGoalId) throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("Removing primary goal " + primaryGoalId);
        PrimaryGoal primaryGoal = db.getPrimaryGoal(primaryGoalId)
                .orElseThrow(PrimaryGoalNotFoundException::new);
        List<Beacon> beacons = db.findBeaconsByPrimary(primaryGoal);
        for (Beacon beacon : beacons) {
            beaconService.removeBeacon(beacon.getId());
        }
        long id = primaryGoal.getId();
        primaryGoalRepository.delete(primaryGoal);
        return id;
    }
}
