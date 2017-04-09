package quantour.data;

import quantour.data.strategy.Average_Impl;
import quantour.data.strategy.Momentum_Impl;
import quantour.dataservice.Strategy_Calculator_data;
import quantour.dataservice.Strategy_data;
import quantour.po.StockSetPO;
import quantour.po.StrategyDataPO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public StrategyDataPO get(String[] quest) {
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
        double d1=profitList.stream().mapToDouble(num -> num * num).average().getAsDouble();
        double annualProfit=(e1/interval) *365;//年化收益率

        Count count=null;
        switch (quest[6]){
            case "T":
                count=arbStock(quest);
                break;
            case "F":
                //根据板块指数计算
                break;
        }
        double e2=count.getE();
        double basicAnnualProfit=(e2/interval)*365;//基准年化收益率

        double sum=0.0;
        for (int i=0;i<profitList.size();i++){
            sum=sum+profitList.get(i)*count.getProfits(i);
        }
        double beta=(sum/profitList.size()-e1*e2)/count.getD(e2);//β

        double rf=0.0;//待读，无风险收益率
        double alpha=(annualProfit-rf)-beta*(basicAnnualProfit-rf);//α

        double sharpe=(e1-rf)/Math.sqrt(d1);//夏普比率

        List<Double> maxDrawBacks=new ArrayList<>();
        for(int i=profitList.size()-1;i>=0;i++){
            double today=profitList.get(i);
            double min=profitList.subList(0,i).stream().mapToDouble(Double::doubleValue).min().getAsDouble();
            maxDrawBacks.add(today-min);
        }
        double maxDrawDown=maxDrawBacks.stream().mapToDouble(Double::doubleValue).max().getAsDouble();//最大回撤

        List<StockSetPO> stockSetPOS=new ArrayList<>();
        for (StockSet stockSet : stockSets) {
            stockSetPOS.add(new StockSetPO(stockSet));
        }
        return new StrategyDataPO(annualProfit,basicAnnualProfit,alpha,beta,sharpe,maxDrawDown,stockSetPOS,profitList);
    }

    //计算自选股票池
    private Count arbStock(String[] quest){
        StockFilter stockFilter=new StockFilter(stocks);
        List<Stock> filted=stockFilter.filterArbStock(quest);
        List<Double> profits=new ArrayList<>();
        List<Double> profitsSquare=new ArrayList<>();
        for (int i=9;i<quest.length;i++){
            String base = quest[i];
            List<Stock> singleStock=filted.stream().filter(stock -> stock.getName().equals(base)).
                    collect(Collectors.toList());
            Stock start;
            Stock end;
            if(singleStock.size()>=2){
                start=singleStock.get(singleStock.size()-1);//大日期在前
                end=singleStock.get(0);//这里需要检查是否为开始日期和结束日期
                double profit=(end.getAdjClose()-start.getAdjClose())/start.getAdjClose();
                profits.add(profit);
                profitsSquare.add(profit*profit);
            }
            //System.out.println(start.getDate());
            //System.out.println(start.getDate());
        }
        return new Count(profits,profitsSquare);
    }

    //计算固定股票池
    private Count staStock(String[] quest){
        return null;
    }

    class Count{
        List<Double> profits;
        List<Double> profitsSquare;

        Count(List<Double> profits, List<Double> profitsSquare) {
            this.profits = profits;
            this.profitsSquare = profitsSquare;
        }

        double getE(){
            return profits.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        }

        double getD(double e){
            return profitsSquare.stream().mapToDouble(Double::doubleValue).average().getAsDouble()-e*e;
        }

        double getProfits(int i){
            return profits.get(i);
        }

    }

}
