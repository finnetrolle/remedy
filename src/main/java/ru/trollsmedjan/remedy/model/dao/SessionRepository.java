package ru.trollsmedjan.remedy.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Session;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by finnetrolle on 08.08.2015.
 */
public interface SessionRepository extends JpaRepository<Session, UUID> {

    Collection<Session> findByCampaign(Campaign campaign);
    Long deleteByCampaign(Campaign campaign);
}
