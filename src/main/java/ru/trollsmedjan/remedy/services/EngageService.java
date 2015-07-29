package ru.trollsmedjan.remedy.services;

import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.exception.RemedyServiceLayerException;

/**
 * Created by finnetrolle on 30.07.2015.
 */
public interface EngageService {

    void engage(Long entoserId, Long beaconId) throws RemedyDataLayerException, RemedyServiceLayerException;

    void disengage(Long entoserId, Long beaconId) throws RemedyDataLayerException, RemedyServiceLayerException;

}
