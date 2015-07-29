package ru.trollsmedjan.remedy.services;

import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.PrimaryGoal;

/**
 * Created by finnetrolle on 30.07.2015.
 */
@Service
public interface PrimaryGoalService {

    PrimaryGoal createPrimaryGoal(String name, Long campaignId) throws RemedyDataLayerException;

    Long removePrimaryGoal(Long primaryGoalId) throws RemedyDataLayerException, RemedyServiceLayerException;


}
