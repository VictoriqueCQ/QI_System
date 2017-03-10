package quantour.po;

import java.util.Date;

/**
 * Created by dell on 2017/3/4.
 */
public class StockPO {
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
    private double[] average;
    private double variance;//相对方差

    public StockPO(String name, int code, Date start, Date over, double[] open, double[] high, double[] low,
                   double[] close, int[] volume, double[] adjClose, double[] average, double variance) {
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

    public double[] getAverage() {
        return average;
    }

    public double getVariance() {
        return variance;
    }
}
