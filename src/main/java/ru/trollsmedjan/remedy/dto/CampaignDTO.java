package ru.trollsmedjan.remedy.dto;

/**
 * Created by finnetrolle on 27.07.2015.
 */
public class CampaignDTO {

    private String name;

    private String constellation;

    private long id;

    public CampaignDTO() {
    }

    public CampaignDTO(String name, String constellation, long id) {
        this.name = name;
        this.constellation = constellation;
        this.id = id;
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

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }
}
