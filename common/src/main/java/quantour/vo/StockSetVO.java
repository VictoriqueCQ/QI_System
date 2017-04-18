package quantour.vo;

import java.util.Map;

/**
 * Created by 朱晨乾 on 2017/4/15.
 */
public class StockSetVO {
    private Map<String,String> stockSets;

    public StockSetVO(Map<String,String> stockSets) {
        this.stockSets = stockSets;
    }

    public Map<String,String> getStockSets() {
        return stockSets;
    }
}
