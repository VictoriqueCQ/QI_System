package quantour.dataservice;

import quantour.po.StockPO;
import quantour.vo.StockVO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dell on 2017/3/4.
 */
public interface Single_Search_data {
    ArrayList<StockPO> getStockListByID(int stockID, Date startTime,Date endTime);
    ArrayList<StockVO> getStockListByName(String stockName,Date startTime,Date endTime);
}
