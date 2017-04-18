package quantour.po;

import quantour.data.Stock;
import quantour.data.StockSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/4/9.
 */
public class StockSetPO {
    private Map<Integer,Integer> stockSets;

    public StockSetPO(Map<Integer, Integer> stockSets) {
        this.stockSets = stockSets;
    }

    public StockSetPO(StockSet stockSet){
        stockSets=new HashMap<>();
        Map<Integer,List<Stock>> listMap=stockSet.getStockSets();
        for (Integer integer:stockSet.getStockSets().keySet()){
            Stock stock=listMap.get(integer).get(0);
            int code=stock.getCode();
            stockSets.put(integer,code);
        }
    }

    public Map<Integer, Integer> getStockSets() {
        return stockSets;
    }
}
