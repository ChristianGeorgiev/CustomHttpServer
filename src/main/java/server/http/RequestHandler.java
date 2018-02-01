package server.http;

import server.http.request.HttpRequest;
import server.http.request.Request;
import server.http.response.HttpResponse;
import server.http.response.Response;

public class RequestHandler {

    // Returns the response as a String
    public String handleRequest(String requestString){
        Request request = new HttpRequest(requestString);
        Response response = new HttpResponse(request.getRequestUrl(), HttpCode.OK);

        response.addHeader("Content-Type", "text/html");
        response.addHeader("Date", "ei sq");
        response.setContent("<h1>Response Class works!</h1>");

        return response.getResponse();
    }

    private String createTestHttpResponse(){
        StringBuilder sb = new StringBuilder();

        sb.append("HTTP/1.1 200 OK").append(System.lineSeparator());
        sb.append("Content-Type: text/html").append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("<h1>Hello From the server!</h1>").append(System.lineSeparator());
        sb.append("<h2>It works, obviously...</h2>");

        return sb.toString();
    }


}
