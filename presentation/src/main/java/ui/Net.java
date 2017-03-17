package ui;

import java.io.*;
import java.net.Socket;

/**
 * Created by xjwhhh on 2017/3/12.
 */
public class Net {

    private Socket sock;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public void setUpNet() {
        try {
            sock = new Socket("127.0.0.1", 9000);
            inputStream = new DataInputStream(sock.getInputStream());
            outputStream = new DataOutputStream(sock.getOutputStream());
            System.out.println("Network established.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeSock() {
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSock() {
        return this.sock;
    }

    /**
     * 获取服务器返回的数据
     * @return
     */
    public String run() {
        StringBuilder sb = new StringBuilder();
        try {
            int length = inputStream.readInt();
            for (int i = 0; i < length; i++) {
                sb.append(inputStream.readUTF());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 传达指令给服务器
     * @param input
     */
    public void actionPerformed(String input) {
        try {
            outputStream.writeUTF(input);
        } catch (Exception ep) {
            ep.printStackTrace();
        }
    }
}
