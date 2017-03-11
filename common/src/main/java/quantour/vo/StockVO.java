package quantour.vo;

import quantour.po.StockPO;

import java.util.ArrayList;
import java.util.Date;

/**
 * 单只股票VO
 * Created by dell on 2017/3/4.
 */
public class StockVO {
    private String name;
    private int code;
    private Date start;
    private Date over;
    private double[] open;
    private double[] high;
    private double[] low;
    private double[] close;
    private int[] volume;//交易数
    private double[] adjClose;//复权后
    private double[] average5;
    private double[] average10;
    private double[] average20;
    private double[] average30;
    private double[] average60;
    private ArrayList<Double> profit;//每日收益率
    private double variance;//相对方差


    public StockVO(String name, int code, Date start, Date over, double[] open, double[] high, double[] low,
                   double[] close, int[] volume, double[] adjClose, double[] average5, double[] average10,
                   double[] average20, double[] average30, double[] average60, ArrayList<Double> profit, double variance) {
        this.name = name;
        this.code = code;
        this.start = start;
        this.over = over;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.adjClose = adjClose;
        this.average5 = average5;
        this.average10 = average10;
        this.average20 = average20;
        this.average30 = average30;
        this.average60 = average60;
        this.variance = variance;
        this.profit = profit;
    }
    public StockVO(StockPO stockPO){
        this.name = stockPO.getName();
        this.code = stockPO.getCode();
        this.start = stockPO.getStart();
        this.over = stockPO.getOver();
        this.open  = stockPO.getOpen();
        this.high = stockPO.getHigh();
        this.low = stockPO.getLow();
        this.close = stockPO.getClose();
        this.volume = stockPO.getVolume();
        this.adjClose = stockPO.getAdjClose();
        this.average5 = stockPO.getAverage5();
        this.average10 = stockPO.getAverage10();
        this.average20 = stockPO.getAverage20();
        this.average30 = stockPO.getAverage30();
        this.average60 = stockPO.getAverage60();
        this.variance = stockPO.getVariance();
        this.profit = stockPO.getProfit();

    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public Date getStart() {
        return start;
    }

    public Date getOver() {
        return over;
    }

    public double[] getOpen() {
        return open;
    }

    public double[] getHigh() {
        return high;
    }

    public double[] getLow() {
        return low;
    }

    public double[] getClose() {
        return close;
    }

    public int[] getVolume() {
        return volume;
    }

    public double[] getAdjClose() {
        return adjClose;
    }

    public double[] getAverage5() {
        return average5;
    }

    public double[] getAverage10() {
        return average10;
    }

    public double[] getAverage20() {
        return average20;
    }

    public double[] getAverage30() {
        return average30;
    }

    public double[] getAverage60() {
        return average60;
    }

    public Double getVariance() {
        return variance;
    }

    public ArrayList<Double> getProfit(){return profit;}
}
