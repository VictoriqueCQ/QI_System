package quantour.dataservice;

import quantour.data.Stock;
import quantour.data.datastructure.Index;
import quantour.data.datastructure.Rate;
import quantour.data.datastructure.StockNameNCode;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/4/12.
 */
public interface Stock_Filter_data {
    List<Stock> filterSingleStock(int code);
    Map<Integer,List<Stock>> filterStaStock(String[] quest);
    List<Stock> getStockList();
    Map<String, List<StockNameNCode>> getPlateList();
    Map<String, List<Index>> getIndexList();
    List<Rate> getRateList();
}
