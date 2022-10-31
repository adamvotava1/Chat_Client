package models.chatClients.fileOperations;

import models.Message;

import java.util.ArrayList;
import java.util.List;

public interface ChatFileOperations {
    List<Message> messages = new ArrayList<Message>();
    public void writeMessages(List<Message> messages);
    public List<Message> readMessages();
}
