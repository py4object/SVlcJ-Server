import java.io.*;
import java.net.Socket;

/**
 * Created by kozo on 7/3/15.
 */

public class TClinet {
    public static void main(String []args){
        try {
            final Socket s=new Socket("127.0.0.1",8899);

            new Thread(){
                @Override
                public void run(){

                    try {
                        ObjectInputStream in=new ObjectInputStream(s.getInputStream());
                        Object msg;
                        while (true) {
                            try {

                                msg = in.readObject();
                                System.out.println(msg);
                            } catch (EOFException e) {

                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }.start();
            ObjectOutputStream outputStream=new ObjectOutputStream(s.getOutputStream());
            outputStream.writeObject(new msg());
            outputStream.writeObject(new msg());
            outputStream.writeObject(new msg());
            outputStream.flush();
            while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
class msg implements Serializable{
    public String toString(){
        return "This was a msg";
    }

}
