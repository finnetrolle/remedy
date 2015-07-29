package ru.trollsmedjan.remedy.dto;

import ru.trollsmedjan.remedy.model.entity.BeaconStatus;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class BeaconDTO {

    private long id;

    private String name;

    private String location;

    private BeaconStatus status;

    private String entoser;

    private long startTime;

    private long timeToCapture;

    private String primary;

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public BeaconDTO(long id, String name, String location, BeaconStatus status, String entoser, long startTime, long timeToCapture, String primary) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.status = status;
        this.entoser = entoser;
        this.startTime = startTime;
        this.timeToCapture = timeToCapture;
        this.primary = primary;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    @Override
    public String toString() {
        return "BeaconDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", status=" + status +
                ", entoser='" + entoser + '\'' +
                ", startTime=" + startTime +
                ", timeToCapture=" + timeToCapture +
                ", primary='" + primary + '\'' +
                '}';
    }
}
