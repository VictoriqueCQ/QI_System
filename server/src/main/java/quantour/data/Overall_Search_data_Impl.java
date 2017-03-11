package quantour.data;

import quantour.dataservice.Overall_Search_data;
import quantour.po.MarketPO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dell on 2017/3/4.
 */
public class Overall_Search_data_Impl implements Overall_Search_data{
    private List<Stock> marketList;
    private Map<Integer,List<Stock>> marketMap;

    private final Date LOWER_BOUND=new SimpleDateFormat("mm/dd/yy").parse("02/01/05");
    private final Date UPPER_BOUND=new SimpleDateFormat("mm/dd/yy").parse("04/30/14");

    Overall_Search_data_Impl(List<Stock> stockList) throws ParseException {
        this.marketList =stockList;
        marketMap=marketList.stream().collect(Collectors.groupingBy(Stock::getCode));
    }

    @Override
    public MarketPO getMarketInfo(Date date) throws ArrayIndexOutOfBoundsException{
        List<Stock> today= marketList.parallelStream().filter(stock -> stock.getDate().compareTo(date)==0).
                sorted(Comparator.comparing(Stock::getCode)).collect(Collectors.toList());

        long sum=today.parallelStream().mapToInt(Stock::getVolume).reduce(0,(x, y)->x+y);

        //计算涨停和跌停的

        //计算涨幅超过5%的股票数和跌幅超过5%的股票数

        //计算开盘-收盘大于5%*上一个交易日收盘价的股票个数和开盘-收盘小于-5%*上一个交易日收盘价的股票个数
        long oc_overPFivePerNum= today.parallelStream().
                filter(stock -> {
                    Stock previous=marketList.get(marketList.indexOf(stock)+1);
                    return previous.getSerial()!=0&&stock.getClose()-stock.getOpen()>0.05*previous.getClose();
                }).
                count();
        long oc_belowMFivePerNum=today.parallelStream().
                filter(stock -> {
                    Stock previous=marketList.get(marketList.indexOf(stock)+1);
                    return previous.getSerial()!=0&&stock.getClose()-stock.getOpen()<-0.05*previous.getClose();
                }).
                count();


        return null;
    }

    private int countNumOfStock(List<Stock> today,double percentage){

        return 0;
    }

    private int countOCNumOfStock(List<Stock> today,double percentage){

        return 0;
    }

}
