package Mulsev.server.data;

import java.io.PrintWriter;
import java.util.Map;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

//서버 데이터 저장
//유저 이름(users key): 스레드 번호로 정해짐
public class ServerData {

    // private final String imagePath = "C:/Users/youji/OneDrive/workspace/java_va/src/Mulsev/server/data/images/";

    // private final String[] imageNames = {
    //     "lol.png", "valo.png", "over.png"
    // };

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

    public boolean containUser(String userKey) {
        return users.containsKey(userKey);
    }

    // public String getImagePath() {
    //     return imagePath;
    // }

    // public String getImageName(int index) {
    //     if (index >= 0 && index < imageNames.length) {
    //         return imageNames[index];
    //     }
    //     return null;
    // }
    
}
