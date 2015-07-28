package ru.trollsmedjan.remedy.dto;

import ru.trollsmedjan.remedy.model.entity.ActionType;

import java.util.Date;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class LogMessageDTO {

    private String username;

    private Date timestamp;

    private ActionType actionType;

    private String description;

    public LogMessageDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
