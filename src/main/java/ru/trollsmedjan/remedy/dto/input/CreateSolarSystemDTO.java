package ru.trollsmedjan.remedy.dto.input;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class CreateSolarSystemDTO {

    private String name;

    private String constellation;

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

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    @Override
    public String toString() {
        return "CreateSolarSystemDTO{" +
                "name='" + name + '\'' +
                ", constellation='" + constellation + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
