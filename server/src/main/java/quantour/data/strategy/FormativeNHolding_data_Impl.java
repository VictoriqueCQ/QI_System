package quantour.data.strategy;

import quantour.data.DataClass;
import quantour.data.StockSet;
import quantour.dataservice.FormativeNHolding_data;
import quantour.dataservice.Strategy_data;
import quantour.po.FormativeNHoldingPO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/4/16.
 */
public class FormativeNHolding_data_Impl implements FormativeNHolding_data{
    private Strategy_data strategyData;

    public FormativeNHolding_data_Impl(){

    }


    @Override
    public DataClass get(String[] quest) {
        if(quest[3].equals("M")){
            strategyData=new Momentum_Impl();
        }
        else{
            strategyData=new Average_Impl();
        }

        List<Double> profits=new ArrayList<>();
        List<Double> winChance=new ArrayList<>();

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

        for(int i=1;i<=30;i++){
            String[] nQuest=quest.clone();
            if(quest[5].equals("F")){
                nQuest[5]=quest[6];
                nQuest[6]=i+"";
            }
            else{
                nQuest[5]=i+"";
            }

            List<StockSet> stockSets=strategyData.getSets(nQuest);
            List<Double> profitList=new ArrayList<>();//策略收益率list
            for(StockSet stockSet:stockSets){
                profitList.add(stockSet.countProfit());
            }

            List<Double> basicProfitList=strategyData.getBasicProfits();

            List<Double> overProfit=new ArrayList<>();
            int winTime=0;
            for(int j=0;j<profitList.size();j++){
                double over=profitList.get(i)-profitList.get(i);
                overProfit.add(over);
                if(over>0){
                    winTime++;
                }
            }

            double avg=(profitList.stream().mapToDouble(Double::doubleValue).average().getAsDouble()/interval)*365;

            profits.add(avg);
            winChance.add(winTime/(double)profitList.size());
        }
        return new FormativeNHoldingPO(profits,winChance);
    }
}
