package server.http.response;

import server.http.HttpCode;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse implements Response {
    private static final String HTTP_VERSION = "HTTP/1.1";

    private HttpCode status;
    private Map<String, String> headers;
    private String content;
    private String urlPath;

    public HttpResponse(String urlPath, HttpCode status) {
        this.headers = new HashMap<>();
        this.urlPath = urlPath;
        this.status = status;
    }

    @Override
    public void setContent(String content){
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value){
        this.headers.put(header, value);
    }

    @Override
    public String getResponse(){
        StringBuilder result = new StringBuilder();
        result.append(HTTP_VERSION).append(" ").append(this.status.getStatusMessage()).append(System.lineSeparator());
        this.headers.entrySet()
                .forEach(x -> result.append(x.getKey())
                                    .append(": ")
                                    .append(x.getValue())
                                    .append(System.lineSeparator()));
        result.append(System.lineSeparator());
        result.append(content);

        return result.toString();
    }
}
