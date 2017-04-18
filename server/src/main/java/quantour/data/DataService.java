package quantour.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * Created by dell on 2017/3/12.
 */
public class DataService {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public static void main(String[] args) throws IOException, ParseException {
        DataService dataService = new DataService();
        dataService.start();
    }

    private void start() throws IOException, ParseException {
        DataFactory_CSV_Impl.getInstance();

        setUpNet();

        run();
    }

    private void setUpNet() {
        try {
            socket = new Socket("127.0.0.1", 9001);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void run() {
        try {
            while (socket.isConnected()) {
                System.out.println("serverok");
                String quest = "";
                quest = dataInputStream.readUTF();
                System.out.println(quest);
                (new ResultSender(quest, dataOutputStream)).run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
