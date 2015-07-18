//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class InternetTimeGetter extends Thread {
    DatagramSocket socket = null;

    public InternetTimeGetter(int port) {
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException var3) {
            var3.printStackTrace();
        }

    }

    public void run() {
        while(true) {
            try {
                byte[] e = new byte[250];
                DatagramPacket packet = new DatagramPacket(e, e.length);
                this.socket.receive(packet);
                (new InternetTimeGetter.SO(packet, this.socket)).start();
            } catch (SocketException var3) {
                var3.printStackTrace();
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }
    }

    class SO extends Thread {
        InetAddress address;
        DatagramSocket socket;
        int port;

        public SO(DatagramPacket packet, DatagramSocket socket) {
            this.address = packet.getAddress();
            this.port = packet.getPort();
            this.socket = socket;
        }

        public void run() {
            byte[] buf = ByteBuffer.allocate(64).putLong(System.currentTimeMillis()).array();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, this.address, this.port);

            try {
                this.socket.send(packet);
            } catch (IOException var4) {
                var4.printStackTrace();
            }

        }

        Long getTime() {
            return new Long((long)((int)System.currentTimeMillis()));
        }
    }
}
