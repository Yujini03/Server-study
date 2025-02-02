package Mulsev.server.data;

import java.io.PrintWriter;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//서버 데이터 저장
//유저 이름(users key): 스레드 번호로 정해짐
public class UsersData {

    private static final Map<String, PrintWriter> users = new ConcurrentHashMap<>();

    
    public void addUser(String userKey, PrintWriter out) {
        users.put(userKey, out);
    }

    public PrintWriter getUser(String userKey) {
        return users.get(userKey);
    }

    public List<PrintWriter> getOtherUsers(String excludedKey) {
        return  users.entrySet().stream()
                .filter(e -> !e.getKey().equals(excludedKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public void removeUser(String userKey) {
        users.remove(userKey);
    }

    public boolean containsUser(String userKey) {
        return users.containsKey(userKey);
    }

    public Set<String> userKeys() {
        return users.keySet();
    }
    
}
