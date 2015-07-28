package ru.trollsmedjan.remedy.dto.input;

import ru.trollsmedjan.remedy.model.entity.Constellation;

/**
 * Created by finnetrolle on 27.07.2015.
 */
public class StartCampaignDTO extends BaseData {

    private String name;
    private String constellation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }
}
