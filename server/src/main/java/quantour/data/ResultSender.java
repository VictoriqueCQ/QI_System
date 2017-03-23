package quantour.data;

import net.sf.json.JSONArray;
import quantour.dataservice.DataFactory;
import quantour.dataservice.Logic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by dell on 2017/3/12.
 */
class ResultSender {
    private DataFactory dataFactory;
    private String quest;
    private DataOutputStream dataOutputStream;

    ResultSender(String quest, DataOutputStream dataOutputStream) throws ParseException {
        this.dataFactory = DataFactory_CSV_Impl.getInstance();
        this.quest = quest;
        this.dataOutputStream = dataOutputStream;
    }

    void run() throws ParseException, IOException {
        String[] questContent = quest.split("\t");
        Logic logic=null;
        DataClass result;
        if (questContent[1].equals("STOCK")) {
            logic = dataFactory.getSingleSearch();

        } else if (questContent[1].equals("MARKET")) {
            logic = dataFactory.getOverallSearch();
        } else {
            System.out.println("wrong");//此处应改为exception
        }
        result= logic.get(questContent);

        JSONArray jsonArray = JSONArray.fromObject(result);
        String resultstr = jsonArray.toString();
        System.out.println(result);
        int j = (int) (resultstr.length() / 100) + 1;
        dataOutputStream.writeInt(j);
        for (int i = 0; i < j; i++) {
            if (i == j - 1) {
                dataOutputStream.writeUTF(resultstr.substring(i * 100));
                break;
            }
            dataOutputStream.writeUTF(resultstr.substring(i * 100, (i + 1) * 100));
        }
    }
}