package ru.trollsmedjan.remedy.dto.request;

/**
 * Created by finnetrolle on 08.08.2015.
 */
public class CreateTokenDTO {

    private String username;

    private String keyword;

    private Long campaignId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }
}
