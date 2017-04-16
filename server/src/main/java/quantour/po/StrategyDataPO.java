package quantour.po;

import quantour.data.DataClass;

import java.util.List;

/**
 * Created by dell on 2017/3/31.
 */
public class StrategyDataPO extends DataClass{
    private double annualReturn;//年化收益率
    private double basicAnnualReturn;//基准年化收益率
    private double alpha;
    private double beta;
    private double sharpeRatio;
    private double maxDrawDown;//最大回撤
    private List<StockSetPO> stockSetPOS;
    private List<Double> profits;
    private List<Double> basicProfits;//基准收益

    public StrategyDataPO(double annualReturn, double basicAnnualReturn, double alpha, double beta, double sharpeRatio,
                          double maxDrawDown, List<StockSetPO> stockSetPOS, List<Double> profits,
                          List<Double> basicProfits) {
        this.annualReturn = annualReturn;
        this.basicAnnualReturn = basicAnnualReturn;
        this.alpha = alpha;
        this.beta = beta;
        this.sharpeRatio = sharpeRatio;
        this.maxDrawDown = maxDrawDown;
        this.stockSetPOS = stockSetPOS;
        this.profits = profits;
        this.basicProfits = basicProfits;
    }

    public double getAnnualReturn() {
        return annualReturn;
    }

    public double getBasicAnnualReturn() {
        return basicAnnualReturn;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getSharpeRatio() {
        return sharpeRatio;
    }

    public double getMaxDrawDown() {
        return maxDrawDown;
    }

    public List<StockSetPO> getStockSetPOS() {
        return stockSetPOS;
    }

    public List<Double> getProfits() {
        return profits;
    }

    public List<Double> getBasicProfits() {
        return basicProfits;
    }
}
