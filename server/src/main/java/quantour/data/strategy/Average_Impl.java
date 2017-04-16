package quantour.data.strategy;

import quantour.data.DataFactory_CSV_Impl;
import quantour.data.Stock;
import quantour.data.StockSet;
import quantour.dataservice.Stock_Filter_data;
import quantour.dataservice.Strategy_data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Average用Stock_Filter_Data_Impl进行筛选，得到的初步筛选结果需要以成员变量的方式保存在类中
 * tock_Filter_Data_Impl怎么用可以参照注释
 * 可以参照Momentum
 * Created by cyy on 2017/3/31.
 */
public class Average_Impl implements Strategy_data {
    //private List<Stock> stocks;//stocks为根据股票池等筛选信息得到的股票信息
    private Map<Integer, List<Stock>> stockPool;//用于保存所选择的股票池里所有股票的信息，integer为code
//    private List<String> stockNames;
    private List<Double> basicProfits;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

    public Average_Impl() {
        stockPool = new HashMap<>();
        basicProfits=new ArrayList<>();
    }

    @Override
    public List<StockSet> getSets(String[] quest) {
        DataFactory_CSV_Impl dataFactoryCsv = null;
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(quest[3]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endDate = sdf.parse(quest[4]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int shapeTime = Integer.parseInt(quest[5]);//形成期,比价基准，均线
        int holdTime = Integer.parseInt(quest[7]);//持有期


        //自选股票
        Stock_Filter_data stockFilterData = dataFactoryCsv.getStockFilterData();
        if (quest[6].equals("T")) {
            for (int i = 9; i < quest.length; i++) {
                int code = Integer.parseInt(quest[i]);
                stockPool.put(code, stockFilterData.filterSingleStock(code));
            }
        } else {
            stockPool = stockFilterData.filterStaStock(quest);
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, -shapeTime);
        Date initialDate = calendar.getTime();//形成期的开始
        Date overDate = startDate;
        calendar.setTime(overDate);//算形成期的时候的结束日期
        calendar.add(Calendar.DAY_OF_YEAR, holdTime);
        Date changeDate = calendar.getTime();//调仓

        List<StockSet> stockSets = new ArrayList<>();

        Set<Integer> codes = stockPool.keySet();//股票编码

        while (overDate.compareTo(endDate) < 0) {
            List<Candidate1> candidates = new ArrayList<>();
            for (int c : codes) {
                Date change = changeDate;
                Date initial = initialDate;
                List<Stock> currentStock = stockPool.get(c);
                List<Stock> currentCalculate = currentStock.stream().
                        filter(stock -> stock.getDate().compareTo(change) <= 0).
                        filter(stock -> stock.getDate().compareTo(initial) >= 0)
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
                    Date over = overDate;
                    List<Stock> formativeList = currentCalculate.stream().
                            filter(stock -> stock.getDate().compareTo(over) < 0).
                            sorted(Comparator.comparing(Stock::getSerial)).collect(Collectors.toList());
                    double sum = 0;
                    for (int i = 0; i < formativeList.size(); i++) {
                        sum = sum + formativeList.get(i).getAdjClose();
                    }
                    double average = sum / shapeTime;
                    List<Stock> holdingList = currentCalculate.stream().
                            filter(stock -> stock.getDate().compareTo(over) >= 0).
                            sorted(Comparator.comparing(Stock::getSerial)).collect(Collectors.toList());
                    double currentPrice = holdingList.get(holdingList.size()-1).getAdjClose();
                    double deviate = (average - currentPrice) / average;
                    Candidate1 candidate1 = new Candidate1(c, holdingList.get(0), holdingList.get(holdingList.size() - 1),
                            deviate);

                    candidates.add(candidate1);


                }
            }
            candidates = candidates.stream().sorted(Comparator.comparing(Candidate1::getDeviate)).
                    collect(Collectors.toList());//从小到大排序
            Map<Integer, List<Stock>> map = new HashMap<>();//取百分之二十的赢家组合
            for (int i = candidates.size() - 1; i >= candidates.size() * 0.8; i--) {
                List<Stock> temp = new ArrayList<>();
                temp.add(candidates.get(i).getS1());
                temp.add(candidates.get(i).getS2());
                map.put(candidates.size() - i, temp);
            }
            StockSet stockSet = new StockSet(map);
            stockSets.add(stockSet);

            calendar.setTime(changeDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            initialDate = calendar.getTime();

            calendar.setTime(initialDate);
            calendar.add(Calendar.DAY_OF_YEAR, shapeTime);
            overDate = calendar.getTime();

            calendar.setTime(overDate);
            calendar.add(Calendar.DAY_OF_YEAR, holdTime);
            changeDate = calendar.getTime();


        }
        return stockSets;
    }
//        List<Stock> stockList = stocks;// 根据股票池或者自选股票啥的得到的股票


        //stocks = FiltExceptST(stocks);



    /*   List<Date> changeDate = null;// 计算若干个调仓时间
        changeDate.add(startDate);
        Date date=startDate;//取时间
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,holdTime);
        while(calendar.getTime().compareTo(endDate)<=0){
            changeDate.add(calendar.getTime());
            calendar.add(calendar.DATE,holdTime);
        }
        //List<Stock> storeStocks = stocks;*/

        /*
         *Integer为排名，stocksets里只要开始和结束的信息

    public StockSet(Map<Integer, List<Stock>> stockSets) {
            this.stockSets = stockSets;
        }*/

     /*   List<StockSet> result = new ArrayList<StockSet>();
        for(int i=0;i<changeDate.size();i++){
            Date changeStocks = changeDate.get(i);
            //stocks = storeStocks;
            Map<Integer,List<Stock>> stockList = null;
            List<String> hasBeChosen;
            /*for(int j=0;j<numOfStocks;j++){
                stockList.put(j,getBestChoice(changeDate.get(i),changeDate.get(i+1),stocks));
                String name = stockList.get(j).get(0).getName();
                stocks = stocks.stream().
                        filter(stock -> !(stock.getName().equals(name))).collect(Collectors.toList());

            }*/



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

            public Candidate1(int code, Stock s1, Stock s2, double deviate) {
                this.code = code;
                this.s1 = s1;
                this.s2 = s2;
                this.deviate = deviate;
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

            public double getDeviate() {
                return deviate;
            }


        }


}
