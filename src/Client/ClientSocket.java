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
    public void join(String ip,String port) throws IOException {
        SocketAddress addr = new InetSocketAddress(ip,Integer.parseInt(port));
        client.connect(addr,60000);
        os = new DataOutputStream(client.getOutputStream());
        is = new DataInputStream(client.getInputStream());
    }
    public void say(String sendStr) throws IOException {
        os.writeUTF(sendStr);
    }
    public String receive() throws IOException {
        return is.readUTF();
    }
}
