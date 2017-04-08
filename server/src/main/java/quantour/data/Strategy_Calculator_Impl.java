package quantour.data;

import quantour.data.strategy.Average_Impl;
import quantour.data.strategy.Momentum_Impl;
import quantour.dataservice.Strategy_Calculator_data;
import quantour.dataservice.Strategy_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        List<StockSet> stockSets=strategyData.getSets(quest);
        List<Double> profitList=new ArrayList<>();
        for(StockSet stockSet:stockSets){
            profitList.add(stockSet.countProfit());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        Date startTime;
        Date endTime;
        try{
            startTime=sdf.parse(quest[3]);
            endTime=sdf.parse(quest[4]);
        }catch (ParseException pe){
            pe.printStackTrace();
            return null;
        }
        long l = endTime.getTime()-startTime.getTime();
        double interval=l/(1000*60*60*24);

        double annualProfit=(profitList.stream().mapToDouble(Double::doubleValue).average().getAsDouble()/interval)
                *365;//年化收益率

        switch (quest[6]){
            case "T":

                break;
            case "F":
                //根据板块指数计算
                break;
        }
        return null;
    }

    //计算自选股票池
    private double arbStock(String[] quest){
        int num;
        return 0.0;
    }

    //计算固定股票池
    private double staStock(String[] quest){
        return 0.0;
    }
}
