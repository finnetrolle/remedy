package ru.trollsmedjan.remedy.dto;

import ru.trollsmedjan.remedy.model.entity.Beacon;

import java.util.List;

/**
 * Created by finnetrolle on 29.07.2015.
 */
public class PrimaryDTO {

    private String name;

    private long id;

    private List<BeaconDTO> beacons;

    public List<BeaconDTO> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<BeaconDTO> beacons) {
        this.beacons = beacons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PrimaryDTO() {

    }

    public PrimaryDTO(String name, long id, List<BeaconDTO> beacons) {
        this.name = name;
        this.id = id;
        this.beacons = beacons;
    }

    public PrimaryDTO(String name, long id) {
        this.name = name;
        this.id = id;
    }
}