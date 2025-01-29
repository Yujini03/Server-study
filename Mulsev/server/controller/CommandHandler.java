package Mulsev.server.controller;

//서버 명령어 처리 클래스
public class CommandHandler {

    private final ServerManager serverManager;

    public CommandHandler(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    public boolean command(String userKey, String message) {

        String[] parts = message.stripLeading().split(" ", 3);
        String prefix = parts[0].toLowerCase();

        switch (prefix) {
            case "/w": // 귓속말
                if (parts.length < 3) {
                    serverManager.systemMessageTo(userKey, "올바른 형식: /w [상대이름] [메시지]");
                } else {
                    String receiverKey = parts[1];
                    String whisperMessage = parts[2];
                    if (receiverKey.equals(userKey)) {
                        serverManager.systemMessageTo(userKey, "자신에게 귓속말은 불가능합니다.");
                    } else {
                        serverManager.sendPrivateMessage(userKey, receiverKey, whisperMessage);
                    }
                }
                return true;

            case "/q": // 종료
                serverManager.systemMessageTo(userKey, "서버 연결을 종료합니다.");
                System.out.println(userKey + ": disconnect request");
                return false;

            case "/help": // 도움말
                String helpText = """
                    [도움말 안내]
                    /w [이름] [메시지] : 특정 유저에게 귓속말
                    /q : 서버 연결 종료
                    /help : 도움말 보기""";
                serverManager.systemMessageTo(userKey, helpText);
                return true;

            default:
                // 존재하지 않는 명령
                serverManager.systemMessageTo(userKey, "알 수 없는 명령어입니다. (/help로 도움말 확인)");
                return true;
        }
    }
    
}
