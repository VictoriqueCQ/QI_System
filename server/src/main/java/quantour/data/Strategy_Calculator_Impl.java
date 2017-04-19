package quantour.data;

import quantour.data.strategy.Average_Impl;
import quantour.data.strategy.Momentum_Impl;
import quantour.dataservice.Stock_Filter_data;
import quantour.dataservice.Strategy_Calculator_data;
import quantour.dataservice.Strategy_data;
import quantour.po.StockSetPO;
import quantour.po.StrategyDataPO;

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
    private Stock_Filter_data stockFilterData;

    Strategy_Calculator_Impl(Stock_Filter_data stockFilterData){
        this.stockFilterData=stockFilterData;
    }

    @Override
    public StrategyDataPO get(String[] quest) {
        //这里先筛选股票池和日期
        switch (quest[2]){
            case "M":
                strategyData=new Momentum_Impl();
                break;
            case "A":
                strategyData=new Average_Impl();
                break;
        }

        List<StockSet> stockSets=strategyData.getSets(quest);

        if(stockSets==null||stockSets.size()==0){
            return null;
        }

        List<Double> basicProfitList=strategyData.getBasicProfits();
        List<Double> profitList=new ArrayList<>();//策略收益率list
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

        double e1=profitList.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        double d1=profitList.stream().mapToDouble(num -> num * num).average().getAsDouble()-e1*e1;
        double annualProfit=(e1/interval) *365;//年化收益率

        double e2=basicProfitList.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        double d2=basicProfitList.stream().mapToDouble(num -> num*num).average().getAsDouble()-e2*e2;
        double basicAnnualProfit=(e2/interval)*365;//基准年化收益率

        double sum=0.0;
        for (int i=0;i<profitList.size();i++){
            sum=sum+profitList.get(i)*basicProfitList.get(i);
        }
        double beta=0.0;
        if(d1>0){
            beta=(sum/profitList.size()-e1*e2)/d2;//β
        }

        double rf=stockFilterData.filterPeriodRate(startTime,endTime);
        double alpha=(annualProfit-rf)-beta*(basicAnnualProfit-rf);//α

        double sharpe=0.0;//夏普比率
        if(d1>0){
            sharpe=(e1-rf)/Math.sqrt(d1);
        }

        double maxDrawDown=0.0;
        if(profitList.size()>1){
            List<Double> maxDrawBacks=new ArrayList<>();
            for(int i=0;i<profitList.size()-1;i++){
                double today=profitList.get(i);
                double min=profitList.subList(i+1,profitList.size()).stream().mapToDouble(Double::doubleValue).
                        min().getAsDouble();
                maxDrawBacks.add(today-min);
            }
            maxDrawDown=maxDrawBacks.stream().mapToDouble(Double::doubleValue).max().getAsDouble();//最大回撤
        }
        System.out.println(alpha);
        System.out.println(beta);
        System.out.println(sharpe);
        System.out.println(maxDrawDown);

        List<StockSetPO> stockSetPOS=new ArrayList<>();
        for (StockSet stockSet : stockSets) {
            stockSetPOS.add(new StockSetPO(stockSet));
        }

        return new StrategyDataPO(annualProfit,basicAnnualProfit,alpha,beta,sharpe,maxDrawDown,stockSetPOS,
                profitList,basicProfitList);
    }

}
