package vo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 单只股票VO
 * Created by dell on 2017/3/4.
 */
public class StockVO {
    private Iterator open;
    private Iterator close;
    private Iterator rehabClose;//复权后
    private Iterator volume;//交易数
    private Iterator high;
    private Iterator low;
    private Iterator average;
    private Iterator variance;//相对方差

    public StockVO(Iterator open, Iterator close, Iterator rehabClose, Iterator volume, Iterator high, Iterator low,
                   Iterator average, Iterator variance) {
        this.open = open;
        this.close = close;
        this.rehabClose = rehabClose;
        this.volume = volume;
        this.high = high;
        this.low = low;
        this.average = average;
        this.variance = variance;
    }

    public Iterator getOpen() {
        return open;
    }

    public Iterator getClose() {
        return close;
    }

    public Iterator getRehabClose() {
        return rehabClose;
    }

    public Iterator getVolume() {
        return volume;
    }

    public Iterator getHigh() {
        return high;
    }

    public Iterator getLow() {
        return low;
    }

    public Iterator getAverage() {
        return average;
    }

    public Iterator getVariance() {
        return variance;
    }
}
