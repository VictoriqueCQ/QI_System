package quantour.data.strategy;

import quantour.data.DataFactory_CSV_Impl;
import quantour.data.Stock;
import quantour.data.StockSet;
import quantour.data.datastructure.Index;
import quantour.dataservice.Stock_Filter_data;
import quantour.dataservice.Strategy_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static quantour.data.DataFactory_CSV_Impl.getInstance;

/**
 * Created by dell on 2017/3/31.
 */
public class Momentum_Impl implements Strategy_data{
    private Map<Integer,List<Stock>> stockPool;
    private List<Double> basicProfits;

    public Momentum_Impl(){
        stockPool=new HashMap<>();
        basicProfits=new ArrayList<>();
    }


    @Override
    public List<StockSet> getSets(String[] quest) {
        DataFactory_CSV_Impl dataFactoryCsv=null;
        try{
            dataFactoryCsv=getInstance();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy");
        Date startTime=null;
        Date endTime=null;
        try{
            startTime=sdf.parse(quest[3]);
            endTime=sdf.parse(quest[4]);
        }catch (ParseException pe){
            pe.printStackTrace();
            return null;
        }

        int formativePeriod=Integer.parseInt(quest[5]);
        int holdingPeriod=Integer.parseInt(quest[7]);

        Stock_Filter_data stockFilterData=dataFactoryCsv.getStockFilterData();
        List<Index> indices=null;
        int winnerSize=0;
        if(quest[6].equals("T")) {
            winnerSize=(int)((quest.length-9)*0.2);
            for (int i = 9; i < quest.length; i++) {
                int code = Integer.parseInt(quest[i]);
                stockPool.put(code, stockFilterData.filterSingleStock(code));
            }
        }else{
            stockPool=stockFilterData.filterStaStock(quest);
            indices=stockFilterData.getIndexList().get(quest[6]);
            winnerSize=(int)(stockPool.size()*0.2);
        }

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.DAY_OF_YEAR,-formativePeriod);
        Date initialDate=calendar.getTime();
        Date overDate=startTime;
        calendar.setTime(overDate);
        calendar.add(Calendar.DAY_OF_YEAR,holdingPeriod);
        Date changeDate=calendar.getTime();

        List<StockSet> stockSets=new ArrayList<>();
        Set<Integer> codes= stockPool.keySet();
        while(overDate.compareTo(endTime)<0){
            List<Candidate> candidates=new ArrayList<>();
            for(int c:codes){
                Date change=changeDate;
                Date initial=initialDate;
                List<Stock> currentStock=stockPool.get(c);
                List<Stock> currentCalculate=currentStock.stream().
                        filter(stock -> stock.getDate().compareTo(change)<=0).
                        filter(stock -> stock.getDate().compareTo(initial)>=0)
                        .collect(Collectors.toList());
                boolean toDelete=false;
                for(Stock temp:currentCalculate){
                    if(temp.getVolume()==0){
                        toDelete=true;
                        break;
                    }
                }
                if(toDelete){
                    continue;
                }

                else{
                    Date over=overDate;
                    List<Stock> formativeList=currentCalculate.stream().
                            filter(stock -> stock.getDate().compareTo(over)<0).
                            sorted(Comparator.comparing(Stock::getSerial)).collect(Collectors.toList());
                    double endPrice=formativeList.get(0).getAdjClose();
                    double startPrice=formativeList.get(formativeList.size()-1).getAdjClose();
                    double profit=(endPrice-startPrice)/startPrice;
                    List<Stock> holdingList=currentCalculate.stream().
                            filter(stock -> stock.getDate().compareTo(over)>=0).
                            sorted(Comparator.comparing(Stock::getSerial)).collect(Collectors.toList());
                    Candidate candidate=new Candidate(c,holdingList.get(0),holdingList.get(holdingList.size()-1),
                            profit);
                    candidates.add(candidate);
                }
            }

            candidates=candidates.stream().sorted(Comparator.comparing(Candidate::getProfit)).
                    collect(Collectors.toList());//从小到大排序

            if(quest[6].equals("T")) {
                basicProfits.add(candidates.stream().mapToDouble(Candidate::getProfit).average().getAsDouble());
            }
            else{
                Date over=overDate;
                Date change=changeDate;
                List<Index> indexList=indices.stream().
                        filter(index -> index.getDate().compareTo(over)>=0&&index.getDate().compareTo(change)<0).
                        collect(Collectors.toList());
                Index start=indexList.get(0);
                Index end=indexList.get(indexList.size()-1);
                basicProfits.add((end.getClose()-start.getClose())/start.getClose());
            }

            candidates=candidates.subList(candidates.size()-winnerSize,candidates.size());

            Map<Integer,List<Stock>> map=new HashMap<>();
            for(int i=candidates.size()-1;i>=0;i--){
                List<Stock> temp=new ArrayList<>();
                temp.add(candidates.get(i).getS1());
                temp.add(candidates.get(i).getS2());
                map.put(candidates.size()-i,temp);
            }
            StockSet stockSet=new StockSet(map);
            stockSets.add(stockSet);

            calendar.setTime(changeDate);
            calendar.add(Calendar.DAY_OF_YEAR,1);
            initialDate=calendar.getTime();

            calendar.setTime(initialDate);
            calendar.add(Calendar.DAY_OF_YEAR,formativePeriod);
            overDate=calendar.getTime();

            calendar.setTime(overDate);
            calendar.add(Calendar.DAY_OF_YEAR,holdingPeriod);
            changeDate=calendar.getTime();
        }


        return stockSets;
    }


    public Map<Integer, List<Stock>> getStockPool() {
        return stockPool;
    }

    @Override
    public List<Double> getBasicProfits() {
        return basicProfits;
    }

    class Candidate{
        int code;
        Stock s1;
        Stock s2;
        double profit;

        public Candidate(int code, Stock s1, Stock s2, double profit) {
            this.code = code;
            this.s1 = s1;
            this.s2 = s2;
            this.profit = profit;
        }

        public int getCode() {
            return code;
        }

        public Stock getS1() {
            return s1;
        }

        public Stock getS2() {
            return s2;
        }

        public double getProfit() {
            return profit;
        }
    }

}
