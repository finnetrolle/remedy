package ru.trollsmedjan.remedy.services;

import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;
import ru.trollsmedjan.remedy.model.entity.Entoser;

/**
 * Created by finnetrolle on 30.07.2015.
 */
public interface EntoserService {

    Entoser createEntoser(String username,
                                 String ship,
                                 boolean isT2EntosisModule,
                                 boolean isCapitalShip,
                                 Long campaignId) throws RemedyDataLayerException, RemedyServiceLayerException;

    Long removeEntoser(Long entoserId) throws RemedyDataLayerException, RemedyServiceLayerException;


    Entoser updateEntoser(long id, String username, String ship, boolean t2EntosisModule, boolean capitalShip, Long campaignId) throws RemedyServiceLayerException;
}
