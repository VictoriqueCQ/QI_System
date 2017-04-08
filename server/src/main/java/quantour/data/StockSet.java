package quantour.data;

/**
 * Created by dell on 2017/3/31.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于计算每一组的收益率等数据
 */
public class StockSet {
    private Map<Integer,List<Stock>> stockSets;

    public StockSet(Map<Integer, List<Stock>> stockSets) {
        this.stockSets = stockSets;
    }

    public Map<Integer, List<Stock>> getStockSets() {
        return stockSets;
    }

    double countProfit(){
        List<Double> eachProift=new ArrayList<>();
        for(List<Stock> temp:stockSets.values()){
            double profit=(temp.get(0).getAdjClose()-temp.get(0).getAdjClose())/temp.get(1).getAdjClose();
            //小日期在前
            eachProift.add(profit);
        }
        return eachProift.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
    }
}
