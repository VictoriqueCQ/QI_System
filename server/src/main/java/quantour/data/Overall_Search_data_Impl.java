package quantour.data;

import quantour.dataservice.Overall_Search_data;
import quantour.po.MarketPO;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dell on 2017/3/4.
 */
public class Overall_Search_data_Impl implements Overall_Search_data{
    private List<Stock> marketMap;

    Overall_Search_data_Impl(List<Stock> stockList) {
        this.marketMap=stockList;
    }

    @Override
    public MarketPO getMarketInfo(Date date) {
        List<Stock> today=marketMap.parallelStream().filter(stock -> stock.getDate().compareTo(date)==0).
                sorted(Comparator.comparing(Stock::getCode)).collect(Collectors.toList());
        long sum=today.parallelStream().mapToInt(value -> value.getVolume()).reduce(0,(x,y)->x+y);

        return null;
    }
}
