package ru.trollsmedjan.remedy.model.entity;

import javax.persistence.*;

/**
 * Created by finnetrolle on 08.08.2015.
 */
@Entity
@Table(name = "pilots")
public class User {

    @Id
    private String username;

    @Embedded
    private AccessRights accessRights;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AccessRights getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(AccessRights accessRights) {
        this.accessRights = accessRights;
    }

}
