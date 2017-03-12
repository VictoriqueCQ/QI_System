package quantour.bl;

import java.io.*;
import java.net.Socket;

/**
 * Created by dell on 2017/3/11.
 */
public class ClientNodeThread implements Runnable {
    private Socket socket;
    private BufferedReader br;
    private PrintWriter printWriter;

    ClientNodeThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(socket.getOutputStream());

            String quest = br.readLine();
            while (quest != null) {
                int key = QuestQue.write(quest);
                Thread.sleep(100);
                String result = "";
                while ((result = ResultMap.get(key)) == null) {
                    Thread.sleep(100);
                }
                printWriter.write(result);
                printWriter.flush();
                ResultMap.delete(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
