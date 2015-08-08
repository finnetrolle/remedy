package ru.trollsmedjan.remedy.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.dao.EntoserRepository;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Entoser;
import ru.trollsmedjan.remedy.resource.exception.entity.CampaignNotFoundException;
import ru.trollsmedjan.remedy.resource.exception.entity.EntoserNotFoundException;

/**
 * Created by finnetrolle on 30.07.2015.
 */
@Service
public class EntoserServiceImpl implements EntoserService {


    @Autowired
    private OptionalDataProvider db;

    @Autowired
    private EntoserRepository entoserRepository;

    @Autowired
    private EngageService engageService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Entoser createEntoser(String username, String ship, boolean isT2EntosisModule, boolean isCapitalShip, Long campaignId) throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("Create entoser " + username + " for campaign " + campaignId);
        Campaign campaign = db.getCampaign(campaignId)
                .orElseThrow(CampaignNotFoundException::new);

        if (db.isEntoserExists(username, campaign)) {
            throw new RemedyServiceLayerException("Entoser " + username + " is already exists in campaign " + campaign.getName());
        }

        Entoser entoser = new Entoser();
        entoser.setCampaign(campaign);
        entoser.setCapitalShip(isCapitalShip);
        entoser.setEngaging(null);
        entoser.setName(username);
        entoser.setShip(ship);
        entoser.setT2EntosisModule(isT2EntosisModule);
        entoserRepository.save(entoser);
        log.debug("entoser created " + entoser);
        return entoser;
    }

    @Override
    public Long removeEntoser(Long entoserId) throws RemedyDataLayerException, RemedyServiceLayerException {
        log.debug("remove entoser " + entoserId);
        Entoser entoser = db.getEntoser(entoserId)
                .orElseThrow(EntoserNotFoundException::new);
        if (entoser.getEngaging() != null) {
            engageService.disengage(entoserId, entoser.getEngaging().getId());
        }
        entoserRepository.delete(entoser);
        return entoserId;
    }

    @Override
    public Entoser updateEntoser(long id, String username, String ship, boolean t2EntosisModule, boolean capitalShip, Long campaignId) throws RemedyServiceLayerException {
        log.debug("Update entoser [" + id + "] " + username + " for campaign " + campaignId);
        Campaign campaign = db.getCampaign(campaignId)
                .orElseThrow(CampaignNotFoundException::new);

        Entoser entoser = db.getEntoser(id).orElseThrow(() -> new RemedyServiceLayerException("Entoser " + id + " is not exists"));
        entoser.setCampaign(campaign);
        entoser.setCapitalShip(capitalShip);
        entoser.setEngaging(null);
        entoser.setName(username);
        entoser.setShip(ship);
        entoser.setT2EntosisModule(t2EntosisModule);
        entoserRepository.save(entoser);
        log.debug("entoser updated " + entoser);
        return entoser;
    }
}
