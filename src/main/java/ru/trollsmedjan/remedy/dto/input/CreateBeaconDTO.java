package ru.trollsmedjan.remedy.dto.input;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class CreateBeaconDTO extends BaseData {

    private String name;

    private String location;

    private String affectingSystem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAffectingSystem() {
        return affectingSystem;
    }

    public void setAffectingSystem(String affectingSystem) {
        this.affectingSystem = affectingSystem;
    }

}
