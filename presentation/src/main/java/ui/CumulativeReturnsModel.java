package ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Administrator on 2017/3/26.
 */
public class CumulativeReturnsModel {
    /*
  以下是累计收益率图表中的变量
   */
    //年化收益率
    private final SimpleStringProperty yearReturns;

    //基准年化收益率
    private final SimpleStringProperty standardYearReturns;

    //阿尔法
    private final SimpleStringProperty alpha;

    //贝塔
    private final SimpleStringProperty beta;

    //夏普比率
    private final SimpleStringProperty sharp;

    //最大回撤
    private final SimpleStringProperty retreats;


    public CumulativeReturnsModel(String yearReturns, String standardYearReturns, String alpha, String beta, String sharp, String retreats) {
        this.yearReturns = new SimpleStringProperty(yearReturns);
        this.standardYearReturns = new SimpleStringProperty(standardYearReturns);
        this.alpha = new SimpleStringProperty(alpha);
        this.beta = new SimpleStringProperty(beta);
        this.sharp = new SimpleStringProperty(sharp);
        this.retreats = new SimpleStringProperty(retreats);

    }

    public SimpleStringProperty yearReturnsProperty() {
        return yearReturns;
    }

    public String getYearReturns() {
        return yearReturns.get();
    }

    public void setYearReturns(String yearReturns) {
        this.yearReturns.set(yearReturns);
    }

    public SimpleStringProperty standardYearReturnsProperty() {
        return standardYearReturns;
    }

    public String getStandardReturns() {
        return standardYearReturns.get();
    }

    public void setStandardYearReturns(String standardYearReturns) {
        this.standardYearReturns.set(standardYearReturns);
    }

    public SimpleStringProperty alphaProperty() {
        return alpha;
    }

    public String getAlpha() {
        return alpha.get();
    }

    public void setAlpha(String alpha) {
        this.alpha.set(alpha);
    }

    public SimpleStringProperty betaProperty() {
        return beta;
    }

    public String getBeta() {
        return beta.get();
    }

    public void setBeta(String beta) {
        this.beta.set(beta);
    }

    public SimpleStringProperty sharpProperty() {
        return sharp;
    }

    public String getSharp() {
        return sharp.get();
    }

    public void setSharp(String sharp) {
        this.sharp.set(sharp);
    }

    public SimpleStringProperty retreatsProperty() {
        return retreats;
    }

    public String getRetreats() {
        return retreats.get();
    }

    public void setRetreats(String retreats) {
        this.retreats.set(retreats);
    }

}
