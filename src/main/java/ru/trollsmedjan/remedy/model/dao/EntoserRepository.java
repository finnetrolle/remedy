package ru.trollsmedjan.remedy.model.dao;

import org.springframework.data.repository.CrudRepository;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.Entoser;

import java.util.List;

/**
 * Created by finnetrolle on 27.07.2015.
 */
public interface EntoserRepository extends CrudRepository<Entoser, Long> {

    Entoser findOneByNameAndCampaign(String name, Campaign campaign);

    List<Entoser> findByCampaign(Campaign campaign);
}
