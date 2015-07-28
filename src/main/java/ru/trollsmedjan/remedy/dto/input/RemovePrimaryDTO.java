package ru.trollsmedjan.remedy.dto.input;

/**
 * Created by finnetrolle on 29.07.2015.
 */
public class RemovePrimaryDTO extends BaseData {

    private long primaryId;

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }
}
