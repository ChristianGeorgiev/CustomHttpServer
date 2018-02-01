package server.http;

public class RequestHandler {

    // Returns the response as a String
    public String handleRequest(String requestString){
        return createTestHttpResponse();
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
