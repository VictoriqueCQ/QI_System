package quantour.bl;

import java.io.*;
import java.net.Socket;

/**
 * Created by dell on 2017/3/11.
 */
public class DataNodeThread implements Runnable{
    private Socket socket;
    private BufferedReader br;
    private PrintWriter printWriter;

    DataNodeThread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader=new InputStreamReader(socket.getInputStream());
            br=new BufferedReader(inputStreamReader);
            printWriter=new PrintWriter(socket.getOutputStream());

            String quest=null;
            while (socket.isConnected()){
                if ((quest=QuestQue.get())==null){
                    continue;
                }
                String[] readKey=quest.split("\t");
                int key=Integer.parseInt(readKey[0]);
                printWriter.write(quest);
                printWriter.flush();
                Thread.sleep(100);

                String result=null;
                while((result=br.readLine())==null){
                    Thread.sleep(100);
                }
                ResultMap.write(key,result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
