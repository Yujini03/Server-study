package Mulsev.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Mulsev.server.controll.ServerManager;

//서버 스레드
public class ServerConnectionThread extends Thread {
    

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private ServerManager serverManager;

    private String userKey;

    public ServerConnectionThread(Socket socket, ServerManager serverManager) {
        this.socket = socket;
        this.serverManager = serverManager;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //유저 키 생성
            userKey = serverManager.generateUserKey(getName());
            System.out.printf("join %s (ip%s)%n", userKey, socket.getInetAddress());

            //유저 추가
            serverManager.addUser(userKey, out);

            //메세지 처리 루프
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(userKey + " send -> " + message);

                boolean state = serverManager.startChat(userKey, message);

                if (!state) break;

            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            closeSocket();    
            serverManager.removeUser(userKey);   
        }
    }

    private void closeSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
