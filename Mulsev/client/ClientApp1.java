package Mulsev.client;

import java.util.Scanner;

import Mulsev.config.Config;

import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

public class ClientApp1 {

    public static void main(String[] args) {
        final String host = Config.HOST;
        final int port = Config.PORT;
        
        try (Socket socket = new Socket(host, port)) {
            System.out.printf("Connected server.. host: %s, port: %d%n", host, port);

            ClientListenerThread reader = new ClientListenerThread(socket);
            Scanner sc = new Scanner(System.in, "Euc-kr");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            new ClientChatRunner(out, sc, reader).onStart();;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}