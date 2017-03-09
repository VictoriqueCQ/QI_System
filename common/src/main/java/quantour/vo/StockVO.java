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
    private ArrayList<Double> average;
    private ArrayList<Double> variance;//相对方差

    public StockVO(String name, int code, Date start, Date over, ArrayList<Double> open, ArrayList<Double> high,
                   ArrayList<Double> low, ArrayList<Double> close, ArrayList<Integer> volume, ArrayList<Double> adjClose, ArrayList<Double> average, ArrayList<Double> variance) {
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
        this.average = average;
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

    public ArrayList<Double> getAverage() {
        return average;
    }

    public ArrayList<Double> getVariance() {
        return variance;
    }
}
