package ru.trollsmedjan.remedy.resource.exception.entity;

public class EntoserNotFoundException extends EntityNotFoundException {
    public EntoserNotFoundException() {
        super(4040, "Entoser not found");
    }
}
