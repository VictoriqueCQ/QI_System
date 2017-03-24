package quantour.po;

import quantour.data.DataClass;

/**
 * Created by dell on 2017/3/4.
 */
public class MarketPO extends DataClass{
    private double totalDeal;
    private int limitUpNum;
    private int limitDownNum;
    private int overFivePerNum;//涨幅超过5%的股票数
    private int belowFivePerNum;//跌幅超过5%的股票数
    private int oc_overPFivePerNum;//开盘-收盘大于5%*上一个交易日收盘价的股票个数
    private int oc_belowMFivePerNum;//开盘-收盘小于-5%*上一个交易日收盘价的股票个数

    public MarketPO(double totalDeal, int limitUpNum, int limitDownNum, int overFivePerNum, int belowFivePerNum,
                    int oc_overPFivePerNum, int oc_belowMFivePerNum) {
        this.totalDeal = totalDeal;
        this.limitUpNum = limitUpNum;
        this.limitDownNum = limitDownNum;
        this.overFivePerNum = overFivePerNum;
        this.belowFivePerNum = belowFivePerNum;
        this.oc_overPFivePerNum = oc_overPFivePerNum;
        this.oc_belowMFivePerNum = oc_belowMFivePerNum;
    }

    public double getTotalDeal() {
        return totalDeal;
    }

    public int getLimitUpNum() {
        return limitUpNum;
    }

    public int getLimitDownNum() {
        return limitDownNum;
    }

    public int getOverFivePerNum() {
        return overFivePerNum;
    }

    public int getBelowFivePerNum() {
        return belowFivePerNum;
    }

    public int getOc_overPFivePerNum() {
        return oc_overPFivePerNum;
    }

    public int getOc_belowMFivePerNum() {
        return oc_belowMFivePerNum;
    }
}
