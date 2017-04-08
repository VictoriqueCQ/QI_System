package quantour.po;

import java.util.Map;

/**
 * Created by dell on 2017/4/3.
 */
public class CombinationPO {//还没继承dataclass
    private Map<Integer,String> stockList;
    private double profit;

    public CombinationPO(Map<Integer, String> stockList, double profit) {
        this.stockList = stockList;
        this.profit = profit;
    }

    public Map<Integer, String> getStockList() {
        return stockList;
    }

    public double getProfit() {
        return profit;
    }
}
