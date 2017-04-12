package quantour.dataservice;

import quantour.data.Stock;
import quantour.data.datastructure.Index;
import quantour.data.datastructure.StockNameNCode;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/4/12.
 */
public interface DataReader_data {
    List<Stock> readStockList();
    Map<String,List<StockNameNCode>> readPlate();
    Map<String,List<Index>> readIndex();
}
