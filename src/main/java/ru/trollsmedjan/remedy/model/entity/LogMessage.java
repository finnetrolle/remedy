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

    @Enumerated(value = EnumType.STRING)
    private ActionType action;

    @Column(length = 2000)
    private String description;

    public LogMessage(Campaign campaign, String username, Date timestamp, ActionType action, String description) {
        this.campaign = campaign;
        this.username = username;
        this.timestamp = timestamp;
        this.action = action;
        this.description = description;
    }

    public LogMessage() {

    }

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
