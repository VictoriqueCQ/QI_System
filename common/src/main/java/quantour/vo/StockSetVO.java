package quantour.vo;

import java.util.Date;
import java.util.Map;

/**
 * Created by 朱晨乾 on 2017/4/15.
 */
public class StockSetVO {
    private Map<String, String> stockSets;
    private Date date;

    public StockSetVO(Map<String, String> stockSets) {
        this.stockSets = stockSets;
    }

    public void setDate(Date date){
        this.date = date;
    }
    public Date getDate() {
        return date;
    }

    public Map<String, String> getStockSets() {
        return stockSets;
    }
}
