package quantour.dataservice;

import quantour.data.Stock;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/4/12.
 */
public interface Stock_Filter_data {
    List<Stock> filterSingleStock(int code);
    Map<Integer,List<Stock>> filterStaStock(String[] quest);
}
