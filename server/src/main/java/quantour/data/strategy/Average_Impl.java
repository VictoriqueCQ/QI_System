package quantour.data.strategy;

import quantour.data.Stock;
import quantour.data.StockSet;
import quantour.dataservice.Strategy_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Date startDate;
        Date endDate;
        try {
            startDate = sdf.parse(quest[3]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endDate = sdf.parse(quest[4]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int shapeTime =Integer.parseInt(quest[5]) ;//形成期,比价基准
        int holdTime = Integer.parseInt(quest[6]);//持有期
        int numOfStocks = Integer.parseInt(quest[7]);//仓内持股数
        ArrayList<String> stockPoolChoose = null;//板块选择
        int length = quest.length - 8;
        for(int i = 0;i<length;i++){
            stockPoolChoose.add(quest[8+i]);
        }


        Date date=new Date();//取时间
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,holdTime);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果



        return null;
    }
    /**
     * 计算均线值
     */
    public double calAverage(List<Stock> stockList) {
        int size = stockList.size();
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum = sum + stockList.get(i).getAdjClose();
        }
        return sum/size;
    }

}
