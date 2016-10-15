package Client;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Dedicatus on 2016/10/14.
 */
public class ClientSocket {
    private Socket client = new Socket();
    private DataInputStream is = null;
    private DataOutputStream os = null;

    public boolean isConnect() {
        return client.isConnected();
    }

    public void join(String ip, String port) {
        SocketAddress addr = new InetSocketAddress(ip, Integer.parseInt(port));
        try {
            client.connect(addr, 60000);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void say(String sendStr) {
        try {
            os = new DataOutputStream(client.getOutputStream());
            os.writeUTF(sendStr);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String receive() {
        String ret = null;
        try {
            is = new DataInputStream(client.getInputStream());
            ret = is.readUTF();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
