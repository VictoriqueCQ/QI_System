package quantour.po;

import java.util.Date;
import java.util.Iterator;

/**
 * Created by dell on 2017/3/4.
 */
public class StockPO {
    private String name;
    private String code;
    private Date start;
    private Date over;
    private Iterator open;
    private Iterator high;
    private Iterator low;
    private Iterator close;
    private Iterator volume;//交易数
    private Iterator adjClose;//复权后

    public StockPO(String name, String code, Date start, Date over, Iterator open, Iterator high, Iterator low,
                   Iterator close, Iterator volume, Iterator adjClose) {
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
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Date getStart() {
        return start;
    }

    public Date getOver() {
        return over;
    }

    public Iterator getOpen() {
        return open;
    }

    public Iterator getHigh() {
        return high;
    }

    public Iterator getLow() {
        return low;
    }

    public Iterator getClose() {
        return close;
    }

    public Iterator getVolume() {
        return volume;
    }

    public Iterator getAdjClose() {
        return adjClose;
    }
}
