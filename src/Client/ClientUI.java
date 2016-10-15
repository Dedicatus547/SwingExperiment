package Client;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dedicatus on 2016/10/13.
 */
public class ClientUI extends JFrame {
    private ClientSocket socket = new ClientSocket();
    private JButton connect = new JButton("Connect");
    private JButton say = new JButton("Say");
    private JTextField port = new JTextField(15);
    private JTextField severIP = new JTextField(15);
    private JTextField sayField = new JTextField(40);
    private JTextArea txt = new JTextArea(17, 55);
    private JLabel ipLabel = new JLabel("Server IP: ");
    private JLabel portLabel = new JLabel("Server Port: ");
    private JLabel sayLabel = new JLabel("Say: ");

    public ClientUI() {
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!socket.isConnect()) {
                    txt.setText("Connect to server…\n");
                    socket.join(severIP.getText(), port.getText());
                    Runnable receive = new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                String message = socket.receive();
                                if(message == null) continue;
                                txt.append(message + "\n");
                            }
                        }
                    };
                    new Thread(receive).start();
                }
            }
        });
        say.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                socket.say(sayField.getText());
                sayField.setText("");
            }
        });
        setLayout(new FlowLayout());
        JPanel ClientSet = new JPanel();
        ClientSet.add(ipLabel);
        ClientSet.add(severIP);
        ClientSet.add(portLabel);
        ClientSet.add(port);
        ClientSet.add(connect);
        ClientSet.setBorder(new TitledBorder("客户机设置"));
        add(ClientSet);
        add(txt);
        add(sayLabel);
        add(sayField);
        add(say);
    }
}
