package Mulsev.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import Mulsev.config.Config;
import Mulsev.server.controller.ServerManager;
import Mulsev.server.controller.UserManager;
import Mulsev.server.data.UsersData;

//익명 채팅 서버
public class ServerApp {

    public static void main(String[] args) {
        final int port = Config.PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running.. PORT: " + port);
            
            UserManager userManager = new UserManager(new UsersData());
            ServerManager serverManager = new ServerManager(userManager);

            while (true) {
                Socket socket = serverSocket.accept();

                ServerConnectionThread serverThread = new ServerConnectionThread(socket, serverManager);
                serverThread.start();
            }
            
        } catch (SocketException e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }  
}
