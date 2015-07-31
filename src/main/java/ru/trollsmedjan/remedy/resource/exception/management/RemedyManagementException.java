package ru.trollsmedjan.remedy.resource.exception.management;

import ru.trollsmedjan.remedy.resource.exception.RestWebApplicationException;

import javax.ws.rs.core.Response;

public class RemedyManagementException extends RestWebApplicationException {
    public RemedyManagementException(int code, String message) {
        super(Response.Status.NOT_ACCEPTABLE, code, message);
    }
}
