package ru.trollsmedjan.remedy.model.entity;

import javax.persistence.*;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@Entity
public class Entoser {

    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne
    private Campaign campaign;

    private String name;

    private String ship;

    private boolean isCapitalShip;

    private boolean isT2EntosisModule;

    @OneToOne
    private Beacon engaging;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public boolean isCapitalShip() {
        return isCapitalShip;
    }

    public void setCapitalShip(boolean isCapitalShip) {
        this.isCapitalShip = isCapitalShip;
    }

    public boolean isT2EntosisModule() {
        return isT2EntosisModule;
    }

    public void setT2EntosisModule(boolean isT2EntosisModule) {
        this.isT2EntosisModule = isT2EntosisModule;
    }

    public Beacon getEngaging() {
        return engaging;
    }

    public void setEngaging(Beacon engaging) {
        this.engaging = engaging;
    }

    @Override
    public String toString() {
        return "Entoser{" +
                "id=" + id +
                ", campaign=" + campaign +
                ", name='" + name + '\'' +
                ", ship='" + ship + '\'' +
                ", isCapitalShip=" + isCapitalShip +
                ", isT2EntosisModule=" + isT2EntosisModule +
                ", engaging=" + ((engaging==null) ? "none" : engaging.getName()) +
                '}';
    }
}
