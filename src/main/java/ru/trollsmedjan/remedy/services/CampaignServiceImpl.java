package ru.trollsmedjan.remedy.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.exception.CampaignNotFoundException;
import ru.trollsmedjan.remedy.exception.ConstellationNotFoundException;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.dao.CampaignRepository;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Constellation;
import ru.trollsmedjan.remedy.model.entity.Entoser;
import ru.trollsmedjan.remedy.model.entity.PrimaryGoal;

import java.util.List;

/**
 * Created by finnetrolle on 30.07.2015.
 */
@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private OptionalDataProvider db;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private PrimaryGoalService primaryGoalService;

    @Autowired
    private EntoserService entoserService;

    private static final Logger log = Logger.getLogger(CampaignServiceImpl.class);

    @Override
    public Campaign createCampaign(String name, String constellationName) throws RemedyDataLayerException {
        log.debug("Creating campaign in " + constellationName);
        Constellation constellation = db.getConstellation(constellationName)
                .orElseThrow(ConstellationNotFoundException::new);
        Campaign campaign = new Campaign();
        campaign.setConstellation(constellation);
        campaign.setName(name);
        campaignRepository.save(campaign);
        log.debug("Campaign created " + campaign);
        return campaign;
    }

    @Override
    public Long stopCampaign(Long campaignId) throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("Removing campaign " + campaignId);
        Campaign campaign = db.getCampaign(campaignId)
                .orElseThrow(CampaignNotFoundException::new);

        List<PrimaryGoal> primaries = db.findPrimaryByCampaign(campaign);
        for (PrimaryGoal goal : primaries) {
            primaryGoalService.removePrimaryGoal(goal.getId());
        }
        List<Entoser> entosers = db.findEntoserByCampaign(campaign);
        for (Entoser e : entosers) {
            entoserService.removeEntoser(e.getId());
        }

        long id = campaign.getId();
        campaignRepository.delete(campaign);
        return id;
    }
}
