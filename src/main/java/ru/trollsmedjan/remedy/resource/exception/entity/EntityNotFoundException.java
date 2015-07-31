package ru.trollsmedjan.remedy.resource.exception.entity;

import ru.trollsmedjan.remedy.resource.exception.RestWebApplicationException;

import javax.ws.rs.core.Response;

public class EntityNotFoundException extends RestWebApplicationException {

    public EntityNotFoundException(int code, String message) {
        super(Response.Status.BAD_REQUEST, code, message);
    }
}
