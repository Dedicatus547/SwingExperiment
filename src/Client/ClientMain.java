package Client;

import javax.swing.*;

/**
 * Created by Dedicatus on 2016/10/14.
 */
public class ClientMain {
    static ClientUI client = new ClientUI();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                client.setTitle("客户端");
                client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                client.setSize(681,444);
                client.setVisible(true);
            }
        });
    }
}
