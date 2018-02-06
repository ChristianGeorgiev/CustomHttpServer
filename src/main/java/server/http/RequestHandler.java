package server.http;

import server.http.request.HttpRequest;
import server.http.request.Request;
import server.http.response.HttpResponse;
import server.http.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

    private Map<String, File> views = new HashMap<>();


    public String handleRequest(String requestString) {
        scanApplicationForHtmlFiles(new File(WebConstants.APP_FOLDER_PATH));
        Request request = new HttpRequest(requestString);

        if (!this.views.containsKey(request.getRequestUrl())){
            return new HttpResponse(HttpCode.NOT_FOUND,
                    new File(WebConstants.DEFAULT_404_PAGE_PATH)).getResponse();
        }

        Response response = new HttpResponse(HttpCode.OK, this.views.get(request.getRequestUrl()));
        return response.getResponse();
    }

    private void scanApplicationForHtmlFiles(File dir) {
        if (dir.isFile() && dir.getName().endsWith(".html")){
            this.views.put(configureRoutingName(dir.getPath()), dir);
        }
        File[] children = dir.listFiles();
        for (File child : children) {
            try {
                scanApplicationForHtmlFiles(child);
            }catch (NullPointerException e){
                continue;
            }
        }
    }

    private String configureRoutingName(String name) {
        String[] tokens = name.split("application");
        return tokens[tokens.length-1].replace("\\", "/");
    }


}
