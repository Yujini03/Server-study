package Mulsev.server.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import Mulsev.server.Service.UserService;
import Mulsev.server.data.UsersData;

//유저 세팅 메서드 제공 클래스
public class UserManager implements UserService {

    private final UsersData usersData;

    public UserManager(UsersData usersData) {
        this.usersData = usersData;
    }

    @Override
    public void addUser(String userKey, PrintWriter out) {
        usersData.addUser(userKey, out);
    }

    @Override
    public void removeUser(String userKey) {
        usersData.removeUser(userKey);
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
    public boolean containsUser(String userKey) {
        return usersData.containsUser(userKey);
    } 

    public Set<String> getUserKeys() {
        return usersData.userKeys();
    }

    //유저 이름(map key) 생성
    public String generateUserKey(String threadName) {
        String[] parts = threadName.split("-");
        if (parts.length < 2) {
            return "user_" + threadName;
        }
        return "user" + parts[1];
    }

}
