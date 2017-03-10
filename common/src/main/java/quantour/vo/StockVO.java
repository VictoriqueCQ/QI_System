package quantour.vo;

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
    private ArrayList<Double> open;
    private ArrayList<Double> high;
    private ArrayList<Double> low;
    private ArrayList<Double> close;
    private ArrayList<Integer> volume;//交易数
    private ArrayList<Double> adjClose;//复权后
    private ArrayList<Double> average5;
    private ArrayList<Double> average10;
    private ArrayList<Double> average20;
    private ArrayList<Double> average30;
    private ArrayList<Double> average60;
    private ArrayList<Double> variance;//相对方差

    public StockVO(String name, int code, Date start, Date over, ArrayList<Double> open, ArrayList<Double> high,
                   ArrayList<Double> low, ArrayList<Double> close, ArrayList<Integer> volume, ArrayList<Double> adjClose,
                   ArrayList<Double> average5, ArrayList<Double> average10, ArrayList<Double> average20,
                   ArrayList<Double> average30, ArrayList<Double> average60, ArrayList<Double> variance) {
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

    public ArrayList<Double> getOpen() {
        return open;
    }

    public ArrayList<Double> getHigh() {
        return high;
    }

    public ArrayList<Double> getLow() {
        return low;
    }

    public ArrayList<Double> getClose() {
        return close;
    }

    public ArrayList<Integer> getVolume() {
        return volume;
    }

    public ArrayList<Double> getAdjClose() {
        return adjClose;
    }

    public ArrayList<Double> getAverage5() {
        return average5;
    }

    public ArrayList<Double> getAverage10() {
        return average10;
    }

    public ArrayList<Double> getAverage20() {
        return average20;
    }

    public ArrayList<Double> getAverage30() {
        return average30;
    }

    public ArrayList<Double> getAverage60() {
        return average60;
    }

    public ArrayList<Double> getVariance() {
        return variance;
    }
}
