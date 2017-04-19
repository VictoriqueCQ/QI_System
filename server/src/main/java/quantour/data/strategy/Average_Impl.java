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
 * Average用Stock_Filter_Data_Impl进行筛选，得到的初步筛选结果需要以成员变量的方式保存在类中
 * tock_Filter_Data_Impl怎么用可以参照注释
 * 可以参照Momentum
 * Created by cyy on 2017/3/31.
 */
public class Average_Impl implements Strategy_data {

    private Map<Integer, List<Stock>> stockPool;//用于保存所选择的股票池里所有股票的信息，integer为code
//
    private List<Double> basicProfits;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

    public Average_Impl() {

    }

    @Override
    public List<StockSet> getSets(String[] quest) {
        stockPool = new HashMap<>();
        basicProfits=new ArrayList<>();
        DataFactory_CSV_Impl dataFactoryCsv = null;
        try{
            dataFactoryCsv=getInstance();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


        final Date startDate;
        final Date endDate ;
        try{
            startDate=sdf.parse(quest[3]);
            endDate=sdf.parse(quest[4]);
        }catch (ParseException pe){
            pe.printStackTrace();
            return null;
        }

        int shapeTime = Integer.parseInt(quest[5]);//形成期,比价基准，均线
        int holdTime = Integer.parseInt(quest[7]);//持有期
        int holdNumber = Integer.parseInt(quest[8]);//持股数
        int winnerSize = Integer.parseInt(quest[8]);//持股数;
        List<Index> indices=null;

//        int winnerSize = 0;
        //自选股票
        Stock_Filter_data stockFilterData = dataFactoryCsv.getStockFilterData();
        if (quest[6].equals("T")) {
//            winnerSize=(int)((quest.length-9)*0.2);
            for (int i = 9; i < quest.length; i++) {
                int code = Integer.parseInt(quest[i]);
                stockPool.put(code, stockFilterData.filterSingleStock(code));
            }
        } else {
            stockPool = stockFilterData.filterStaStock(quest);
            indices=stockFilterData.getIndexList().get(quest[9]);
//            winnerSize=(int)(stockPool.size()*0.2);
        }

        int startSerial=0;
        int endSerial=0;
        Set<Integer> codes=stockPool.keySet();
        for(int i:codes){
            List<Stock> temp=stockPool.get(i);
            List<Stock> filted=temp.stream().
                    filter(stock -> stock.getDate().compareTo(endDate)<=0&&stock.getDate().compareTo(startDate)>=0).
                    collect(Collectors.toList());
            startSerial=filted.stream().mapToInt(Stock::getSerial).max().getAsInt();
            endSerial=filted.stream().mapToInt(Stock::getSerial).min().getAsInt();
            break;
        }

        int initialDate=startSerial+shapeTime;
        int overDate=startSerial;
        int changeDate=startSerial-holdTime;


      /*  Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -shapeTime);
        Date initialDate = calendar.getTime();//形成期的开始
        Date overDate = startDate;
        calendar.setTime(overDate);//算形成期的时候的结束日期
        calendar.add(Calendar.DAY_OF_YEAR, holdTime);
        Date changeDate = calendar.getTime();//调仓*/

        List<StockSet> stockSets = new ArrayList<>();

//        Set<Integer> codes = stockPool.keySet();//股票编码

        while (overDate>endSerial) {
            List<Candidate1> candidates = new ArrayList<>();
            for (int c : codes) {
                int change = changeDate;
                int initial = initialDate;
                List<Stock> currentStock = stockPool.get(c);
                List<Stock> currentCalculate = currentStock.stream().
                        filter(stock -> stock.getSerial() > change).
                        filter(stock -> stock.getSerial() <= initial)
                        .collect(Collectors.toList());
                boolean toDelete = false;
                for (Stock temp : currentCalculate) {
                    if (temp.getVolume() == 0) {
                        toDelete = true;
                        break;
                    }
                }
                if (toDelete) {
                    continue;
                } else {
                    int over = overDate;
                    List<Stock> formativeList = currentCalculate.stream().
                            filter(stock -> stock.getSerial() > over).
                            sorted(Comparator.comparing(Stock::getSerial)).collect(Collectors.toList());

                    double endPrice = formativeList.get(0).getAdjClose();
                    double startPrice = formativeList.get(formativeList.size() - 1).getAdjClose();
                    double profit = (endPrice - startPrice) / startPrice;

                    double sum = 0;
                    for (int i = 0; i < formativeList.size(); i++) {
                        sum = sum + formativeList.get(i).getAdjClose();
                    }
                    double average = sum / shapeTime;
                    List<Stock> holdingList = currentCalculate.stream().
                            filter(stock -> stock.getSerial() <= over).
                            sorted(Comparator.comparing(Stock::getSerial)).collect(Collectors.toList());
                    double currentPrice = holdingList.get(holdingList.size() - 1).getAdjClose();
                    double deviate = (average - currentPrice) / average;
                    Candidate1 candidate1 = new Candidate1(c, holdingList.get(0), holdingList.get(holdingList.size() - 1),
                            deviate, profit);

                    candidates.add(candidate1);


                }
            }
            if (candidates.size() > 0&&candidates.size()>=winnerSize) {
                candidates = candidates.stream().sorted(Comparator.comparing(Candidate1::getDeviate)).
                        collect(Collectors.toList());//从小到大排序


                //基准收益率
                if (quest[6].equals("T")) {
                    basicProfits.add(candidates.stream().mapToDouble(Candidate1::getProfit).average().getAsDouble());
                } else {
                    Date change = candidates.get(0).getS1().getDate();
                    Date over = candidates.get(0).getS2().getDate();
                    List<Index> indexList = indices.stream().
                            filter(index -> index.getDate().compareTo(over) >= 0 && index.getDate().compareTo(change) < 0).
                            collect(Collectors.toList());
                    Index start = indexList.get(0);
                    Index end = indexList.get(indexList.size() - 1);
                    basicProfits.add((end.getClose() - start.getClose()) / start.getClose());
                }


                candidates = candidates.subList(candidates.size() - winnerSize, candidates.size());

                Map<Integer, List<Stock>> map = new HashMap<>();//取百分之二十的赢家组合
                System.out.println("ca shi "+candidates.size());

                for (int i = winnerSize - 1; i >= 0; i--) {
//                    System.out.println("i de zhi wei"+i);
                    System.out.println("di"+i+":"+candidates.get(i).getCode());
                    List<Stock> temp = new ArrayList<>();
                    temp.add(candidates.get(i).getS1());
                    temp.add(candidates.get(i).getS2());
                    map.put(candidates.size() - i, temp);
                }
                StockSet stockSet = new StockSet(map);
                stockSets.add(stockSet);
            }

                overDate = changeDate;

                initialDate = overDate + shapeTime;

                startSerial = overDate;

                changeDate = startSerial - holdTime;


        }
        return stockSets;
    }


    public Map<Integer, List<Stock>> getStockPool () {
            return stockPool;
        }

    public List<Double> getBasicProfits() {
        return basicProfits;
    }

        class Candidate1 {
            int code;
            Stock s1;
            Stock s2;
            double deviate;
            double profit;

            public Candidate1(int code, Stock s1, Stock s2, double deviate,double profit) {
                this.code = code;
                this.s1 = s1;
                this.s2 = s2;
                this.deviate = deviate;
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

            public double getDeviate() {return deviate;}

            public double getProfit(){return profit;}


        }


}
