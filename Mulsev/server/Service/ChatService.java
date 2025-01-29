package Mulsev.server.Service;

//채팅 서버 서비스
public interface ChatService {

    boolean startChat(String userKey, String message); //채팅 서버 시작
    void sendPublicMessage(String senderKey, String message); //공개 메세지(전체 메세지)
    void sendPrivateMessage(String senderKey, String receiverKey, String message); //비밀 메세지(귓속말, 1:1 대화)
    void systemMessage(String userKey, String message); //전체 시스템 메세지
    void systemMessageTo(String userKey, String message); //특정 유저에게 시스템 메세지
    
}
