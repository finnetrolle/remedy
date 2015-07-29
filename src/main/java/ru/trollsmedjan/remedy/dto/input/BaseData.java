package ru.trollsmedjan.remedy.dto.input;

/**
 * Created by finnetrolle on 28.07.2015.
 */
public class BaseData {

    private String username;

    private Long campaignId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "username='" + username + '\'' +
                ", campaignId=" + campaignId +
                '}';
    }
}
