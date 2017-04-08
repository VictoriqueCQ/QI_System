package quantour.data;

import java.util.List;

/**
 * 别忘了把所有停过牌的股票删掉
 * Created by dell on 2017/4/8.
 */
public class StockFilter {
    private List<Stock> stockList;

    public StockFilter(List<Stock> stockList) {
        this.stockList = stockList;
    }

    //得到自选股票
    public List<Stock> filterArbStock(String[] quest){
        return null;
    }

    //得到股票池股票
    public List<Stock> filterStaStock(String[] quest){
        return null;
    }


}
