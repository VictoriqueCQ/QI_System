package quantour.bl;

import java.net.Socket;
import java.util.Stack;

/**
 * Created by dell on 2017/3/11.
 */
public class ClientNodeThread implements Runnable {
    private Socket socket;
    private Stack<Socket> dataServerStack;

    ClientNodeThread(Socket socket, Stack<Socket> dataServerStack){
        this.socket=socket;
        this.dataServerStack=dataServerStack;
    }

    @Override
    public void run() {

    }
}
