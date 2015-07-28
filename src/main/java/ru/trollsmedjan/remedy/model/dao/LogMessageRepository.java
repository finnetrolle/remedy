package ru.trollsmedjan.remedy.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.trollsmedjan.remedy.model.entity.LogMessage;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public interface LogMessageRepository extends JpaRepository<LogMessage, Long> {
}
