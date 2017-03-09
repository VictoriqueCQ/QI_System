package quantour.data;

import java.util.Date;

/**
 * Created by dell on 2017/3/5.
 */
public class DayStockDeal {
    private int serial;
    private Date date;
    private double open;
    private double high;
    private double low;
    private double close;
    private int volume;
    private double adjClose;

    DayStockDeal(int serial, Date date, double open, double high, double low, double close, int volume, double adjClose) {
        this.serial = serial;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.adjClose = adjClose;
    }

    public int getSerial() {
        return serial;
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public int getVolume() {
        return volume;
    }

    public double getAdjClose() {
        return adjClose;
    }
}
