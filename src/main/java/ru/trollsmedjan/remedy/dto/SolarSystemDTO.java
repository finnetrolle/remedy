package ru.trollsmedjan.remedy.dto;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class SolarSystemDTO {

    private String name;

    public SolarSystemDTO(String name) {
        this.name = name;
    }

    public SolarSystemDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
