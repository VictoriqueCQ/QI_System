package quantour.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 别忘了把所有停过牌的股票删掉
 * Created by dell on 2017/4/8.
 */
public class StockFilter {
    private List<Stock> stockList;

    public StockFilter(List<Stock> stockList) {
        this.stockList = stockList;
    }

    //得到单个股票
    public List<Stock> filterSingleStock(String code){
        int code_i=Integer.parseInt(code);
        return stockList.stream().filter(stock -> stock.getCode()==code_i).collect(Collectors.toList());
    }


    //得到自选股票(删掉)
    public List<Stock> filterArbStock(String[] quest){
        List<Stock> arbStocks=new ArrayList<>();
        for(int i=9;i<quest.length;i++){

        }
        return null;
    }

    //得到股票池股票
    public List<Stock> filterStaStock(String[] quest){

        return null;
    }


}
