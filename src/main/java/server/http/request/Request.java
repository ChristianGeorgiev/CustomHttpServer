package server.http.request;

import java.util.Map;

public interface Request {
    String getRequestUrl();

    String getRequestMethod();

    Map<String, String> getCookies();

    Map<String, String> getHeaders();



}
