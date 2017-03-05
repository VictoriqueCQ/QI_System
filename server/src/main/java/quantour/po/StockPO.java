package quantour.po;

import java.util.Iterator;

/**
 * Created by dell on 2017/3/4.
 */
public class StockPO {
    private Iterator open;
    private Iterator close;
    private Iterator rehabClose;//复权后
    private Iterator volume;//交易数
    private Iterator high;
    private Iterator low;

    public StockPO(Iterator open, Iterator close, Iterator rehabClose, Iterator volume, Iterator high, Iterator low) {
        this.open = open;
        this.close = close;
        this.rehabClose = rehabClose;
        this.volume = volume;
        this.high = high;
        this.low = low;
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
}
