package ru.trollsmedjan.remedy.dto;


/**
 * Created by finnetrolle on 28.07.2015.
 */
public class EntoserDTO {

    private long id;

    private String name;

    private String ship;

    private boolean isCapitalShip;

    private boolean isT2EntosisModule;

    private String engaging;

    public EntoserDTO() {
    }

    public EntoserDTO(long id, String name, String ship, boolean isCapitalShip, boolean isT2EntosisModule, String engaging) {

        this.id = id;
        this.name = name;
        this.ship = ship;
        this.isCapitalShip = isCapitalShip;
        this.isT2EntosisModule = isT2EntosisModule;
        this.engaging = engaging;
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

    public String getEngaging() {
        return engaging;
    }

    public void setEngaging(String engaging) {
        this.engaging = engaging;
    }
}
