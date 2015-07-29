package ru.trollsmedjan.remedy.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.model.dao.EntoserRepository;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Entoser;

import java.util.List;

/**
 * Created by finnetrolle on 28.07.2015.
 */
@Service
public class EntoserService {

    private static final Logger log = Logger.getLogger(EntoserService.class);

    @Autowired
    private EntoserRepository entoserRepository;

    public Entoser getEntoser(Long id) {
        return entoserRepository.findOne(id);
    }

    public Entoser getEntoser(String name, Campaign campaign) {
        return entoserRepository.findOneByNameAndCampaign(name, campaign);
    }

    public void save(Entoser entoser) {
        entoserRepository.save(entoser);
    }

    public void remove(Entoser entoser) {
        entoserRepository.delete(entoser);
    }

    public List<Entoser> getEntosers(Campaign campaign) {
        return entoserRepository.findByCampaign(campaign);
    }
}
