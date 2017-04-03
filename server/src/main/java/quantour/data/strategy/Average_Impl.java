package quantour.data.strategy;

import quantour.data.Stock;
import quantour.data.StockSet;
import quantour.dataservice.Strategy_data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/3/31.
 */
public class Average_Impl implements Strategy_data{
    private List<Stock> stocks;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

    public Average_Impl(List<Stock> stocks){
        this.stocks=stocks;
    }

    @Override
    public List<StockSet> getSets(String[] quest) {
        String startDate = quest[3];
        String endDate = quest[4];
        int shapeTime =Integer.parseInt(quest[5]) ;//形成期,比价基准
        int holdTime = Integer.parseInt(quest[6]);//持有期
        int numOfStocks = Integer.parseInt(quest[7]);//仓内持股数
        ArrayList<String> stockPoolChoose = null;
        int length = quest.length - 8;//选择板块的个数
        for(int i = 0;i<length;i++){
            stockPoolChoose.add(quest[8+i]);
        }



        return null;
    }
}
