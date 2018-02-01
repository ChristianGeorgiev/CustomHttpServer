package server.http.response;

public interface Response {
    void addHeader(String header, String value);

    String getResponse();
}
