package quantour.vo;

import java.util.Date;

/**
 * 单个股票搜索条件VO
 * Created by dell on 2017/3/4.
 */
public class StockSearchConditionVO {
    private String stockID;
    private String stockName;
    private Date startTime;
    private Date endTime;

    public StockSearchConditionVO(String stockID, String stockName, Date startTime, Date endTime) {
        this.stockID = stockID;
        this.stockName = stockName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStockID() {
        return stockID;
    }

    public String getStockName() {
        return stockName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
