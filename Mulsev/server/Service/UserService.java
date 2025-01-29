package Mulsev.server.Service;

import java.io.PrintWriter;

public interface UserService {

    void addUser(String userKey, PrintWriter out); //유저 추가
    void removeUser(String userKey); //유저 삭제
    String generateUserKey(String threadName); //유저 키 생성
    
}
