package ru.trollsmedjan.remedy.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.model.dao.PrimaryGoalRepository;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.PrimaryGoal;

import java.util.List;

/**
 * Created by finnetrolle on 29.07.2015.
 */
@Service
public class PrimaryService {

    private static final Logger log = Logger.getLogger(PrimaryService.class);

    @Autowired
    private PrimaryGoalRepository primaryGoalRepository;

    public PrimaryGoal get(long id) {
        return primaryGoalRepository.findOne(id);
    }

    public List<PrimaryGoal> get(Campaign campaign) {
        return primaryGoalRepository.findByCampaign(campaign);
    }

    public PrimaryGoal get(String name, Campaign campaign) {
        return primaryGoalRepository.findByNameAndCampaign(name, campaign);
    }

    public void save(PrimaryGoal primaryGoal) {
        primaryGoalRepository.save(primaryGoal);
    }

    public void delete(PrimaryGoal primaryGoal) {
        primaryGoalRepository.delete(primaryGoal);
    }



}
