package server;

import server.http.RequestHandler;
import server.utils.Reader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    private static final int SOCKET_TIMEOUT = 2000;
    private static final String SOCKET_TIMEOUT_MESSAGE = "Timeout detected";


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



                String requestString = Reader.readAllBytes(clientSocket.getInputStream());
                this.handler.handleRequest(requestString);


                clientSocket.close();
                System.out.println("Received request on port " + this.port);
            }catch (SocketTimeoutException e){
                System.out.println(SOCKET_TIMEOUT_MESSAGE);
            }


        }
    }
}
