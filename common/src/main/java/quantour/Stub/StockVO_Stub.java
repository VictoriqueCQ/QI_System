package quantour.Stub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xjwhhh on 2017/3/17.
 */
public class StockVO_Stub {
    private String name = "深发展A";
    private int code = 1;
    private Date start = new Date(2005, 1, 12);
    private Date over = new Date(2014, 3, 2);
    private double[] open = {10};
    private double[] high = {11};
    private double[] low = {9};
    private double[] close = {10.4};
    private int[] volume = {100};//交易数
    private double[] adjClose = {10};//复权后
    private List<Date> dates;
    private double[] average5 = {10};
    private double[] average10 = {9};
    private double[] average20 = {8};
    private double[] average30 = {7};
    private double[] average60 = {8};
    private ArrayList<Double> profit;//每日收益率
    private double variance = 5;//相对方差

    public StockVO_Stub(String name, int code, Date start, Date over, double[] open, double[] high, double[] low,
                        double[] close, int[] volume, double[] adjClose, List<Date> dates, double[] average5, double[] average10,
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
        this.dates = dates;
        this.average5 = average5;
        this.average10 = average10;
        this.average20 = average20;
        this.average30 = average30;
        this.average60 = average60;
        this.variance = variance;
        this.profit = profit;
    }

    public StockVO_Stub() {
        Date date = new Date(2009, 3, 2);
        this.dates.add(date);
        this.profit.add(10.0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date date) {
        this.start = date;
    }

    public Date getOver() {
        return over;
    }

    public void setOver(Date date) {
        this.over = date;
    }

    public double[] getOpen() {
        return open;
    }

    public void setOpen(double[] open) {
        this.open = open;
    }

    public double[] getHigh() {
        return high;
    }

    public void setHigh(double[] high) {
        this.high = high;
    }

    public double[] getLow() {
        return low;
    }

    public void setLow(double[] low) {
        this.low = low;
    }

    public double[] getClose() {
        return close;
    }

    public void setClose(double[] close) {
        this.close = close;
    }

    public int[] getVolume() {
        return volume;
    }

    public void setVolume(int[] volume) {
        this.volume = volume;
    }

    public double[] getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(double[] adjClose) {
        this.adjClose = adjClose;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public double[] getAverage5() {
        return average5;
    }

    public void setAverage5(double[] average5) {
        this.average5 = average5;
    }

    public double[] getAverage10() {
        return average10;
    }

    public void setAverage10(double[] average10) {
        this.average10 = average10;
    }

    public double[] getAverage20() {
        return average20;
    }

    public void setAverage20(double[] average20) {
        this.average20 = average20;
    }

    public double[] getAverage30() {
        return average30;
    }

    public void setAverage30(double[] average30) {
        this.average30 = average30;
    }

    public double[] getAverage60() {
        return average60;
    }

    public void setAverage60(double[] average60) {
        this.average60 = average60;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public ArrayList<Double> getProfit() {
        return profit;
    }

    public void setProfit(ArrayList<Double> profit) {
        this.profit = profit;
    }
}
