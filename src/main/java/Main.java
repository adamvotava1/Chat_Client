import models.Message;
import models.chatClients.ChatClient;
import models.chatClients.DatabaseChatClient;
import models.chatClients.FileChatClient;
import models.chatClients.InMemoryChatClient;
import models.chatClients.api.ApiChatClient;
import models.chatClients.fileOperations.ChatFileOperations;
import models.chatClients.fileOperations.JsonChatFileOperations;
import models.database.DatabaseOperations;
import models.database.DbInitializer;
import models.database.JdbcDatabaseOperations;
import models.gui.MainFrame;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //http://fimuhkpro22021.aspifyhost.cz/swagger/index.html

        final String databaseDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        final String databaseUrl = "jdbc:derby:ChatClientDb_skC";

        DbInitializer dbInitializer = new DbInitializer(databaseDriver, databaseUrl);
        dbInitializer.init();

        DatabaseOperations databaseOperations;
        try{
            databaseOperations =
                    new JdbcDatabaseOperations(databaseDriver, databaseUrl);
                    //databaseOperations.addMessage(new Message("Votava", "pokusný text"));
        }catch (Exception e){
            e.printStackTrace();
            return;
        }

        ChatFileOperations chatFileOperations = new JsonChatFileOperations();

        //ChatClient chatClient = new FileChatClient(chatFileOperations);
        ChatClient chatClient = new DatabaseChatClient(databaseOperations);
        //ChatClient chatClient = new InMemoryChatClient();
        //ChatClient chatClient = new ApiChatClient();

        MainFrame window = new MainFrame(800, 600,chatClient);
    }

    private static void testChat(){
        ChatClient chatClient = new InMemoryChatClient();

        chatClient.login("votavad1");

        chatClient.sendMessage("Ahoj");
        chatClient.sendMessage("Ahoj2");

        chatClient.logout();
    }

    private static List<Field> getAllFields(Class<?> cls){
        List<Field> fieldList = new ArrayList<>();
        for (Field f:
                cls.getDeclaredFields()) {
            fieldList.add(f);
        }
        return fieldList;
    }
}
