package ru.trollsmedjan.remedy.dto;

import java.util.Date;

/**
 * Created by finnetrolle on 08.08.2015.
 */
public class TokenDTO {

    private String token;
    private String username;
    private Date created;
    private Date timeToLive;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Date timeToLive) {
        this.timeToLive = timeToLive;
    }
}
