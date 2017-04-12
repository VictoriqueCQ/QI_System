package quantour.data.strategy;

import quantour.data.Stock;
import quantour.data.StockSet;
import quantour.dataservice.Strategy_data;

import java.util.List;

/**
 * Created by dell on 2017/3/31.
 */
public class Momentum_Impl implements Strategy_data{
    private List<Stock> stocks;

    public Momentum_Impl(List<Stock> stocks){
        this.stocks=stocks;
    }

    @Override
    public List<StockSet> getSets(String[] quest) {

        return null;
    }
}
