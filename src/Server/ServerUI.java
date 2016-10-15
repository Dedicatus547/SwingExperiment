package Server;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by Dedicatus on 2016/10/14.
 */
public class ServerUI extends JFrame {
    JButton start = new JButton("Start");
    JButton say = new JButton("Say");
    JTextField portField = new JTextField(40);
    JTextField sayField = new JTextField(40);
    JTextArea txt = new JTextArea(17, 55);
    JLabel portLabel = new JLabel("Port: ");
    JLabel sayLabel = new JLabel("Say: ");
    ServerSocket server = null;
    LinkedList<Socket> SocketSet = new LinkedList<>();

    public ServerUI() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server == null) {
                    int port = Integer.parseInt(portField.getText());
                    try {
                        server = new ServerSocket(port);
                        txt.setText("Server starting…\n");
                        Runnable Lisen = new Runnable() {
                            @Override
                            public void run() {
                                while (true) {
                                    Socket client = null;
                                    try {
                                        client = server.accept();
                                        txt.append("Client connected…\n");
                                        SocketSet.add(client);
                                        new Thread(new clientThread(client, ServerUI.this)).start();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        };
                        new Thread(Lisen).start();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        say.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = sayField.getText();
                sayField.setText("");
                txt.append(message + "\n");
                DataOutputStream os = null;
                for (Socket each : SocketSet) {
                    try {
                        os = new DataOutputStream(each.getOutputStream());
                        os.writeUTF(message);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        setLayout(new FlowLayout());
        JPanel ServerSet = new JPanel();
        ServerSet.add(portLabel);
        ServerSet.add(portField);
        ServerSet.add(start);
        ServerSet.setBorder(new TitledBorder("服务器设置："));
        add(ServerSet);
        add(txt);
        add(sayLabel);
        add(sayField);
        add(say);
    }
}
