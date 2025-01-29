package Mulsev.client;

import java.io.PrintWriter;
import java.util.Scanner;

//클라이언트 실행 클래스
public class ClientChatRunner {

    private final PrintWriter out;
    private final Scanner sc;
    private final ClientListenerThread reader;

    public ClientChatRunner(PrintWriter out, Scanner sc, ClientListenerThread reader) {
        this.out = out;
        this.sc = sc;
        this.reader = reader;
    }

    //호출
    public void onStart() {
        reader.start();
        runInputLoop();
    }

    private void runInputLoop() {
        while (true) {
            String message = sc.nextLine();
            out.println(message);
            if (message.toLowerCase().equals("/q")) {
                System.out.println("disconnect");
                reader.interrupt();
                break;
            }
        }
    }

}
