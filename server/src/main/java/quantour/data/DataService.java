package quantour.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * Created by dell on 2017/3/12.
 */
public class DataService {
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public static void main(String[] args) {
        DataService dataService = new DataService();
        dataService.start();
    }

    private void start() {
        setUpNet();

        run();
    }

    private void setUpNet() {
        try {
            socket = new Socket("127.0.0.1", 9001);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void run() {
        while (socket.isConnected()) {
            String quest = null;
            try {
                if ((quest = bufferedReader.readLine()) != null) {
                    (new ResultSender(quest, printWriter)).run();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
