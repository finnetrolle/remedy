package ru.trollsmedjan.remedy.resource.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestWebApplicationException extends WebApplicationException {

    public RestWebApplicationException(Response.Status status, int code, String message) {
        super(createResponse(status, code, message));
    }

    private static Response createResponse(Response.Status status, int code, String message) {
        return Response
                .status(status)
                .entity(new ErrorBean(code, message))
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .build();
    }

    private static class ErrorBean {
        private int code;
        private String message;

        public ErrorBean(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
