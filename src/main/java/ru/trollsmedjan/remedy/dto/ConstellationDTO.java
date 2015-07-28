package ru.trollsmedjan.remedy.dto;

import ru.trollsmedjan.remedy.model.entity.SolarSystem;

import java.util.List;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class ConstellationDTO {

    private String name;

    private List<SolarSystemDTO> systemList;

    public List<SolarSystemDTO> getSystemList() {
        return systemList;
    }

    public void setSystemList(List<SolarSystemDTO> systemList) {
        this.systemList = systemList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConstellationDTO(String name) {
        this.name = name;
    }

    public ConstellationDTO(String name, List<SolarSystemDTO> systemList) {
        this.name = name;
        this.systemList = systemList;
    }

    public ConstellationDTO() {
    }
}
