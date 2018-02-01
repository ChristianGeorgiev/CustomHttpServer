package server.http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements Request {
    private String method;
    private String requestUrl;
    private Map<String, String> headers;


    public HttpRequest(String requestString) {
        this.headers = new HashMap<>();
        extractParametersFromString(requestString);
    }

    private void extractParametersFromString(String requestString) {
        if (requestString == null || requestString.equals("")){
            return;
        }

        String[] lines = requestString.split("[\r\n]+");

        this.method = extractMethod(lines[0]);

        this.requestUrl = extractUrl(lines[0]);

        this.headers = extractHeaders(lines);
    }

    private Map<String, String> extractHeaders(String[] lines) {
        Map<String, String> result = new HashMap<>();

        for (int i = 1; i < lines.length; i++) {
            if (lines[i].equals("") || lines[i] == null || lines[i].equals(System.lineSeparator())){ break; }

            String[] headerTokens = lines[i].split(": ");
            result.put(headerTokens[0], headerTokens[1]);
        }

        return result;
    }

    private String extractUrl(String line) {
        return line.split("\\s+")[1];
    }

    private String extractMethod(String line) {
        return line.split("\\s+")[0];
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }
}
