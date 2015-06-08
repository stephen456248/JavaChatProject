
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;




public class MessageManager implements Runnable{
    ArrayList<String> messageBuff;
    ArrayList<ClientConnection> connections;
    public MessageManager(ArrayList<String> messageBuff, ArrayList<ClientConnection> connections){
        this.messageBuff = messageBuff;
        this.connections = connections;
    }
    public void run(){
        while(true){
            //System.out.println(this.messageBuff);
            if(this.messageBuff.size() == 0){
                try{ Thread.sleep(500);} catch(InterruptedException e){}
            }else{
                while(this.messageBuff.size() > 0){
                    String currentMessage = this.messageBuff.remove(0);
                    System.out.println(currentMessage);
                    List<String> names = new LinkedList<>();
                    
                    for (int i = 0; i < connections.size(); i++) {
                        names.add(connections.get(i).clientName());
                    }
                    String nameList = String.join("\n",names);
                    for(ClientConnection connection: connections){
                        
                        connection.passMessage(nameList);
                        connection.passMessage(currentMessage);
                    }
                }
            }
        }
    }
}
