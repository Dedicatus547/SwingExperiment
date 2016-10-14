package Server;

import javax.swing.*;

/**
 * Created by Dedicatus on 2016/10/14.
 */
public class ServerMain {
    static ServerUI server = new ServerUI();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                server.setTitle("服务器");
                server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                server.setSize(681,444);
                server.setVisible(true);
            }
        });
    }
}
