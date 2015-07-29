package ru.trollsmedjan.remedy.dto.input;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class BaseBeaconData extends BaseData {

    private Long beaconId;

    public Long getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Long beaconId) {
        this.beaconId = beaconId;
    }

    @Override
    public String toString() {
        return "BaseBeaconData{" +
                "beaconId=" + beaconId +
                "} " + super.toString();
    }
}
