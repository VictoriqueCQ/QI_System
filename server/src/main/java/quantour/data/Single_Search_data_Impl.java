package quantour.data;

import quantour.dataservice.Single_Search_data;
import quantour.po.StockPO;
import quantour.vo.StockVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dell on 2017/3/4.
 */
public class Single_Search_data_Impl implements Single_Search_data{
    private List<Stock> stockList;

    Single_Search_data_Impl(List<Stock> stockList){
        this.stockList =stockList;
    }

    @Override
    public ArrayList<StockPO> getStockListByID(int stockID, Date startTime, Date endTime) {
        List<Stock> singleStockList=stockList.stream().
                filter(stock -> ((stock.getCode()==stockID)&&(stock.getDate().after(startTime))&&(stock.getDate().before(endTime)))).
                collect(Collectors.toList());

        return null;
    }

    @Override
    public ArrayList<StockVO> getStockListByName(String stockName, Date startTime, Date endTime) {
        return null;
    }
}
