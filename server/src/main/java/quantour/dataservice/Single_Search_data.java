package quantour.dataservice;

import quantour.po.StockPO;

import java.util.Date;

/**
 * Created by dell on 2017/3/4.
 */
public interface Single_Search_data {
    StockPO getStockListByID(int stockID, Date startTime,Date endTime);
    StockPO getStockListByName(String stockName,Date startTime,Date endTime);//日后还要改进，目前只能实现完全匹配
}
