package models.chatClients;

import models.Message;
import models.database.DatabaseOperations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DatabaseChatClient implements ChatClient{
    private String loggedUser;
    private List<String> loggedUsers;
    private List<Message> messages;
    private List<ActionListener> listeners = new ArrayList<>();
    private DatabaseOperations databaseOperations;

    public DatabaseChatClient(DatabaseOperations databaseOperations) {
        this.databaseOperations = databaseOperations;
        loggedUsers = new ArrayList<>();
        messages = databaseOperations.getMessages();
    }

    @Override
    public void sendMessage(String text) {
        Message msg = new Message(loggedUser,text);
        messages.add(msg);
        databaseOperations.addMessage(msg);
        raiseEventMessagesChanged();
    }

    @Override
    public void login(String userName) {
        loggedUser = userName;
        loggedUsers.add(userName);
        addSystemMessage(Message.USER_LOGGED_IN, loggedUser);
        System.out.println("user logged in - " + userName);
        raiseEventLoggedUsersChanged();
    }

    @Override
    public void logout() {
        addSystemMessage(Message.USER_LOGGED_OUT, loggedUser);
        loggedUsers.remove(loggedUser);
        loggedUser = null;
        System.out.println("user logged out");
        raiseEventLoggedUsersChanged();
    }

    @Override
    public boolean isAuthenticated() {
        boolean isAuthenticated = loggedUser != null;
        System.out.println("user logged: " + isAuthenticated);
        return isAuthenticated;
    }

    @Override
    public List<String> getLoggedUsers() {
        return loggedUsers;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void addActionListener(ActionListener toAdd) {
        listeners.add(toAdd);
    }

    private void raiseEventLoggedUsersChanged(){
        for (ActionListener al:
                listeners) {
            al.actionPerformed(
                    new ActionEvent(
                            this,
                            1,
                            "usersChanged"
                    )
            );
        }
    }

    private void raiseEventMessagesChanged(){
        for (ActionListener al:
                listeners) {
            al.actionPerformed(
                    new ActionEvent(
                            this,
                            2,
                            "messagesChanged"
                    )
            );
        }
    }

    private void addSystemMessage(int type, String username){
        messages.add(new Message(type, username));
        raiseEventMessagesChanged();
    }
}
