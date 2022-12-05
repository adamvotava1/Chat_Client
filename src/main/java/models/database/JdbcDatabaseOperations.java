package models.database;

import models.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcDatabaseOperations implements DatabaseOperations{
    private final Connection connection;
    public JdbcDatabaseOperations(String driver, String url) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        this.connection = DriverManager.getConnection(url);
    }


    @Override
    public void addMessage(Message message) {
        try {
            String sql =
                    "INSERT INTO ChatMessages (author, text, created)"
                    + "VALUES ("
                        +"'" + message.getAuthor() + "', "
                        +"'" + message.getText() + "', "
                        +"'" + Timestamp.valueOf(message.getCreated()) + "')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> getMessages() {
        final List<Message> messages = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM chatmessages order by created");
            while(rs.next()) {
                String author = rs.getString("author");
                String text = rs.getString("text");
                messages.add(new Message(author, text));
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return messages;
    }
}
