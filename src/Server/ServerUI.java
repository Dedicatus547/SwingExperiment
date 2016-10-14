package Server;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by Dedicatus on 2016/10/14.
 */
public class ServerUI extends JFrame {
    private JButton start = new JButton("Start");
    private JButton say = new JButton("Say");
    private JTextField portField = new JTextField(40);
    private JTextField sayField = new JTextField(40);
    private JTextArea txt = new JTextArea(17,55);
    private JLabel portLabel = new JLabel("Port: ");
    private JLabel sayLabel = new JLabel("Say: ");
    private ServerSocket server = null;
    private LinkedList<DataOutputStream> osSet = new LinkedList<>();
    class clientThread extends Thread {
        Socket client;
        DataInputStream is;
        DataOutputStream os;
        clientThread(Socket client,
                     DataInputStream is,
                     DataOutputStream os) {
            this.client = client;
            this.is = is;
            this.os = os;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    String message = is.readUTF();
                    txt.append(message + "\n");
                    for (DataOutputStream out : osSet)
                        out.writeUTF(message);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public ServerUI() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(server == null) {
                    int port = Integer.parseInt(portField.getText());
                    try {
                        server = new ServerSocket(port);
                        txt.setText("Server starting…\n");
                        Runnable Lisen = new Runnable() {
                            @Override
                            public void run() {
                                while (true) {
                                    Socket client = null;
                                    DataInputStream is = null;
                                    DataOutputStream os = null;
                                    try {
                                        client = server.accept();
                                        txt.append("Client connected…\n");
                                        is = new DataInputStream(client.getInputStream());
                                        os = new DataOutputStream(client.getOutputStream());
                                        osSet.add(os);
                                    }
                                    catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    new clientThread(client,is,os).start();
                                }
                            }
                        };
                        new Thread(Lisen).start();
                    }
                    catch (IOException ex) {
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
                for(DataOutputStream out : osSet) {
                    try {
                        out.writeUTF(message);
                    }
                    catch (IOException ex) {
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
