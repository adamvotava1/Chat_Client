import models.chatClients.ChatClient;
import models.chatClients.InMemoryChatClient;
import models.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        //testChat();
        MainFrame window = new MainFrame(800, 600);
    }

    private static void testChat(){
        ChatClient chatClient = new InMemoryChatClient();

        chatClient.login("votavad1");

        chatClient.sendMessage("Ahoj");
        chatClient.sendMessage("Ahoj2");

        chatClient.logout();
    }
}
