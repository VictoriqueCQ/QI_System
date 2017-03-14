package quantour.data;

import net.sf.json.JSONArray;
import quantour.dataservice.DataFactory;
import quantour.dataservice.Overall_Search_data;
import quantour.dataservice.Single_Search_data;
import quantour.po.MarketPO;
import quantour.po.StockPO;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dell on 2017/3/12.
 */
class ResultSender{
    private DataFactory dataFactory;
    private String quest;
    private DataOutputStream dataOutputStream;
    
    ResultSender(String quest, DataOutputStream dataOutputStream) throws ParseException {
        dataFactory=DataFactory_CSV_Impl.getInstance();
        this.quest=quest;
        this.dataOutputStream=dataOutputStream;
    }


    void run() throws ParseException, IOException {
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy");
        String[] questContent=quest.split("\t");
        if (questContent[1].equals("STOCK")){
            Single_Search_data single_search_data=dataFactory.getSingleSearch();
            StockPO stockPO=single_search_data.getStockList(questContent);

            JSONArray jsonArray=JSONArray.fromObject(stockPO);
            dataOutputStream.writeUTF(jsonArray.toString());
        }
        else if (questContent[1].equals("MARKET")){
            Date date=null;
            date=sdf.parse(questContent[2]);
            Overall_Search_data overall_search_data=dataFactory.getOverallSearch();
            MarketPO marketPO=overall_search_data.getMarketInfo(date);

            JSONArray jsonArray=JSONArray.fromObject(marketPO);
            dataOutputStream.writeUTF(jsonArray.toString());
        }
        else{
            System.out.println("wrong");//此处应改为exception
        }
    }

}
