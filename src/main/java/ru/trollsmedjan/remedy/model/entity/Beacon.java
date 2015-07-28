package ru.trollsmedjan.remedy.model.entity;

import javax.persistence.*;

/**
 * Created by finnetrolle on 27.07.2015.
 */

@Entity
public class Beacon {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Campaign campaign;

    @ManyToOne
    private PrimaryGoal primaryGoal;

    private String name;

    @ManyToOne
    private SolarSystem location;

    @ManyToOne
    private SolarSystem affectingSystem;

    @Enumerated
    private BeaconStatus status;

    @OneToOne
    private Entoser entoser;

    private long startTime;

    private long timeToCapture;

    public PrimaryGoal getPrimaryGoal() {
        return primaryGoal;
    }

    public void setPrimaryGoal(PrimaryGoal primaryGoal) {
        this.primaryGoal = primaryGoal;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
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

    public SolarSystem getLocation() {
        return location;
    }

    public void setLocation(SolarSystem location) {
        this.location = location;
    }

    public SolarSystem getAffectingSystem() {
        return affectingSystem;
    }

    public void setAffectingSystem(SolarSystem affectingSystem) {
        this.affectingSystem = affectingSystem;
    }

    public BeaconStatus getStatus() {
        return status;
    }

    public void setStatus(BeaconStatus status) {
        this.status = status;
    }

    public Entoser getEntoser() {
        return entoser;
    }

    public void setEntoser(Entoser entoser) {
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
        return "B{" +
                ", p=" + primaryGoal +
                ", n='" + name + '\'' +
                ", l=" + location +
                ", a=" + affectingSystem +
                ", s=" + status +
                ", e=" + entoser +
                ", t=" + startTime +
                ", o=" + timeToCapture +
                '}';
    }
}
