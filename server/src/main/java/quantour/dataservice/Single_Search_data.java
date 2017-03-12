package quantour.dataservice;

import quantour.po.StockPO;

import java.text.ParseException;

/**
 * Created by dell on 2017/3/4.
 */
public interface Single_Search_data {
    StockPO getStockList(String[] quest) throws ParseException;
}
