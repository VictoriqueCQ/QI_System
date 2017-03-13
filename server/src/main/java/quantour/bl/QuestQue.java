package quantour.bl;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dell on 2017/3/12.
 */
class QuestQue {
    private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private static int id = 0;

    static int write(String quest) throws InterruptedException {
        //System.out.println("receive "+quest);
        queue.put(id + "\t" + quest);
        return id++;//目前只能叠加，应该在一段时间后进行清零
    }

    static String get() {
        String s=queue.poll();
        /*if(s!=null) {
            System.out.println("get "+s);
        }*/
        return s;
    }
}
