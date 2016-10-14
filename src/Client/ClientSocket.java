package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Dedicatus on 2016/10/14.
 */
public class ClientSocket extends Socket {
    private InputStream is = null;
    private OutputStream os = null;
    public void say(String sendStr) throws IOException {
        try {
            os = getOutputStream();
            os.write(sendStr.getBytes());
        }
        finally {
            os.close();
        }
    }
    public String receive() throws IOException {
        String ret = new String();
        try {
            is = getInputStream();
            byte[] b = new byte[1024];
            int n = is.read(b,0,b.length);
            while(n != -1) {
                ret += b.toString();
                n = is.read(b,0,b.length);
            }
        }
        finally {
            is.close();
        }
        return ret;
    }
}
