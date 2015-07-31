package ru.trollsmedjan.remedy.resource.exception.entity;

public class PrimaryNotFoundException extends EntityNotFoundException {

    public PrimaryNotFoundException() {
        super(4020, "Primary goal not found");
    }
}
