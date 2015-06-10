

import java.io.DataInputStream;
import java.io.IOException;


public class MessageManager implements Runnable{
    DataInputStream inputStream;
    javax.swing.JTextArea output;
    javax.swing.JTextArea listOutput;
    
    public MessageManager(DataInputStream inputStream){
        this.inputStream = inputStream;
    }
    
    public void setOutput(javax.swing.JTextArea ta){
        this.output = ta;
    }
    
    public void setListOutput(javax.swing.JTextArea ta){
        this.listOutput = ta;
    }
    
    public void run(){
        String message;
        while(true){
            try{
                String nameList = this.inputStream.readUTF();
                System.out.println("nameList = " + nameList);
                this.listOutput.setText(nameList);
                message = this.inputStream.readUTF();
                this.output.setText(this.output.getText() + "\n" + message);
                //System.out.println(message);
            }catch(IOException e){}
        }
    }
}