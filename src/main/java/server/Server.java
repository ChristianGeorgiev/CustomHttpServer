package server;

import server.http.ConnectionHandler;
import server.http.RequestHandler;
import server.utils.Reader;
import server.utils.Writer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.FutureTask;

public class Server {
    private static final int SOCKET_TIMEOUT = 5000;
    private static final String SOCKET_TIMEOUT_MESSAGE = "Timeout detected";
    private static final String HANDLED_REQUEST_MESSAGE = "Handled request on port: ";


    private int port;
    private ServerSocket serverSocket;
    private RequestHandler handler;


    public Server(int port) {
        this.port = port;
        this.handler = new RequestHandler();
    }


    public void start() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.serverSocket.setSoTimeout(SOCKET_TIMEOUT);

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){

            try {
                Socket clientSocket = this.serverSocket.accept();
                clientSocket.setSoTimeout(SOCKET_TIMEOUT);



                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, new RequestHandler());
                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();



                System.out.println(HANDLED_REQUEST_MESSAGE + this.port);

            }catch (Exception e){
                System.out.println(SOCKET_TIMEOUT_MESSAGE);
            }


        }
    }
}
