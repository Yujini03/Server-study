package Mulsev.server.controller;

import java.io.PrintWriter;

import Mulsev.server.Service.ChatService;
import Mulsev.server.data.UsersData;

//Server 관리 클래스
public class ServerManager extends UserManager implements ChatService {

    private final CommandHandler commandHandler;

    public ServerManager(UsersData usersData) {
        super(usersData);
        this.commandHandler = new CommandHandler(this);
    }

    @Override
    public boolean startChat(String userKey, String message) {

        if (message.startsWith("/")) {
            return commandHandler.command(userKey, message);

        }
        sendPublicMessage(userKey, message);
        return true;
    }

    @Override
    public void sendPublicMessage(String senderKey, String message) {
        getOtherUsers(senderKey).forEach(out ->
            out.println("[" + senderKey + "]: " + message)
        );
        systemMessageTo(senderKey, "전체 메세지 전송 완료");
    }

    @Override
    public void sendPrivateMessage(String senderKey, String receiverKey, String message) {
        PrintWriter receiverwriter = getUser(receiverKey);
        if (receiverwriter != null) {
            receiverwriter.println("[(귓속말) " + senderKey + "]: " + message);
            systemMessageTo(senderKey, "귓속말 전송 완료");
        } else {
            systemMessageTo(senderKey, " 님을 찾을 수 없습니다.");
        }
    }


    // public String getImagePath() {
    //     return serverData.getImagePath();
    // }

    // public String getImageName(int index) {
    //     return serverData.getImageName(index);
    // }
    
}
