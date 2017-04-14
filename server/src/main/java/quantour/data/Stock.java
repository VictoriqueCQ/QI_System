package quantour.data;

import java.util.Date;

/**
 * Created by dell on 2017/3/9.
 */
public class Stock extends quantour.data.DataClass {
    private int serial;
    private Date date;
    private double open;
    private double high;
    private double low;
    private double close;
    private int volume;
    private double adjClose;
    private int code;
    private String name;
    private String market;

    Stock(int serial, Date date, double open, double high, double low, double close, int volume, double adjClose,
          int code, String name, String market) {
        this.serial = serial;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.adjClose = adjClose;
        this.code = code;
        this.name = name;
        this.market = market;
    }

    public int getSerial() {
        return serial;
    }

    public Date getDate() {
        return date;
    }

    double getOpen() {
        return open;
    }

    double getHigh() {
        return high;
    }

    double getLow() {
        return low;
    }

    double getClose() {
        return close;
    }

    public int getVolume() {
        return volume;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public int getCode() {
        return code;
    }

    String getName() {
        return name;
    }

    public String getMarket() {
        return market;
    }
}
