package ru.trollsmedjan.remedy.resource.exception.entity;

public class BeaconNotFoundException extends EntityNotFoundException {

    public BeaconNotFoundException() {
        super(4030, "Beacon not found");
    }
}
