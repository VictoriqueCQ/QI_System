package quantour.data.strategy;

import quantour.data.Stock;
import quantour.data.StockSet;
import quantour.dataservice.Strategy_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cyy on 2017/3/31.
 */
public class Average_Impl implements Strategy_data{
    private List<Stock> stocks;//stocks为根据股票池等筛选信息得到的股票信息
    private List<String> stockNames;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

    public Average_Impl(List<Stock> stocks){
        this.stocks=stocks;
    }

    @Override
    public List<StockSet> getSets(String[] quest) {
        Date startDate = null;
        Date endDate = null;
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
        int shapeTime =Integer.parseInt(quest[5]) ;//形成期,比价基准，均线
        int holdTime = Integer.parseInt(quest[6]);//持有期
        int numOfStocks = Integer.parseInt(quest[7]);//仓内持股数
        ArrayList<String> stockPoolChoose = null;//板块选择
        int length = quest.length - 8;
        for(int i = 0;i<length;i++){
            stockPoolChoose.add(quest[8+i]);
        }

//        List<Stock> stockList = stocks;// 根据股票池或者自选股票啥的得到的股票


        stocks = FiltExceptST(stocks);



        List<Date> changeDate = null;// 计算若干个调仓时间
        changeDate.add(startDate);
        Date date=startDate;//取时间
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,holdTime);
        while(calendar.getTime().compareTo(endDate)<=0){
            changeDate.add(calendar.getTime());
            calendar.add(calendar.DATE,holdTime);
        }
        List<Stock> storeStocks = stocks;

        /*
         *Integer为排名，stocksets里只要开始和结束的信息

    public StockSet(Map<Integer, List<Stock>> stockSets) {
            this.stockSets = stockSets;
        }*/

        List<StockSet> result = new ArrayList<StockSet>();
        for(int i=0;i<changeDate.size();i++){
            Date changeStocks = changeDate.get(i);
            stocks = storeStocks;
            Map<Integer,List<Stock>> stockList = null;
            List<String> hasBeChosen;
            for(int j=0;j<numOfStocks;j++){
                stockList.put(j,getBestChoice(changeDate.get(i),changeDate.get(i+1),stocks));
                String name = stockList.get(j).get(0).getName();
                stocks = stocks.stream().
                        filter(stock -> !(stock.getName()==name)).collect(Collectors.toList());

            }

        }


        return null;
    }
    /**
     * 计算均线值
     */
    private double calAverage(List<Stock> stockList) {
        int size = stockList.size();
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum = sum + stockList.get(i).getAdjClose();
        }
        return sum/size;
    }

    /**
     * 计算偏离度
     */
    private double calDeviate(double average,double adjClose){
        return (average-adjClose)/average;

    }
    /**
     * 删除st股
     */
    private List<Stock> FiltExceptST(List<Stock> stockList){
        List<Stock> result = stockList.stream().
                filter(stock -> !(stock.getName().startsWith("ST"))).collect(Collectors.toList());
        return result;
    }

    /**
     * 得到一个换仓日的的最佳股票
     * @return
     */
    private List<Stock> getBestChoice(Date startDate,Date endDate,List<Stock> stockListToChoose) {
        double temp ;
        return null;
    }

    private List<Stock> getStockListByName(String stockName,List<Stock> stockList) {
        List<Stock> singleStockList = stockList.stream().
                filter(stock -> stock.getName().equals(stockName)).
                collect(Collectors.toList());//得到该name的股票信息，完全匹配

        return singleStockList;
    }


}
