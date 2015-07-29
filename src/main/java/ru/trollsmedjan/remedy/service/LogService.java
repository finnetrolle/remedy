package ru.trollsmedjan.remedy.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.model.dao.LogMessageRepository;
import ru.trollsmedjan.remedy.model.entity.ActionType;
import ru.trollsmedjan.remedy.model.entity.Campaign;
import ru.trollsmedjan.remedy.model.entity.LogMessage;

import java.util.Date;

/**
 * Created by finnetrolle on 29.07.2015.
 */
@Service
public class LogService {

    private static final Logger log = Logger.getLogger(LogService.class);

    @Autowired
    private LogMessageRepository repository;

    public void info(ActionType action, String username, Campaign campaign, String description) {
        LogMessage msg = new LogMessage(campaign, username, new Date(), action, "");
        repository.save(msg);
    }
}
