package Mulsev.clientSwing;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import Mulsev.config.Config;
import java.io.IOException;
import java.net.Socket;

public class ClientApp2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // 소켓 연결
                Socket socket = new Socket(Config.HOST, Config.PORT);
                System.out.printf("Connected server.. host: %s, port: %d%n", Config.HOST, Config.PORT);

                // ChatClientGUI 표시
                ClientGUI clientFrame = new ClientGUI(socket);
                clientFrame.setVisible(true);

                clientFrame.startListening(socket);

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "서버에 연결할 수 없습니다.", 
                    "Connection Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}