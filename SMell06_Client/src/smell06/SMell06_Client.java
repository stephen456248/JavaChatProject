
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;



public class SMell06_Client
{  
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
    public static void main(String args []){
        String name;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        name = scanner.nextLine();
        name = name.split(" ")[0];
        if(args.length !=2){
            System.out.println("Invalid number of arguments <server><port number>");
        }else{
            System.out.println("Connected to " + args[0] + " via port " + args[1]);
            try
            {
                Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
                System.out.println("Attached to server.....");
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                Thread receiveMessageThread = new Thread(new MessageManager(inputStream));
                receiveMessageThread.start();
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
                String line = "";
                boolean done = false;
                while(!done){
                    line = consoleInput.readLine();
                    outputStream.writeUTF(name + " " + line);
                    outputStream.flush();
                    if(line.equals("/kill")) { done = !done; }
                }
                receiveMessageThread.stop();
            }catch(IOException e){
                System.out.println("Error creating socket...." + e);
            }
        }
    }
}