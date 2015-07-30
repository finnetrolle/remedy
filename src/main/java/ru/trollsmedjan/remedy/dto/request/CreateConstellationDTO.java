package ru.trollsmedjan.remedy.dto.request;

/**
 * Created by finnetrolle on 31.07.2015.
 */
public class CreateConstellationDTO extends AuthDTO {

    private String constellationName;

    public String getConstellationName() {
        return constellationName;
    }

    public void setConstellationName(String constellationName) {
        this.constellationName = constellationName;
    }

    @Override
    public String toString() {
        return "CreateConstellationDTO{" +
                "constellationName='" + constellationName + '\'' +
                "} " + super.toString();
    }
}
