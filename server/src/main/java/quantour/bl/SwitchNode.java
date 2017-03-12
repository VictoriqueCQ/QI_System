package quantour.bl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dell on 2017/3/11.
 */
class SwitchNode {
    //private Stack<Socket> dataServerStack;
    private ClientServer clientServer;
    private DataServer dataServer;

    void run() {
        clientServer = new ClientServer(9000);
        dataServer = new DataServer(9001);
        clientServer.start();
        dataServer.start();
    }

    public class ClientServer extends Thread {
        private int port;

        ClientServer(int port) {
            this.port = port;
        }

        public void run() {
            try {
                ServerSocket cServer = new ServerSocket(port);
                while (true) {
                    Socket socket = cServer.accept();
                    (new ClientNodeThread(socket)).run();
                    System.out.println("client connected");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public class DataServer extends Thread {
        private int port;

        DataServer(int port) {
            this.port = port;
        }

        public void run() {
            try {
                ServerSocket cServer = new ServerSocket(port);//注册数据服务器
                while (true) {
                    Socket socket = cServer.accept();
                    (new DataNodeThread(socket)).run();
                    System.out.println("data connected");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
