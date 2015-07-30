package ru.trollsmedjan.remedy.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.trollsmedjan.remedy.model.entity.Beacon;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.PrimaryGoal;
import ru.trollsmedjan.remedy.model.entity.SolarSystem;
import scala.util.parsing.combinator.testing.Str;

import java.util.List;

/**
 * Created by finnetrolle on 27.07.2015.
 */
public interface BeaconRepository extends JpaRepository<Beacon, Long> {

    List<Beacon> findByCampaign(Campaign campaign);

    List<Beacon> findByPrimaryGoal(PrimaryGoal primaryGoal);

    Beacon findOneByNameAndPrimaryGoalAndLocation(String name, PrimaryGoal primaryGoal, SolarSystem solarSystem);

}
