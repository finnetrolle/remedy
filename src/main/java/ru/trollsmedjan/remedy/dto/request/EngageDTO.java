package ru.trollsmedjan.remedy.dto.request;

/**
 * Created by finnetrolle on 31.07.2015.
 */
public class EngageDTO extends AuthDTO {

    private Long entoserId;
    private Long beaconId;

    public Long getEntoserId() {
        return entoserId;
    }

    public void setEntoserId(Long entoserId) {
        this.entoserId = entoserId;
    }

    public Long getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Long beaconId) {
        this.beaconId = beaconId;
    }

    @Override
    public String toString() {
        return "EngageDTO{" +
                "entoserId=" + entoserId +
                ", beaconId=" + beaconId +
                "} " + super.toString();
    }
}
