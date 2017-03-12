package quantour.data;

import net.sf.json.JSONArray;
import quantour.dataservice.DataFactory;
import quantour.dataservice.Overall_Search_data;
import quantour.dataservice.Single_Search_data;
import quantour.po.MarketPO;
import quantour.po.StockPO;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dell on 2017/3/12.
 */
class ResultSender{
    private DataFactory dataFactory;
    private String quest;
    private PrintWriter printWriter;

    ResultSender(String quest, PrintWriter printWriter) throws ParseException {
        dataFactory=DataFactory_CSV_Impl.getInstance();
        this.quest=quest;
        this.printWriter=printWriter;
    }

    void run() throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("mm/dd/yy");
        String[] questContent=quest.split("\t");
        if (questContent[1].equals("STOCK")){
            Single_Search_data single_search_data=dataFactory.getSingleSearch();
            StockPO stockPO=single_search_data.getStockList(questContent);

            JSONArray jsonArray=JSONArray.fromObject(stockPO);
            printWriter.write(jsonArray.toString());
            printWriter.flush();
        }
        else if (questContent[1].equals("MARKET")){
            Date date=null;
            date=sdf.parse(questContent[2]);
            Overall_Search_data overall_search_data=dataFactory.getOverallSearch();
            MarketPO marketPO=overall_search_data.getMarketInfo(date);

            JSONArray jsonArray=JSONArray.fromObject(marketPO);
            printWriter.write(jsonArray.toString());
            printWriter.flush();
        }
        else{
            System.out.println("wrong");//此处应改为exception
        }
    }

}
