package ru.trollsmedjan.remedy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.model.dao.CampaignRepository;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Constellation;

import java.util.List;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public Campaign createNewCampaign(String name, Constellation constellation) {
        Campaign campaign = new Campaign();
        campaign.setConstellation(constellation);
        campaign.setName(name);
        campaignRepository.save(campaign);
        return campaign;
    }

    public Campaign getCampaign(Long id) {
        return campaignRepository.findOne(id);
    }

    public void stopCampaign(Campaign campaign) {
        campaignRepository.delete(campaign);
    }

    public List<Campaign> getCampaigns() {
        return campaignRepository.findAll();
    }
}
