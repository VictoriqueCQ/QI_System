package quantour.data.datastructure;

import java.util.Date;

/**
 * Created by dell on 2017/4/12.
 */
public class Index {
    private Date date;
    private double open;
    private double highest;
    private double lowest;
    private double close;
    private double idprice;//涨跌额
    private double idrate;//涨跌幅
    private double volume;//成交量
    private double turnover;//成交额

    public Index(Date date, double open, double highest, double lowest, double close, double idprice, double idrate,
                 double volume, double turnover) {
        this.date = date;
        this.open = open;
        this.highest = highest;
        this.lowest = lowest;
        this.close = close;
        this.idprice = idprice;
        this.idrate = idrate;
        this.volume = volume;
        this.turnover = turnover;
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHighest() {
        return highest;
    }

    public double getLowest() {
        return lowest;
    }

    public double getClose() {
        return close;
    }

    public double getIdprice() {
        return idprice;
    }

    public double getIdrate() {
        return idrate;
    }

    public double getVolume() {
        return volume;
    }

    public double getTurnover() {
        return turnover;
    }
}
