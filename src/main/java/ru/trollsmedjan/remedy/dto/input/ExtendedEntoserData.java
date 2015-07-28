package ru.trollsmedjan.remedy.dto.input;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class ExtendedEntoserData extends BaseData {

    private String corporation;

    private String alliance;

    private String ship;

    private boolean isT2EntosisModule;

    private boolean isCapitalShip;

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getAlliance() {
        return alliance;
    }

    public void setAlliance(String alliance) {
        this.alliance = alliance;
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
