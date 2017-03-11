package quantour.bl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

/**
 * Created by dell on 2017/3/11.
 */
class SwitchNode {
    private Stack<Socket> dataServerStack;
    private ClientServer clientServer;
    private DataServer dataServer;

    SwitchNode(){
        dataServerStack=new Stack<>();
    }

    void run(){
        clientServer=new ClientServer(9000);
        dataServer=new DataServer(9001);
        clientServer.start();
        dataServer.start();
    }

    public class ClientServer extends Thread{
        private int port;

        ClientServer(int port){
            this.port=port;
        }

        public void run(){
            try{
                ServerSocket cServer=new ServerSocket(port);
                while(true){
                    Socket socket=cServer.accept();
                    System.out.println("connected");
                    while(!dataServerStack.empty()){}
                    new ClientNodeThread(socket,dataServerStack).run();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public class DataServer extends Thread{
        private int port;
        DataServer(int port){
            this.port=port;
        }

        public void run(){
            
        }
    }

}
