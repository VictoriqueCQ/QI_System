package ui;

import java.io.*;
import java.net.Socket;

/**
 * Created by xjwhhh on 2017/3/12.
 */
public class Net {

    private Socket sock;
    private BufferedReader br;
    private PrintWriter pw;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public void setupNet() {
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

    public BufferedReader getBufferedReader() {
        return this.br;
    }

    public PrintWriter getPrintWriter() {
        return this.pw;
    }


    public String run() {
//        String message;
//        String output = "";
        StringBuilder sb=new StringBuilder();
        try {
//            message = inputStream.readUTF();
//            output += (message + "\n");
            int length=inputStream.readInt();
            for(int i=0;i<length;i++){
                sb.append(inputStream.readUTF());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return output;

        return sb.toString();

    }

    public void actionPerformed(String input) {
        try {
            outputStream.writeUTF(input);
        } catch (Exception ep) {
            ep.printStackTrace();
        }
    }

}
