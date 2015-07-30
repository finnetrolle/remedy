package ru.trollsmedjan.remedy.dto.request;


/**
 * Created by finnetrolle on 28.07.2015.
 */
public class CreateBeaconDTO extends AuthDTO {

    private String name;

    private String location;

    private long primaryId;

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
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

    @Override
    public String toString() {
        return "CreateBeaconDTO{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", primaryId=" + primaryId +
                "} " + super.toString();
    }
}
