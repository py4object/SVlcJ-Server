

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by kozo on 7/5/15.
 */
public class TTClient {
    public static void main(String[]args){
        try {
                DatagramSocket socket = new DatagramSocket();
            while (true) {
                byte[] buff = new byte[250];
                DatagramPacket packet2 = new DatagramPacket(new byte[250], 250);
                DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getByName("127.0.0.1"), 8898);
                socket.send(packet);
                socket.receive(packet2);
                String p = Arrays.toString(packet2.getData());
                Long p2 = ByteBuffer.wrap(packet2.getData()).getLong();
                System.out.println((p2 ) );
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
