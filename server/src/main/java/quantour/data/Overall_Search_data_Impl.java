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
    public MarketPO getMarketInfo(Date date) {
        List<Stock> today= marketList.parallelStream().filter(stock -> stock.getDate().compareTo(date)==0).
                sorted(Comparator.comparing(Stock::getCode)).collect(Collectors.toList());

        List<Stock> last=null;
        Calendar c=Calendar.getInstance();
        c.setTime(date);


        long sum=today.parallelStream().mapToInt(Stock::getVolume).reduce(0,(x, y)->x+y);

        int oc_overPFivePerNum= (int) today.stream().
                filter(stock1 -> (stock1.getClose()-stock1.getOpen())>1.05*marketMap.get(stock1.getCode()).get(stock1.getSerial()-1).getClose()).
                count();


        return null;
    }

    private int countNumOfStock(List<Stock> today,double percentage){

        return 0;
    }

}
