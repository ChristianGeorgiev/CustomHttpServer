package server;

import server.http.RequestHandler;
import server.utils.Reader;
import server.utils.Writer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    private static final int SOCKET_TIMEOUT = 2000;
    private static final String SOCKET_TIMEOUT_MESSAGE = "Timeout detected";
    private static final String HANDLED_REQUEST_MESSAGE = "Handled request on port: ";


    private int port;
    private ServerSocket serverSocket;
    private RequestHandler handler;


    public Server(int port) {
        this.port = port;
        this.handler = new RequestHandler();
    }

    public void run() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        while (true){

            try {
                Socket clientSocket = this.serverSocket.accept();
                clientSocket.setSoTimeout(SOCKET_TIMEOUT);


                //====== Handling request ======
                String requestString = Reader.readAllBytes(clientSocket.getInputStream()); //Receives the request
                String responseString = this.handler.handleRequest(requestString); //Handles the request and creates a response

                Writer.writeAllBytes(responseString, clientSocket.getOutputStream()); //Returns the response
                //====== Finished handling request ======


                clientSocket.close();
                System.out.println(HANDLED_REQUEST_MESSAGE + this.port);

            }catch (SocketTimeoutException e){
                System.out.println(SOCKET_TIMEOUT_MESSAGE);
            }


        }
    }
}
