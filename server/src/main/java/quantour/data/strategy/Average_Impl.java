package quantour.data.strategy;

import quantour.data.Stock;
import quantour.data.StockSet;
import quantour.dataservice.Strategy_data;

import java.util.List;

/**
 * Created by dell on 2017/3/31.
 */
public class Average_Impl implements Strategy_data{
    private List<Stock> stocks;

    public Average_Impl(List<Stock> stocks){
        this.stocks=stocks;
    }

    @Override
    public List<StockSet> getSets(String[] quest) {
        return null;
    }
}
