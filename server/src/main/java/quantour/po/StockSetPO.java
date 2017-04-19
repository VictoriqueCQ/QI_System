package quantour.po;

import quantour.data.Stock;
import quantour.data.StockSet;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/4/9.
 */
public class StockSetPO {
    private Map<String,String> stockSets;
    private Date date;

    public StockSetPO(StockSet stockSet){
        stockSets=new HashMap<>();
        Map<Integer,List<Stock>> listMap=stockSet.getStockSets();
        for (Integer integer:stockSet.getStockSets().keySet()){
            Stock stock=listMap.get(integer).get(0);
            int code=stock.getCode();
            stockSets.put(integer+"",code+"");
        }
        if(listMap!=null&&listMap.size()!=0) {
            date =listMap.get(1).get(1).getDate();
            if(date==null){
                System.out.print("1");
            }
        }
    }

    public Date getDate(){return date;}
    public Map<String, String> getStockSets() {
        return stockSets;
    }

    public Date getDate() {
        return date;
    }
}
