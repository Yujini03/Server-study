package Mulsev.server.Service;

import java.io.PrintWriter;
import java.util.List;

//나중에 데이터 베이스 ?
//유저 관리 서비스
public interface UserService {

    void addUser(String userKey, PrintWriter out);
    void removeUser(String userKey);
    String generateUserKey(String threadName); //유저 키 생성
    PrintWriter getUser(String userKey);
    public List<PrintWriter> getOtherUsers(String excludedKey); //특정 유저 제외 나머지
    boolean containUser(String userKey);
    
}
