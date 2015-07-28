package ru.trollsmedjan.remedy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.model.dao.PrimaryRepository;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Primary;

import java.util.List;

/**
 * Created by finnetrolle on 29.07.2015.
 */
@Service
public class PrimaryService {

    @Autowired
    private PrimaryRepository primaryRepository;

    public Primary get(long id) {
        return primaryRepository.findOne(id);
    }

    public List<Primary> get(Campaign campaign) {
        return primaryRepository.findByCampaign(campaign);
    }

    public Primary get(String name, Campaign campaign) {
        return primaryRepository.findByNameAndCampaign(name, campaign);
    }

    public void save(Primary primary) {
        primaryRepository.save(primary);
    }

    public void delete(Primary primary) {
        primaryRepository.delete(primary);
    }



}
