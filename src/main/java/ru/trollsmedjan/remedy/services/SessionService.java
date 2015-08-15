package ru.trollsmedjan.remedy.services;

import ru.trollsmedjan.remedy.exception.RemedyAuthException;
import ru.trollsmedjan.remedy.model.entity.AccessRights;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Session;
import ru.trollsmedjan.remedy.model.entity.User;

/**
 * Created by finnetrolle on 08.08.2015.
 */
public interface SessionService {

    Session getSession(String token) throws RemedyAuthException;

    Session createSession(User user, Campaign campaign);
    Session createSession(User user);

    void checkSessions();

    void removeSessionsByCampaign(Campaign campaign);

    Session updateAccessRights(String token, AccessRights accessRights) throws RemedyAuthException;

    boolean canCreateCampaigns(String token) throws RemedyAuthException;
    boolean canDeleteCampaigns(String token) throws RemedyAuthException;
    boolean canReadPrimaries(String token) throws RemedyAuthException;
    boolean canCreatePrimaries(String token) throws RemedyAuthException;
    boolean canDeletePrimaries(String token) throws RemedyAuthException;
    boolean canReadEntosers(String token) throws RemedyAuthException;
    boolean canCreateEntosers(String token) throws RemedyAuthException;
    boolean canDeleteEntosers(String token) throws RemedyAuthException;
    boolean canReadBeacons(String token) throws RemedyAuthException;
    boolean canCreateBeacons(String token) throws RemedyAuthException;
    boolean canDeleteBeacons(String token) throws RemedyAuthException;
    boolean canEngageBeacons(String token) throws RemedyAuthException;
    boolean canReportEnemies(String token) throws RemedyAuthException;
    boolean canReadAccessRights(String token) throws RemedyAuthException;
    boolean canWriteAccessRights(String token) throws RemedyAuthException;



}
