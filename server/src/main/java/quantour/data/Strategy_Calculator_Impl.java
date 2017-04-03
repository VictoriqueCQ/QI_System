package quantour.data;

import quantour.data.strategy.Average_Impl;
import quantour.data.strategy.Momentum_Impl;
import quantour.dataservice.Strategy_Calculator_data;
import quantour.dataservice.Strategy_data;

import java.util.List;

/**
 * Created by dell on 2017/3/31.
 */

/**
 * 用于计算不同策略下选择的股票的各个指标
 */
public class Strategy_Calculator_Impl implements Strategy_Calculator_data {
    private Strategy_data strategyData;
    private List<Stock> stocks;

    Strategy_Calculator_Impl(List<Stock> stocks){
        this.stocks=stocks;
    }

    @Override
    public DataClass get(String[] quest) {
        //这里先筛选股票池和日期
        switch (quest[2]){
            case "M":
                strategyData=new Momentum_Impl(stocks);
                break;
            case "A":
                strategyData=new Average_Impl(stocks);
                break;
        }
        return null;
    }

    private
}
