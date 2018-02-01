package server.http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest implements Request {
    private String method;
    private String requestUrl;
    private Map<String, String> headers;
    private Map<String, String> cookies;


    public HttpRequest(String requestString) {
        this.headers = new HashMap<>();
        this.cookies = new HashMap<>();
        extractParametersFromString(requestString);
    }




    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public String getRequestMethod() {
        return this.method;
    }

    @Override
    public Map<String, String> getCookies() {
        return this.cookies;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }




    private void extractParametersFromString(String requestString) {
        if (requestString == null || requestString.equals("")) {
            return;
        }

        String[] lines = requestString.split("[\r\n]+");

        this.method = extractMethod(lines[0]);

        this.requestUrl = extractUrl(lines[0]);

        this.headers = extractHeaders(lines);

        if (this.headers.containsKey("Cookie")) {
            this.cookies = extractCookiesFromHeader(this.headers.get("Cookie"));
        }
    }

    private Map<String, String> extractHeaders(String[] lines) {
        Map<String, String> result = new HashMap<>();

        for (int i = 1; i < lines.length; i++) {
            if (lines[i].equals("") || lines[i] == null || lines[i].equals(System.lineSeparator())) {
                break;
            }

            String[] headerTokens = lines[i].split(": ");
            result.put(headerTokens[0], headerTokens[1]);
        }


        return result;
    }

    private Map<String, String> extractCookiesFromHeader(String cookieHeader) {

        Map<String, String> result = new HashMap<>();
        String[] cookieTokens = cookieHeader.split("; ");

        for (String pair : cookieTokens) {
            String[] cookiePair = pair.split("=");
            String cookie = cookiePair[0];
            String value;

            if (cookiePair.length > 2){
                String[] valueTokens = new String[cookiePair.length-1];
                for (int i = 1; i < cookiePair.length; i++) {
                    valueTokens[i-1] = cookiePair[i];
                }
                value = String.join("=", valueTokens);
            }else {
                value = cookiePair[1];
            }

            result.put(cookie, value);
        }

        return result;
    }

    private String extractUrl(String line) {
        return line.split("\\s+")[1];
    }

    private String extractMethod(String line) {
        return line.split("\\s+")[0];
    }


}
