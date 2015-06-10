

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class SMell06_Client implements Runnable
{  
    DataInputStream inputStream;
    DataOutputStream outputStream;
    Thread receiveMessageThread;
    MessageManager messageManager;
    Socket socket;
       //validate user input
       //Error msg if input notgood
       //create chat cleint and convert to string args[]
       //Create socket passing in host and address
       //Print attached to server
       //Initialize output stream
       //initialize console input
       //make while loop to read the chats
            //read line
            //write line
            //terminate with "/kill"
    public SMell06_Client(String args [], DataInputStream guiInput){
        if(args.length !=2){
            System.out.println("Invalid number of arguments <server><port number>");
        }else{
            System.out.println("Connected to " + args[0] + " via port " + args[1]);
            try
            {
                this.socket = new Socket(args[0], Integer.parseInt(args[1]));
                System.out.println("Attached to server.....");
                DataInputStream inputStream = new DataInputStream(this.socket.getInputStream());
                this.messageManager = new MessageManager(inputStream);
                this.receiveMessageThread = new Thread(this.messageManager);
                this.receiveMessageThread.start();
                this.outputStream = new DataOutputStream(this.socket.getOutputStream());
                this.inputStream = guiInput;
                
            }catch(IOException e){
                System.out.println("Error creating socket...." + e);
            }
        }
    }
    
    public MessageManager messageManager(){
        return this.messageManager;
    }
    
    @SuppressWarnings("deprecation")
	public void run(){
        String line = "";
        boolean done = false;
        try{
            while(!done){
                line = this.inputStream.readUTF();
                this.outputStream.writeUTF(line);
                this.outputStream.flush();
                if(line.equals("/kill")) { done = !done; }
            }
            this.socket.close();
        }catch(IOException e){}
        this.receiveMessageThread.stop();
    }
}