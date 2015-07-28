package ru.trollsmedjan.remedy.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.trollsmedjan.remedy.model.entity.Constellation;

/**
 * Created by finnetrolle on 27.07.2015.
 */
public interface ConstellationRepository extends JpaRepository<Constellation, String> {
}
