package ru.trollsmedjan.remedy.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.PrimaryGoal;

import java.util.List;

/**
 * Created by finnetrolle on 29.07.2015.
 */
public interface PrimaryGoalRepository extends JpaRepository<PrimaryGoal, Long> {

    List<PrimaryGoal> findByCampaign(Campaign campaign);

    PrimaryGoal findByNameAndCampaign(String name, Campaign campaign);
}
