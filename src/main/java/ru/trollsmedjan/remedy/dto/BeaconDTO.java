package ru.trollsmedjan.remedy.dto;

import ru.trollsmedjan.remedy.model.entity.BeaconStatus;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class BeaconDTO {

    private long id;

    private String name;

    private SolarSystemDTO location;

    private SolarSystemDTO affectingSystem;

    private BeaconStatus status;

    private String entoser;

    private long startTime;

    private long timeToCapture;

    public BeaconDTO(long id, String name, SolarSystemDTO location, SolarSystemDTO affectingSystem, BeaconStatus status, String entoser, long startTime, long timeToCapture) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.affectingSystem = affectingSystem;
        this.status = status;
        this.entoser = entoser;
        this.startTime = startTime;
        this.timeToCapture = timeToCapture;
    }

    public BeaconDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SolarSystemDTO getLocation() {
        return location;
    }

    public void setLocation(SolarSystemDTO location) {
        this.location = location;
    }

    public SolarSystemDTO getAffectingSystem() {
        return affectingSystem;
    }

    public void setAffectingSystem(SolarSystemDTO affectingSystem) {
        this.affectingSystem = affectingSystem;
    }

    public BeaconStatus getStatus() {
        return status;
    }

    public void setStatus(BeaconStatus status) {
        this.status = status;
    }

    public String getEntoser() {
        return entoser;
    }

    public void setEntoser(String entoser) {
        this.entoser = entoser;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getTimeToCapture() {
        return timeToCapture;
    }

    public void setTimeToCapture(long timeToCapture) {
        this.timeToCapture = timeToCapture;
    }
}
