package quantour.bl;

import java.io.*;
import java.net.Socket;

/**
 * Created by dell on 2017/3/11.
 */
public class DataNodeThread implements Runnable {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    DataNodeThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());

            String quest = "";
            while (socket.isConnected()) {
                if ((quest = QuestQue.get()) == null) {
                    continue;
                }
                String[] readKey = quest.split("\t");
                int key = Integer.parseInt(readKey[0]);
                System.out.println(key);
                dataOutputStream.writeUTF(quest);
                Thread.sleep(100);

                String result = "";
                while ((result = dataInputStream.readUTF()) == null) {
                    Thread.sleep(100);
                }
                //System.out.println(result);
                ResultMap.write(key, result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
