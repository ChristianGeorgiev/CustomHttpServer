package server.http;

public enum  HttpCode {
    OK("200 OK"),
    CREATED("201 Created"),
    NO_CONTENT("204 No Content"),
    SEE_OTHER("303 See Other"),
    BAD_REQUEST("400 Bad Request"),
    UNAUTHORIZED("401 Unathorized"),
    FORBIDDEN("403 Forbidden"),
    NOT_FOUND("404 Not Found"),
    INTERNAL_SERVER_ERROR("500 Internal Server Error");

    private String statusMessage;

    HttpCode(String message) {
        this.statusMessage = message;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
