package quantour.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by 朱晨乾 on 2017/4/15.
 */
public class StockSetVO {
    private Map<Integer, Integer> stockSets;

    public StockSetVO(Map<Integer, Integer> stockSets) {
        this.stockSets = stockSets;
    }

    public Map<Integer, Integer> getStockSets() {
        return stockSets;
    }
}
