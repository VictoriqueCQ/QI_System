package quantour.bl;

import java.net.Socket;

/**
 * Created by dell on 2017/3/11.
 */
public class ClientNodeThread implements Runnable {
    private Socket socket;

    ClientNodeThread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {

    }
}
