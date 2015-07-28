package ru.trollsmedjan.remedy.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.trollsmedjan.remedy.model.entity.Beacon;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Primary;

import java.util.List;

/**
 * Created by finnetrolle on 27.07.2015.
 */
public interface BeaconRepository extends JpaRepository<Beacon, Long> {

    List<Beacon> findByCampaign(Campaign campaign);

    List<Beacon> findByPrimary(Primary primary);

}
