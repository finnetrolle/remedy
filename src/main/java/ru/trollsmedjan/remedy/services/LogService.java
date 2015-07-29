package ru.trollsmedjan.remedy.services;

import org.apache.log4j.spi.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.exception.CampaignNotFoundException;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.model.dao.LogMessageRepository;
import ru.trollsmedjan.remedy.model.entity.ActionType;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.LogMessage;

import java.util.Date;

/**
 * Created by finnetrolle on 30.07.2015.
 */
@Service
public class LogService {

    @Autowired
    private LogMessageRepository repo;

    @Autowired
    private OptionalDataProvider db;

    public void log(Long campaignId, String username, ActionType action, String description) throws RemedyDataLayerException {
        Campaign campaign = db.getCampaign(campaignId)
                .orElseThrow(CampaignNotFoundException::new);
        repo.save(new LogMessage(campaign, username, new Date(), action, description));
    }

}
