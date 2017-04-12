package quantour.dataservice;

import quantour.data.Stock;

import java.util.List;

/**
 * Created by dell on 2017/4/12.
 */
public interface Stock_Filter_data {
    List<Stock> filterSingleStock(String code);
    List<Stock> filterStaStock(String[] quest);
}
