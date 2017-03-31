package quantour.dataservice;

import quantour.data.StockSet;

import java.util.List;

/**
 * Created by dell on 2017/3/31.
 */
public interface Strategy_data {
    List<StockSet> getSets(String[] quest);//quest代表指令split后的数组
}
