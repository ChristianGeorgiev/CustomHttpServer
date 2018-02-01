package server.http.response;

import server.http.HttpCode;
import server.utils.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse implements Response {
    private static final String HTTP_VERSION = "HTTP/1.1";

    private HttpCode status;
    private Map<String, String> headers;
    private File content;
    private String urlPath;

    public HttpResponse(HttpCode status, File content) {
        this.headers = new HashMap<>();
        this.urlPath = urlPath;
        this.status = status;
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value){
        this.headers.put(header, value);
    }

    @Override
    public String getResponse(){
        StringBuilder result = new StringBuilder();
        result.append(HTTP_VERSION).append(" ")
              .append(this.status.getStatusMessage())
              .append(System.lineSeparator());

        this.headers.forEach((key, value) -> result.append(key)
                .append(": ")
                .append(value)
                .append(System.lineSeparator()));

        result.append(System.lineSeparator());


        if (this.content != null) {
            try {
                result.append(Reader.readAllBytes(new FileInputStream(this.content)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            result.append("<h1>You messed up</h1>");
        }
        return result.toString();
    }
}
