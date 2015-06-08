

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class ClientConnection implements Runnable{
    Socket socket;
    ArrayList<String> messageBuff;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    int id;
    String name = "";
    ArrayList<ClientConnection> conns;
    
    //Make client socket
    //Set up input stream
    //read in the value of the input stream
    //set up output stream
    //While loop
        //read in line
        //test for stopping conditions
        //write the line
        //flush buffer
    public ClientConnection(Socket socket){
        this.socket = socket;
    }
    
    public ClientConnection(Socket socket, ArrayList<String> messageBuff, int id, ArrayList<ClientConnection> c){
        this.socket = socket;
        this.messageBuff = messageBuff;
        this.id = id;
        this.conns = c;
        try{
            this.outputStream = new DataOutputStream(this.socket.getOutputStream());
            this.inputStream = new DataInputStream(this.socket.getInputStream());
        }catch(IOException e){}
        
    }
    
    public int client_id(){
        return this.id;
    }
    
    public String clientName(){
        return this.name;
    }
    
    public void passMessage(String message){
        try{
            this.outputStream.writeUTF(message);
            this.outputStream.flush();
        }catch (IOException e){}
    }
    
    public void run(){
        try{
            System.out.println("A client has joined the group");
            
            boolean done = false;
            String currentLine;
            while(!done){
                currentLine = inputStream.readUTF();
                this.name = currentLine.split(" ")[0];
                //System.out.println(currentLine);
                this.messageBuff.add(currentLine);
                if(currentLine.substring(this.name.length()).trim().equals("/kill")){ done = !done; }
            }
            socket.close();
            inputStream.close();
            conns.remove(this);
        }catch(IOException e){
            System.out.println("Error in ClientConnection");
        }
    }
}
