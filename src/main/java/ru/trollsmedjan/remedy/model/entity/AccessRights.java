package ru.trollsmedjan.remedy.model.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 * Created by finnetrolle on 08.08.2015.
 */
@Embeddable
public class AccessRights {

    private boolean canCreateCampaigns = false;
    private boolean canDeleteCampaigns = false;
    private boolean canReadPrimaries = true;
    private boolean canCreatePrimaries = false;
    private boolean canDeletePrimaries = false;
    private boolean canReadEntosers = true;
    private boolean canCreateEntosers = true;
    private boolean canDeleteEntosers = true;
    private boolean canReadBeacons = true;
    private boolean canCreateBeacons = true;
    private boolean canDeleteBeacons = true;
    private boolean canEngageBeacons = true;
    private boolean canReportEnemies = true;
    private boolean canReadAccessRights = false;
    private boolean canWriteAccessRights = false;

    public AccessRights() {
    }

    public AccessRights(boolean absolute) {
        this.canCreateCampaigns = absolute;
        this.canDeleteCampaigns = absolute;
        this.canReadPrimaries = absolute;
        this.canCreatePrimaries = absolute;
        this.canDeletePrimaries = absolute;
        this.canReadEntosers = absolute;
        this.canCreateEntosers = absolute;
        this.canDeleteEntosers = absolute;
        this.canReadBeacons = absolute;
        this.canCreateBeacons = absolute;
        this.canDeleteBeacons = absolute;
        this.canEngageBeacons = absolute;
        this.canReportEnemies = absolute;
        this.canReadAccessRights = absolute;
        this.canWriteAccessRights = absolute;
    }

    public boolean isCanCreateCampaigns() {
        return canCreateCampaigns;
    }

    public void setCanCreateCampaigns(boolean canCreateCampaigns) {
        this.canCreateCampaigns = canCreateCampaigns;
    }

    public boolean isCanDeleteCampaigns() {
        return canDeleteCampaigns;
    }

    public void setCanDeleteCampaigns(boolean canDeleteCampaigns) {
        this.canDeleteCampaigns = canDeleteCampaigns;
    }

    public boolean isCanReadPrimaries() {
        return canReadPrimaries;
    }

    public void setCanReadPrimaries(boolean canReadPrimaries) {
        this.canReadPrimaries = canReadPrimaries;
    }

    public boolean isCanCreatePrimaries() {
        return canCreatePrimaries;
    }

    public void setCanCreatePrimaries(boolean canCreatePrimaries) {
        this.canCreatePrimaries = canCreatePrimaries;
    }

    public boolean isCanDeletePrimaries() {
        return canDeletePrimaries;
    }

    public void setCanDeletePrimaries(boolean canDeletePrimaries) {
        this.canDeletePrimaries = canDeletePrimaries;
    }

    public boolean isCanReadEntosers() {
        return canReadEntosers;
    }

    public void setCanReadEntosers(boolean canReadEntosers) {
        this.canReadEntosers = canReadEntosers;
    }

    public boolean isCanCreateEntosers() {
        return canCreateEntosers;
    }

    public void setCanCreateEntosers(boolean canCreateEntosers) {
        this.canCreateEntosers = canCreateEntosers;
    }

    public boolean isCanDeleteEntosers() {
        return canDeleteEntosers;
    }

    public void setCanDeleteEntosers(boolean canDeleteEntosers) {
        this.canDeleteEntosers = canDeleteEntosers;
    }

    public boolean isCanReadBeacons() {
        return canReadBeacons;
    }

    public void setCanReadBeacons(boolean canReadBeacons) {
        this.canReadBeacons = canReadBeacons;
    }

    public boolean isCanCreateBeacons() {
        return canCreateBeacons;
    }

    public void setCanCreateBeacons(boolean canCreateBeacons) {
        this.canCreateBeacons = canCreateBeacons;
    }

    public boolean isCanDeleteBeacons() {
        return canDeleteBeacons;
    }

    public void setCanDeleteBeacons(boolean canDeleteBeacons) {
        this.canDeleteBeacons = canDeleteBeacons;
    }

    public boolean isCanEngageBeacons() {
        return canEngageBeacons;
    }

    public void setCanEngageBeacons(boolean canEngageBeacons) {
        this.canEngageBeacons = canEngageBeacons;
    }

    public boolean isCanReportEnemies() {
        return canReportEnemies;
    }

    public void setCanReportEnemies(boolean canReportEnemies) {
        this.canReportEnemies = canReportEnemies;
    }

    public boolean isCanReadAccessRights() {
        return canReadAccessRights;
    }

    public void setCanReadAccessRights(boolean canReadAccessRights) {
        this.canReadAccessRights = canReadAccessRights;
    }

    public boolean isCanWriteAccessRights() {
        return canWriteAccessRights;
    }

    public void setCanWriteAccessRights(boolean canWriteAccessRights) {
        this.canWriteAccessRights = canWriteAccessRights;
    }
}
