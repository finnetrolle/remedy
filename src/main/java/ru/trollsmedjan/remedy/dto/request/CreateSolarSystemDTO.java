package ru.trollsmedjan.remedy.dto.request;

/**
 * Created by finnetrolle on 31.07.2015.
 */
public class CreateSolarSystemDTO extends AuthDTO {

    private String solarSystemName;

    public String getSolarSystemName() {
        return solarSystemName;
    }

    public void setSolarSystemName(String solarSystemName) {
        this.solarSystemName = solarSystemName;
    }
}
