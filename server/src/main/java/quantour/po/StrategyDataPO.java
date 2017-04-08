package quantour.po;

import quantour.data.DataClass;

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
    private CombinationPO combinationPO;

    public StrategyDataPO(double annualReturn, double basicAnnualReturn, double alpha, double beta, double sharpeRatio,
                          double maxDrawDown, CombinationPO combinationPO) {
        this.annualReturn = annualReturn;
        this.basicAnnualReturn = basicAnnualReturn;
        this.alpha = alpha;
        this.beta = beta;
        this.sharpeRatio = sharpeRatio;
        this.maxDrawDown = maxDrawDown;
        this.combinationPO = combinationPO;
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

    public CombinationPO getCombinationPO() {
        return combinationPO;
    }
}
