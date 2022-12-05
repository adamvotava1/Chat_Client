package models.gui;

import models.Message;
import models.chatClients.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    JTextArea txtChatArea;
    private final ChatClient chatClient;
    private JTextField txtInputMessage;

    public MainFrame(int width, int height, ChatClient chatClient){
        super("ChatClient");
        setSize(width,height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.chatClient = chatClient;

        initGui();
        setVisible(true);
    }

    private void initGui(){
        JPanel panelMain = new JPanel(new BorderLayout());

        panelMain.add(initLoginPanel(), BorderLayout.NORTH);
        panelMain.add(initChatPanel(), BorderLayout.CENTER);
        panelMain.add(initMessagePanel(), BorderLayout.SOUTH);
        panelMain.add(initLoggedUsersPanel(), BorderLayout.EAST);

        add(panelMain);
    }

    private JPanel initLoginPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("username"));

        JTextField txtInputUserName = new JTextField("", 30);
        panel.add(txtInputUserName);


        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            String userName = txtInputUserName.getText();
            System.out.println("btn login clicked - " + txtInputUserName.getText());

            if(chatClient.isAuthenticated()){
                //LOGOUT
                chatClient.logout();
                btnLogin.setText("Login");
                txtInputUserName.setEditable(true);
                txtInputMessage.setEnabled(false);
                txtChatArea.setEnabled(false);
            }
            else {
                if(userName.length()<1){
                    JOptionPane.showMessageDialog(
                            null,"Enter your userName","Error",JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                chatClient.login(userName);
                btnLogin.setText("Logout");
                txtInputUserName.setEditable(false);
                txtInputMessage.setEnabled(true);
                txtChatArea.setEnabled(true);
            }
        });

        panel.add(btnLogin);

        return panel;
    }

    private JPanel initChatPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        txtChatArea = new JTextArea();
        txtChatArea.setEditable(false);
        txtChatArea.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(txtChatArea);
        panel.add(scrollPane);


        chatClient.addActionListener(e -> {
            if(e.getID()==2) refreshMessages();
        });
        return panel;
    }

    private JPanel initLoggedUsersPanel() {
        JPanel panel = new JPanel();

        JTable tblLoggedUsers = new JTable();
        LoggedUsersTableModel loggedUsersTableModel = new LoggedUsersTableModel(chatClient);
        tblLoggedUsers.setModel(loggedUsersTableModel);

        chatClient.addActionListener(
                e -> {
            if(e.getID()==1){
                loggedUsersTableModel.fireTableDataChanged();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblLoggedUsers);
        scrollPane.setPreferredSize(new Dimension(250, 500));
        panel.add(scrollPane);
        return panel;
    }

    private JPanel initMessagePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtInputMessage = new JTextField("", 50);
        txtInputMessage.setEnabled(false);
        panel.add(txtInputMessage);

        JButton btnSendMessage = new JButton("Send");
        btnSendMessage.addActionListener(e -> {
            String msgText = txtInputMessage.getText();
            //System.out.println("btn send clicked - " + txtInputMessage.getText());
            txtChatArea.append(txtInputMessage.getText() + "\n");

            if(msgText.length() ==0)
                return;
            if(!chatClient.isAuthenticated())
                return;

            chatClient.sendMessage(msgText);
            txtInputMessage.setText("");
            refreshMessages();
        });
        panel.add(btnSendMessage);
        return panel;
    }

    private void refreshMessages() {
        if(!chatClient.isAuthenticated()) return;
        txtChatArea.setText("");
        for (Message msg:
            chatClient.getMessages()) {
            txtChatArea.append(msg.toString());
            txtChatArea.append("\n");
        }
    }
}
