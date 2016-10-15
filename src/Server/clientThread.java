package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Dedicatus on 2016/10/15.
 */
public class clientThread implements Runnable {
    private Socket client;
    private ServerUI UI;

    public clientThread(Socket client, ServerUI UI) {
        this.client = client;
        this.UI = UI;
    }

    @Override
    public void run() {
        while (true) {
            DataInputStream is = null;
            String message = null;
            try {
                is = new DataInputStream(client.getInputStream());
                message = is.readUTF();
                UI.txt.append(message + "\n");
                DataOutputStream os = null;
                for (Socket each : UI.SocketSet) {
                    try {
                        os = new DataOutputStream(each.getOutputStream());
                        os.writeUTF(message);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
