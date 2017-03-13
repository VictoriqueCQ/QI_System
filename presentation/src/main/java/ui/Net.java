package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by xjwhhh on 2017/3/12.
 */
public class Net {

    private Socket sock;
    private BufferedReader br;
    private PrintWriter pw;

    public void setupNet() {
        try {
            sock = new Socket("127.0.0.1", 9000);
            InputStreamReader is = new InputStreamReader(sock.getInputStream());
            br = new BufferedReader(is);
            pw = new PrintWriter(sock.getOutputStream());
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
        String message;
        String output = "";
        try {
                while ((message = br.readLine()) != null) {
                    System.out.println("read: " + message);
                    output += (message + "\n");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public void actionPerformed(String input) {
        try {
            pw.write(input);
            pw.flush();
        } catch (Exception ep) {
            ep.printStackTrace();
        }

    }
}
