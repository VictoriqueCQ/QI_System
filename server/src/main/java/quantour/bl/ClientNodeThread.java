package quantour.bl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by dell on 2017/3/11.
 */
public class ClientNodeThread implements Runnable {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    ClientNodeThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
            dataInputStream=new DataInputStream(socket.getInputStream());

            String quest = dataInputStream.readUTF();

            while (quest != null) {
                int key = QuestQue.write(quest);
                Thread.sleep(100);
                String result;
                while ((result = ResultMap.get(key)) == null) {
                    Thread.sleep(100);
                }

                int j=(int)(result.length()/100)+1;
                dataOutputStream.writeInt(j);
                for(int i=0;i<j;i++) {
                    if (i==j-1){
                        dataOutputStream.writeUTF(result.substring(i*100));
                        break;
                    }
                    dataOutputStream.writeUTF(result.substring(i * 100, (i + 1) * 100));
                }
                System.out.println(result);
                ResultMap.delete(key);
                quest=dataInputStream.readUTF();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
