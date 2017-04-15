package quantour.vo;

import java.util.List;

/**
 * Created by 朱晨乾 on 2017/4/15.
 */
public class StrategyDataVO {
    private double annualReturn;//年化收益率
    private double basicAnnualReturn;//基准年化收益率
    private double alpha;
    private double beta;
    private double sharpeRatio;
    private double maxDrawDown;//最大回撤
    private List<StockSetVO> stockSetVOS;
    private List<Double> profits;

    public StrategyDataVO(double annualReturn, double basicAnnualReturn, double alpha, double beta, double sharpeRatio,
                          double maxDrawDown, List<StockSetVO> stockSetVOS, List<Double> profits) {
        this.annualReturn = annualReturn;
        this.basicAnnualReturn = basicAnnualReturn;
        this.alpha = alpha;
        this.beta = beta;
        this.sharpeRatio = sharpeRatio;
        this.maxDrawDown = maxDrawDown;
        this.stockSetVOS = stockSetVOS;
        this.profits = profits;
    }

    public StrategyDataVO(){}

    public double getAnnualReturn() {
        return annualReturn;
    }

    public void setAnnualReturn(double annualReturn){this.annualReturn = annualReturn;}

    public double getBasicAnnualReturn() {
        return basicAnnualReturn;
    }

    public void setBasicAnnualReturn(double basicAnnualReturn){this.basicAnnualReturn = basicAnnualReturn;}

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha){this.alpha = alpha;}

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta){this.beta = beta;}

    public double getSharpeRatio() {
        return sharpeRatio;
    }

    public void setSharpeRatio(double sharpeRatio){this.sharpeRatio = sharpeRatio;}

    public double getMaxDrawDown() {
        return maxDrawDown;
    }

    public void setMaxDrawDown(double maxDrawDown){this.maxDrawDown = maxDrawDown;}

    public List<StockSetVO> getStockSetVOS() {
        return stockSetVOS;
    }

    public void setStockSetPOS(List<StockSetVO> stockSetVOS){this.stockSetVOS = stockSetVOS;}

    public List<Double> getProfits() {
        return profits;
    }

    public void setProfits(List<Double> profits){this.profits = profits;}
}
