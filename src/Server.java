import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import com.google.common.eventbus.*;
import uk.co.caprica.vlcjplayer.synchronizationEvents.*;

public class Server {

    int port;
    ArrayList<Socket> socketList;
    ArrayList<ObjectOutputStream> outList;
    EventBus eventBus;
    boolean isPlaying=false;
    int currentPostion;
    ServerSocket server;
    public Server(int port){
        socketList=new ArrayList<Socket>();
        socketList=new ArrayList<Socket>();
        outList =new ArrayList<ObjectOutputStream>();
        eventBus =new EventBus();
        try {
             server= new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new SocketServer().start();
        new InternetTimeGetter(8898).start();
    }
    class TimeSocket extends Thread {


    }
    class SocketServer extends Thread{
        @Override
        public void run(){
            try {

                while (true) {
                    Socket socket = server.accept();
                    socket.setTcpNoDelay(true);
                    socketList.add(socket);
                    outList.add(new ObjectOutputStream(socket.getOutputStream()));
                    new SocketHandler(socket).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    class SocketHandler extends Thread{
        Socket socket;
        public SocketHandler(Socket s){
            socket=s;
        }
        @Override
        public void run(){

            try {
                ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
                SynchronizedEvent msg;
                while (true){
                 try {
                     msg=(SynchronizedEvent)in.readObject();
                     msg.setTime(System.currentTimeMillis());
                     eventBus.post(msg);
                     broadcast(msg);
                 }catch (EOFException e){

                 }
                    catch (SocketException e){
                        outList.remove(socketList.indexOf(socket));
                        socketList.remove(socket);
                        return;
                    }
                }


            } catch (SocketException e){
                outList.remove(socketList.indexOf(socket));
                socketList.remove(socket);
                return;
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

        private void broadcast(Object msg){
            ArrayList<ObjectOutputStream> deadStreams=new ArrayList<ObjectOutputStream>();
            for(ObjectOutputStream s:outList)
                try {
                    s.writeObject(msg);
                    s.flush();
                }catch (SocketException e){
                    deadStreams.add(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            for(ObjectOutputStream s:deadStreams){
                socketList.remove(outList.indexOf(s));
                outList.remove(s);
            }
        }

    }

    public static void main(String []args){
        new Server(Integer.valueOf(args[0]));
    }
}
