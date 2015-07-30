package ru.trollsmedjan.remedy.dto.request;

/**
 * Created by finnetrolle on 31.07.2015.
 */
public class CreatePrimaryDTO extends AuthDTO {

    private String primaryName;

    private String solarSystem;

    private float defenceIndex;

    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public String getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(String solarSystem) {
        this.solarSystem = solarSystem;
    }

    public float getDefenceIndex() {
        return defenceIndex;
    }

    public void setDefenceIndex(float defenceIndex) {
        this.defenceIndex = defenceIndex;
    }

    @Override
    public String toString() {
        return "CreatePrimaryDTO{" +
                "primaryName='" + primaryName + '\'' +
                ", solarSystem='" + solarSystem + '\'' +
                ", defenceIndex=" + defenceIndex +
                "} " + super.toString();
    }
}
