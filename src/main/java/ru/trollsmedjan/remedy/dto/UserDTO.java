package ru.trollsmedjan.remedy.dto;

import ru.trollsmedjan.remedy.model.entity.AccessRights;

/**
 * Created by finnetrolle on 15.08.2015.
 */
public class UserDTO {

    private String username;
    private AccessRights accessRights;

    public UserDTO(String username, AccessRights accessRights) {
        this.username = username;
        this.accessRights = accessRights;
    }

    public UserDTO() {

    }

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
