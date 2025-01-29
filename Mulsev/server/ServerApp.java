package Mulsev.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Mulsev.config.Config;
import Mulsev.server.controll.ServerManager;
import Mulsev.server.data.ServerData;

//익명 채팅 서버
public class ServerApp {

    public static void main(String[] args) {
        final int port = Config.PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running.. PORT: " + port);
            
            ServerManager serverManager = new ServerManager(new ServerData());

            while (true) {
                Socket socket = serverSocket.accept();

                ServerConnectionThread serverThread = new ServerConnectionThread(socket, serverManager);
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
