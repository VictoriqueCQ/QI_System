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

    ResultSender(DataFactory dataFactory, String quest) {
        this.dataFactory = dataFactory;
        this.quest = quest;
    }

    void run() throws ParseException, IOException {
        String[] questContent = quest.split("\t");
        Logic logic=null;
        DataClass result;
        switch (questContent[1]) {
            case "STOCK":
                logic = dataFactory.getSingleSearch();

                break;
            case "MARKET":
                logic = dataFactory.getOverallSearch();
                break;
            case "User":
                logic = dataFactory.getUser();
                break;
            default:
                System.out.println("wrong");//此处应改为exception

                break;
        }
        result= logic.get(questContent);

        JSONArray jsonArray = JSONArray.fromObject(result);
        String resultstr = jsonArray.toString();
        System.out.println(resultstr);
        /*int j = (int) (resultstr.length() / 100) + 1;
        dataOutputStream.writeInt(j);
        for (int i = 0; i < j; i++) {
            if (i == j - 1) {
                dataOutputStream.writeUTF(resultstr.substring(i * 100));
                break;
            }
            dataOutputStream.writeUTF(resultstr.substring(i * 100, (i + 1) * 100));
        }*/
    }
}