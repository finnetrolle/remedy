package ru.trollsmedjan.remedy.model.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@Entity
public class LogMessage {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Campaign campaign;

    private String username;

    private Date timestamp;

    @Enumerated
    private ActionType action;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
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

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
