package ru.trollsmedjan.remedy.dto.input;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class CreateConstellationDTO {

    private String name;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
