package ru.trollsmedjan.remedy.resource.exception.entity;

public class CampaignNotFoundException extends EntityNotFoundException {

    public CampaignNotFoundException() {
        super(4010, "Campaign not found");
    }
}
