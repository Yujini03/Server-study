package Mulsev.server.controller;

import java.io.PrintWriter;
import java.util.List;

import Mulsev.server.Service.UserService;
import Mulsev.server.data.UsersData;

//유저 세팅 메서드 제공 클래스
public class UserManager implements UserService {

    protected final UsersData usersData;

    public UserManager(UsersData usersData) {
        this.usersData = usersData;
    }

    @Override
    public void addUser(String userKey, PrintWriter out) {
        usersData.addUser(userKey, out);
        systemMessage(userKey, "님이 입장하였습니다. ----------");
        systemMessageTo(userKey, userKey+ "님, 익명 채팅 서버에 오신 걸 환영합니다! 도움말 명령어는 /help입니다.");
    }

    @Override
    public void removeUser(String userKey) {
        usersData.removeUser(userKey);
        systemMessage(userKey, "님이 퇴장하였습니다. ----------");
    }

    //유저 이름(map key) 생성
    @Override
    public String generateUserKey(String threadName) {
        String[] parts = threadName.split("-");
        if (parts.length < 2) {
            return "user_" + threadName;
        }
        return "user" + parts[1];
    }

    @Override
    public PrintWriter getUser(String userKey) {
        return usersData.getUser(userKey);
    }

    @Override
    public List<PrintWriter> getOtherUsers(String excludedKey) {
        return usersData.getOtherUsers(excludedKey);
    }

    @Override
    public boolean containUser(String userKey) {
        return usersData.containUser(userKey);
    }

    //전체 시스템 메세지
    private void systemMessage(String userKey, String message) {
        getOtherUsers(userKey).forEach(out ->
            out.println("System: \"" + userKey + "\"" + message)
        );
    }

    //특정 유저에게만 시스템 메시지
    protected void systemMessageTo(String userKey, String message) {
        PrintWriter out = getUser(userKey);
        if (out != null) {
            out.println("System: " + message);
        }
    }

}
