package ru.trollsmedjan.remedy.dto;

import java.util.List;

/**
 * Created by finnetrolle on 27.07.2015.
 */
public class CampaignDTO {

    private String name;

    private String constellation;

    private long id;

    private List<PrimaryDTO> primaries;

    private List<EntoserDTO> entosers;

    private List<String> solarSystems;

    public List<String> getSolarSystems() {
        return solarSystems;
    }

    public void setSolarSystems(List<String> solarSystems) {
        this.solarSystems = solarSystems;
    }

    public List<EntoserDTO> getEntosers() {
        return entosers;
    }

    public void setEntosers(List<EntoserDTO> entosers) {
        this.entosers = entosers;
    }

    public List<PrimaryDTO> getPrimaries() {
        return primaries;
    }

    public void setPrimaries(List<PrimaryDTO> primaries) {
        this.primaries = primaries;
    }

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

    @Override
    public String toString() {
        return "CampaignDTO{" +
                "name='" + name + '\'' +
                ", constellation='" + constellation + '\'' +
                ", id=" + id +
                '}';
    }
}
