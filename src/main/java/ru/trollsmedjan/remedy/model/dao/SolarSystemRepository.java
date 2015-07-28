package ru.trollsmedjan.remedy.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.trollsmedjan.remedy.model.entity.Constellation;
import ru.trollsmedjan.remedy.model.entity.SolarSystem;

import java.util.List;

/**
 * Created by finnetrolle on 27.07.2015.
 */
public interface SolarSystemRepository extends JpaRepository<SolarSystem, String> {

    public List<SolarSystem> findByConstellation(Constellation constellation);

}
