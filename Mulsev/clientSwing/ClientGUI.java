package Mulsev.clientSwing;

import javax.swing.*;
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.awt.BorderLayout;
import java.awt.Insets;

public class ClientGUI extends JFrame {

    private JTextArea chatArea; //메세지 표시 영역
    private JTextField inputField; //메세지 입력 필드

    private JButton sendButton; //전송 버튼

    private PrintWriter out;
    private Socket socket;

    public ClientGUI(Socket socket) {
        this.socket = socket;

        setTitle("익명 채팅 프로그램");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(null);

        //채팅 표시 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setMargin(new Insets(5, 10, 5, 10));
        JScrollPane scrollPane = new JScrollPane(chatArea);

        //입력 영역
        inputField = new JTextField(30);
        sendButton = new JButton("전송");

        //상단 패널
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        topPanel.add(new JLabel("도움말: /help"), BorderLayout.CENTER);
        topPanel.add(new JLabel("PORT: " + socket.getLocalPort()), BorderLayout.EAST);

        //하단 패널 구성
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        

        //레이아웃 배치
        add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //전송 버튼, 엔터키
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
    }

    //메세지 전송
    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            if (message.toLowerCase().trim().equals("/q")) {
                out.println(message);

                try { Thread.sleep(500); } catch (InterruptedException ignored) {} //0.5초 대기

                SwingUtilities.invokeLater(() -> {
                    closeSocket();
                    dispose();
                });
                return;

            }
            out.println(message);
        }
        inputField.setText("");
        inputField.requestFocus();
    }

    //스레드
    public void startListening(Socket socket) {
        new Thread(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String serverMessage;
                while (((serverMessage = in.readLine()) != null)) {
                    replaceMessage(serverMessage);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeSocket();
            }
        }).start();
    }

    //메세지 줄바꿈 있나 확인
    private void replaceMessage(String serverMessage) {
        if (serverMessage.contains("/")) {
            String message = serverMessage
                .replace("//n", "__temp__")
                .replace("/n", "\n")
                .replace("__temp__", "/n");

                SwingUtilities.invokeLater(() -> appendMessage(message));

        } else {
            SwingUtilities.invokeLater(() -> appendMessage(serverMessage));
        }
    }

    //서버로부터 메세지 받음
    public void appendMessage(String msg) {
        chatArea.append(msg + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength()); //자동 스크롤
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