package Mulsev.server.controller;

import java.io.PrintWriter;
import java.util.Set;

import Mulsev.server.Service.ChatService;

//Server 관리 클래스
public class ServerManager implements ChatService {

    private final CommandHandler commandHandler;
    private final UserManager userManager;

    public ServerManager(UserManager userManager) {
        this.userManager = userManager;
        this.commandHandler = new CommandHandler(this);
    }

    @Override
    public boolean startChat(String userKey, String message) {

        if (message.startsWith("/") && !message.startsWith("/n")) {
            return commandHandler.command(userKey, message);

        }
        sendPublicMessage(userKey, message);
        return true;
    }

    @Override
    public void sendPublicMessage(String senderKey, String message) {
        userManager.getOtherUsers(senderKey).forEach(out ->
            out.println("[" + senderKey + "]: " + message)
        );
        systemMessageTo(senderKey, "전체 메세지 전송 완료");
    }

    @Override
    public void sendPrivateMessage(String senderKey, String receiverKey, String message) {
        PrintWriter receiverwriter = userManager.getUser(receiverKey);
        if (receiverwriter != null) {
            receiverwriter.println("[(귓속말) " + senderKey + "]: " + message);
            systemMessageTo(senderKey, "귓속말 전송 완료");
        } else {
            systemMessageTo(senderKey, " 님을 찾을 수 없습니다.");
        }
    }

    //전체 시스템 메세지
    @Override
    public void systemMessage(String userKey, String message) {
        userManager.getOtherUsers(userKey).forEach(out ->
            out.printf("System: \"%s\" %s%n", userKey, message)
        );
    }

    //특정 유저에게만 시스템 메시지
    @Override
    public void systemMessageTo(String userKey, String message) {
        PrintWriter out = userManager.getUser(userKey);
        if (out != null) {
            out.println("System: " + message);
        }
    }

    public String onUserJoin(String threadName, PrintWriter out) {
        String userKey = userManager.generateUserKey(threadName);
        userManager.addUser(userKey, out);
        systemMessage(userKey, "님이 입장하였습니다. ----------");
        systemMessageTo(userKey, userKey+ "님, 익명 채팅 서버에 오신 걸 환영합니다! 도움말 명령어는 /help입니다.");
        return userKey;
    }

    public void onUserLeave(String userKey) {
        userManager.removeUser(userKey);
        systemMessage(userKey, "님이 퇴장하였습니다. ----------");
    }

    public String getUserKeysString() {
        Set<String> userKeys = userManager.getUserKeys();
        String userKeysString = String.join(", ", userKeys);
        return "유저 목록 (" + userKeysString + ")";
    }


    // public String getImagePath() {
    //     return serverData.getImagePath();
    // }

    // public String getImageName(int index) {
    //     return serverData.getImageName(index);
    // }
    
}
