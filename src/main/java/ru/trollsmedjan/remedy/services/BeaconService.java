package ru.trollsmedjan.remedy.services;

import ru.trollsmedjan.remedy.exception.*;
import ru.trollsmedjan.remedy.model.entity.*;

/**
 * Created by finnetrolle on 30.07.2015.
 */
public interface BeaconService {

    Beacon createBeacon(String name, Long primaryGoalId, String location) throws RemedyDataLayerException, RemedyServiceLayerException;

    Long removeBeacon(long beaconId) throws RemedyDataLayerException, RemedyServiceLayerException;

    void reportEnemyAttack(Long beaconId) throws RemedyDataLayerException, RemedyServiceLayerException;

    void reportBeaconDefended(Long beaconId) throws RemedyDataLayerException, RemedyServiceLayerException;

}
