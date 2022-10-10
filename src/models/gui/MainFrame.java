package models.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    JTextArea txtChatArea;

    public MainFrame(int width, int height){
        super("ChatClient");
        setSize(width,height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initGui();
        setVisible(true);
    }

    private void initGui(){
        JPanel panelMain = new JPanel(new BorderLayout());

        panelMain.add(initLoginPanel(), BorderLayout.NORTH);
        panelMain.add(initChatPanel(), BorderLayout.CENTER);
        panelMain.add(initMessagePanel(), BorderLayout.SOUTH);

        add(panelMain);
    }

    private JPanel initLoginPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("username"));

        JTextField txtInputUserName = new JTextField("", 30);
        panel.add(txtInputUserName);


        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn login clicked - " + txtInputUserName.getText());
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

        JScrollPane scrollPane = new JScrollPane(txtChatArea);
        panel.add(scrollPane);

        /*for(int i = 0; i< 50; i++) {
            txtChatArea.append("Message" + i + "\n");
        }*/
        return panel;
    }

    private JPanel initMessagePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtInputMessage = new JTextField("", 50);
        panel.add(txtInputMessage);

        JButton btnSendMessage = new JButton("Send");
        btnSendMessage.addActionListener(e -> {
            System.out.println("btn send clicked - " + txtInputMessage.getText());
            txtChatArea.append(txtInputMessage.getText() + "\n");
            txtInputMessage.setText("");
        });
        panel.add(btnSendMessage);
        return panel;
    }
}
