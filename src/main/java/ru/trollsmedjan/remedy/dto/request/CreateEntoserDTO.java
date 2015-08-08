package ru.trollsmedjan.remedy.dto.request;

/**
 * Created by finnetrolle on 31.07.2015.
 */
public class CreateEntoserDTO extends AuthDTO {

    private String ship;
    private long id;
    private boolean isT2EntosisModule;
    private boolean isCapitalShip;

    @Override
    public String toString() {
        return "CreateEntoserDTO{" +
                "ship='" + ship + '\'' +
                ", id=" + id +
                ", isT2EntosisModule=" + isT2EntosisModule +
                ", isCapitalShip=" + isCapitalShip +
                "} " + super.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public boolean isT2EntosisModule() {
        return isT2EntosisModule;
    }

    public void setT2EntosisModule(boolean isT2EntosisModule) {
        this.isT2EntosisModule = isT2EntosisModule;
    }

    public boolean isCapitalShip() {
        return isCapitalShip;
    }

    public void setCapitalShip(boolean isCapitalShip) {
        this.isCapitalShip = isCapitalShip;
    }
}
