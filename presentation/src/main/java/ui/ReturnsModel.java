package ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Administrator on 2017/final SimpleStringProperty3/24.
 */
public class ReturnsModel {
    /*
    以下是超额收益率图表中的变量
     */
    //相对强弱计算周期
    private final SimpleStringProperty period;

    //超额收益
    private final SimpleStringProperty returns;

    //1年内收益
    private final SimpleStringProperty percent;



    public ReturnsModel() {
        this.period = new SimpleStringProperty();
        this.returns = new SimpleStringProperty();
        this.percent = new SimpleStringProperty();
    }



    public SimpleStringProperty periodProperty() {
        return period;
    }

    public String getPeriod() {
        return period.get();
    }

    public void setPeriod(String period) {
        this.period.set(period);
    }

    public SimpleStringProperty returnsProperty() {
        return returns;
    }

    public String getReturns() {
        return returns.get();
    }

    public void setReturns(String returns) {
        this.returns.set(returns);
    }

    public SimpleStringProperty percentProperty() {
        return percent;
    }

    public String getPercent() {
        return percent.get();
    }

    public void setPercent(String percent) {
        this.percent.set(percent);
    }


}
