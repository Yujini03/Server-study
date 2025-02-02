package Mulsev.clientSwing2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;

public class ConnectionGUI extends JFrame {
    private JTextField hostField;
    private JTextField portField;
    private JButton connectButton;

    public ConnectionGUI() {
        setTitle("서버 연결 설정");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        //컴포넌트
        hostField = new JTextField(15);
        portField = new JTextField(15);
        connectButton = new JButton("Connect");

        //레이아웃
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        
        gbc.gridx = 0; 
        gbc.gridy = 0;
        panel.add(new JLabel("Host(IP):"), gbc);

        gbc.gridx = 1; 
        gbc.gridy = 0;
        panel.add(hostField, gbc);

        gbc.gridx = 0; 
        gbc.gridy = 1;
        panel.add(new JLabel("Port:"), gbc);

        gbc.gridx = 1; 
        gbc.gridy = 1;
        panel.add(portField, gbc);

        gbc.gridx = 0; 
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(connectButton, gbc);

        add(panel);

        connectButton.addActionListener(this::connect);

        setVisible(true);
    }

    private void connect(ActionEvent e) {
        String host = hostField.getText().trim();
        String portText = portField.getText().trim();
        
        int port;
        try {
            port = Integer.parseInt(portText);
        } catch (NumberFormatException ex) {
        	if (!portText.isEmpty()) {
        		JOptionPane.showMessageDialog(
        			this,
                    "포트 번호는 숫자만 입력 가능합니다.",
                    "Port Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }           
            return;
        }

        //서버 연결 시도
        try {
            Socket socket = new Socket(host, port);
            System.out.printf("Connected server.. host: %s, port: %d%n", host, port);

            ClientGUI clientFrame = new ClientGUI(socket);
            clientFrame.setVisible(true);
            clientFrame.startListening(socket);

            dispose();

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "서버에 연결할 수 없습니다.\n" + ex.getMessage(),
                "Connection Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
