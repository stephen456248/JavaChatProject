
import java.io.DataInputStream;
import java.io.IOException;


public class MessageManager implements Runnable{
    DataInputStream inputStream;
    
    public MessageManager(DataInputStream inputStream){
        this.inputStream = inputStream;
    }
    
    public void run(){
        String message;
        while(true){
            try{
                message = this.inputStream.readUTF();
                System.out.println(message);
            }catch(IOException e){}
        }
    }
}