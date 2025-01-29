package Mulsev.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

//클라이언트 스레드 생성
public class ClientListenerThread extends Thread {

    private Socket socket;

    public ClientListenerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String serverMessage;
            
            while (((serverMessage = in.readLine()) != null) && !isInterrupted()) {
                System.out.println("\r" + " ".repeat(20) + "\r" + serverMessage);
                System.out.print("메세지 입력 -> ");
            }


        } catch (IOException e) {
            if (!isInterrupted()) {
                e.printStackTrace();
            }
        }
    }
    
}
