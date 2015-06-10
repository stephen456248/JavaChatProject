
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class SMell06_Server {


            
    @SuppressWarnings("resource")
	public static void main(String [] args){
        //check if correct args passed
        //args array of 2 things first hostname 2nd port
        //create and run server
        //if not correct input error message
        // print binding port
        // bind the port()
        // print waiting for client
        // print client accepted
        // Make data input stream from socket
        // loop and print out all messages that come in until "/kill"
        //print currentLine 
        //if something, done = true
        //Set to automatically start new client connection
        //Close socket and inputStream
        System.out.println("Initiated server/port setup *order 66*");
        ServerSocket server = null;
        ArrayList<String> messageBuff = new ArrayList<String>();
        ArrayList<ClientConnection> connections = new ArrayList<ClientConnection>();
        MessageManager messageManager = new MessageManager(messageBuff, connections);
        Thread messageManagerThread = new Thread(messageManager);
        messageManagerThread.start();
        int clientID = 0;
        while(true){
            try{
                if(server == null)
                    server = new ServerSocket(5555);
                System.out.println("Server ready");
                System.out.println("Waiting for client......");
                clientID++;
                ClientConnection newConnection = new ClientConnection(server.accept(), messageBuff, clientID, connections);
                
                connections.add(newConnection);
                new Thread(newConnection).start();
            }catch(IOException e){
                System.out.println(e);
            }
        }
        
    }
}