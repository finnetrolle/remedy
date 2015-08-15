package ru.trollsmedjan.remedy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.exception.RemedyAuthException;
import ru.trollsmedjan.remedy.model.dao.SessionRepository;
import ru.trollsmedjan.remedy.model.entity.AccessRights;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Session;
import ru.trollsmedjan.remedy.model.entity.User;

import java.util.UUID;

/**
 * Created by finnetrolle on 08.08.2015.
 */
@Service
public class SessionServiceImpl implements SessionService {

    private static final Long EXPIRE_TIME = 1000L * 60L * 60L * 24L;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Session getSession(String token) throws RemedyAuthException {
        log.debug("Checking session for token " + token);
        UUID uuid = UUID.fromString(token);
        Session session = sessionRepository.findOne(uuid);
        if (session == null) {
            throw new RemedyAuthException("Given token is bad");
        }
        if (session.getExpireTime() <= System.currentTimeMillis()) {
            sessionRepository.delete(session);
            throw new RemedyAuthException("Session expired");
        }

        return session;
    }

    @Override
    public Session createSession(User user, Campaign campaign) {
        Long time = System.currentTimeMillis();
        Session session = new Session();
        session.setAccessRights(user.getAccessRights());
        session.setActivateTime(time);
        session.setExpireTime(time + EXPIRE_TIME);
        session.setToken(UUID.randomUUID());
        session.setUser(user);
        session.setCampaign(campaign);
        sessionRepository.save(session);
        return session;
    }

    @Override
    public Session createSession(User user) {
        Long time = System.currentTimeMillis();
        Session session = new Session();
        session.setAccessRights(user.getAccessRights());
        session.setActivateTime(time);
        session.setExpireTime(time + EXPIRE_TIME);
        session.setToken(UUID.randomUUID());
        session.setUser(user);
        sessionRepository.save(session);
        return session;
    }

    @Override
    public void checkSessions() {
        Long time = System.currentTimeMillis();
        for(Session session : sessionRepository.findAll()) {
            if (session.getExpireTime() <= time) {
                sessionRepository.delete(session);
            }
        }
    }

    @Override
    public void removeSessionsByCampaign(Campaign campaign) {
        sessionRepository.deleteByCampaign(campaign);
    }

    @Override
    public Session updateAccessRights(String token, AccessRights accessRights) throws RemedyAuthException {
        Session session = getSession(token);
        session.setAccessRights(accessRights);
        sessionRepository.save(session);
        return session;
    }

    @Override
    public boolean canCreateCampaigns(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanCreateCampaigns();
    }

    @Override
    public boolean canDeleteCampaigns(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanDeleteCampaigns();
    }

    @Override
    public boolean canReadPrimaries(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanReadPrimaries();
    }

    @Override
    public boolean canCreatePrimaries(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanCreatePrimaries();
    }

    @Override
    public boolean canDeletePrimaries(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanDeletePrimaries();
    }

    @Override
    public boolean canReadEntosers(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanReadEntosers();
    }

    @Override
    public boolean canCreateEntosers(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanCreateEntosers();
    }

    @Override
    public boolean canDeleteEntosers(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanDeleteEntosers();
    }

    @Override
    public boolean canReadBeacons(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanReadBeacons();
    }

    @Override
    public boolean canCreateBeacons(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanCreateBeacons();
    }

    @Override
    public boolean canDeleteBeacons(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanDeleteBeacons();
    }

    @Override
    public boolean canEngageBeacons(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanEngageBeacons();
    }

    @Override
    public boolean canReportEnemies(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanReportEnemies();
    }

    @Override
    public boolean canReadAccessRights(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanReadAccessRights();
    }

    @Override
    public boolean canWriteAccessRights(String token) throws RemedyAuthException {
        return getSession(token).getAccessRights().isCanWriteAccessRights();
    }


}
