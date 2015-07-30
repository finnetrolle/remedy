package ru.trollsmedjan.remedy.dto;

import ru.trollsmedjan.remedy.model.entity.SolarSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class ConstellationDTO {

    private String name;

    private List<String> systemList = new ArrayList<>();

    public List<String> getSystemList() {
        return systemList;
    }

    public void setSystemList(List<String> systemList) {
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

    public ConstellationDTO(String name, List<String> systemList) {
        this.name = name;
        this.systemList = systemList;
    }

    public ConstellationDTO() {
    }

    @Override
    public String toString() {
        return "ConstellationDTO{" +
                "name='" + name + '\'' +
                ", systemList=" + systemList +
                '}';
    }
}
