package server;

import java.io.IOException;

public class StartUp {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Server server = new Server(8000);
        server.start();
    }



}
