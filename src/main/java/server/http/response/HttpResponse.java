package server.http.response;

import server.http.HttpCode;
import server.utils.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse implements Response {
    private static final String HTTP_VERSION = "HTTP/1.1";


    private HttpCode status;
    private Map<String, String> headers;
    private File content;

    public HttpResponse(HttpCode status, File content) {
        this.headers = new HashMap<>();
        this.status = status;
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public String getResponse() {
        initDefaultHeaders();
        StringBuilder result = new StringBuilder();
        result.append(HTTP_VERSION).append(" ")
                .append(this.status.getStatusMessage())
                .append(System.lineSeparator());

        this.headers.forEach((key, value) -> result.append(key)
                .append(": ")
                .append(value)
                .append(System.lineSeparator()));

        result.append(System.lineSeparator());

        try {
            result.append(Reader.readAllBytes(new FileInputStream(this.content)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private void initDefaultHeaders(){
        this.addHeader("Content-Type", "text/html");
        this.addHeader("Date", new Date().toString());
    }
}
