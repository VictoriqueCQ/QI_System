package quantour.data.datastructure;

import java.util.Date;

/**
 * Created by dell on 2017/4/15.
 */
public class Rate {
    Date date;
    double rate;

    public Rate(Date date, double rate) {
        this.date = date;
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public double getRate() {
        return rate;
    }
}
