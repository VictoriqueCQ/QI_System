package vo;

import java.util.ArrayList;

/**
 * 单只股票VO
 * Created by dell on 2017/3/4.
 */
public class StockVO {
    private ArrayList<Double> open;
    private ArrayList<Double> close;
    private ArrayList<Double> high;
    private ArrayList<Double> low;
    private ArrayList<Double> average;
    private ArrayList<Double> variance;//相对方差

    public StockVO(ArrayList<Double> open, ArrayList<Double> close, ArrayList<Double> high, ArrayList<Double> low,
                   ArrayList<Double> average, ArrayList<Double> variance) {
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.average = average;
        this.variance = variance;
    }

    public ArrayList<Double> getOpen() {
        return open;
    }

    public ArrayList<Double> getClose() {
        return close;
    }

    public ArrayList<Double> getHigh() {
        return high;
    }

    public ArrayList<Double> getLow() {
        return low;
    }

    public ArrayList<Double> getAverage() {
        return average;
    }

    public ArrayList<Double> getVariance() {
        return variance;
    }
}
