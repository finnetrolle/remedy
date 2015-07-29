package ru.trollsmedjan.remedy.services;

import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.Campaign;

/**
 * Created by finnetrolle on 30.07.2015.
 */
public interface CampaignService {

    Campaign createCampaign(String name, String constellationName) throws RemedyDataLayerException;

    Long stopCampaign(Long campaignId) throws RemedyDataLayerException, RemedyServiceLayerException;

}
