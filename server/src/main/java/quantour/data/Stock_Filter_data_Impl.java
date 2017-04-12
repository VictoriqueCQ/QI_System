package quantour.data;

import quantour.dataservice.Stock_Filter_data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dell on 2017/4/8.
 */
public class Stock_Filter_data_Impl implements Stock_Filter_data{
    private List<Stock> stockList;

    public Stock_Filter_data_Impl(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @Override
    public List<Stock> filterSingleStock(String code) {
        int code_i=Integer.parseInt(code);
        return stockList.stream().filter(stock -> stock.getCode()==code_i).collect(Collectors.toList());
    }

    @Override
    public List<Stock> filterStaStock(String[] quest) {

        return null;
    }
}
