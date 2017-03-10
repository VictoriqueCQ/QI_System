package quantour.data;

import quantour.dataservice.Single_Search_data;
import quantour.po.StockPO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dell on 2017/3/4.
 */
public class Single_Search_data_Impl implements Single_Search_data {
    private List<Stock> stockList;

    Single_Search_data_Impl(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @Override
    public StockPO getStockListByID(int stockID, Date startTime, Date endTime) {
        List<Stock> singleStockList = stockList.stream().filter(stock -> stock.getCode() == stockID).
                collect(Collectors.toList());//得到ID的该股票信息
        return getStockListByDate(singleStockList, startTime, endTime);
    }

    @Override
    public StockPO getStockListByName(String stockName, Date startTime, Date endTime) {
        List<Stock> singleStockList = stockList.stream().filter(stock -> stock.getName().equals(stockName)).
                collect(Collectors.toList());//得到该name的股票信息，完全匹配

        return getStockListByDate(singleStockList, startTime, endTime);
    }

    /*得到某日期间的股票并计算相关数值，生成StockPO*/
    private StockPO getStockListByDate(List<Stock> singleStockList, Date startTime, Date endTime) {
        List<Stock> resultList=singleStockList.stream().
                filter(stock -> stock.getDate().compareTo(endTime)<=0).
                filter(stock -> stock.getDate().compareTo(startTime)>=0).
                sorted(Comparator.comparing(Stock::getSerial)).collect(Collectors.toList());//得到某日期间的股票信息
        int startSerial=resultList.get(0).getSerial();
        int endSerial=resultList.get(resultList.size()-1).getSerial();

        if(resultList.isEmpty()){
            return null;
        }
        else {
            String name=resultList.get(0).getName();
            int code=resultList.get(0).getCode();
            double[] open= resultList.parallelStream().mapToDouble(Stock::getOpen).toArray();
            double[] high=resultList.parallelStream().mapToDouble(Stock::getHigh).toArray();
            double[] low=resultList.parallelStream().mapToDouble(Stock::getLow).toArray();
            double[] close=resultList.parallelStream().mapToDouble(Stock::getClose).toArray();
            int[] volume=resultList.parallelStream().mapToInt(Stock::getVolume).toArray();
            double[] adjClose=resultList.parallelStream().mapToDouble(Stock::getAdjClose).toArray();

            double[] average5=getAverageByInterval(singleStockList,5,startSerial,endSerial);
            double[] average10=getAverageByInterval(singleStockList,10,startSerial,endSerial);
            double[] average20=getAverageByInterval(singleStockList,20,startSerial,endSerial);
            double[] average30=getAverageByInterval(singleStockList,30,startSerial,endSerial);
            double[] average60=getAverageByInterval(singleStockList,60,startSerial,endSerial);

            ArrayList<Double> profit=new ArrayList<>();
            for(int i=1;i<resultList.size();i++){
                profit.add(Math.log(close[i]/close[i-1]));
            }
            double average=profit.stream().reduce(0.0,(x,y)->x+y)/(resultList.size()-1);
            double sum=0.0;
            for(double x:profit){
                sum+=x*x;
            }
            double variance=sum/(resultList.size()-1)-average*average;

            return new StockPO(name,code,startTime,endTime,open,high,low,close,volume,adjClose, average5,average10,
                    average20,average30,average60,variance);
        }

    }

    private double[] getAverageByInterval(List<Stock> singleStockList,int interval,int startSerial,int endSerial){
        double[] averageByInterval;
        if(startSerial+interval>=singleStockList.size()&&endSerial+interval>=singleStockList.size()){
            return null;
        }
        else {
            averageByInterval = new double[endSerial - startSerial+1];
        }
        for(int i=startSerial;i<=endSerial;i++){
                int temp = interval-1;
                double partialSum = 0.0;
                while ((temp >= 0) && (i + temp < singleStockList.size())) {
                    partialSum += singleStockList.get(i + temp).getClose();
                    temp--;
                }
                averageByInterval[i] = partialSum / (double) interval;
        }
        return averageByInterval;
    }

}
