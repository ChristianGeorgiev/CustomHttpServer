package server.http;

import server.http.response.HttpResponse;
import server.utils.Reader;
import server.utils.Writer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private Socket clientSocket;

    private InputStream clientSocketInputStream;

    private OutputStream clientSocketOutputStream;

    private RequestHandler handler;

    public ConnectionHandler(Socket clientSocket, RequestHandler handler) {
        this.clientSocket = clientSocket;
        this.handler = handler;
        initializeConnection();
    }

    private void initializeConnection() {
        try {
            this.clientSocketInputStream = this.clientSocket.getInputStream();
            this.clientSocketOutputStream = this.clientSocket.getOutputStream();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
                                //====== Handling request ======\\
            String requestString = Reader.readAllBytes(this.clientSocketInputStream); //Receives the request
            String responseString = this.handler.handleRequest(requestString); //Handles the request and creates a response
            Writer.writeAllBytes(responseString, this.clientSocketOutputStream); //Returns the response
                            //====== Finished handling request ======\\

            this.clientSocketInputStream.close();
            this.clientSocketOutputStream.close();
            this.clientSocket.close();
        }catch (IOException e){
            try {
                Writer.writeAllBytes(new HttpResponse(HttpCode.INTERNAL_SERVER_ERROR, new File(WebConstants.DEFAULT_500_PAGE_PATH)).getResponse(), this.clientSocketOutputStream);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
