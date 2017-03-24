package ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Administrator on 2017/3/24.
 */
public class ReturnsModel {
    private final SimpleStringProperty period;

    private final SimpleStringProperty returns;

    private final SimpleStringProperty percent;

    public ReturnsModel(String period, String returns, String percent) {
        this.period = new SimpleStringProperty(period);
        this.returns = new SimpleStringProperty(returns);
        this.percent = new SimpleStringProperty(percent);
    }

    public SimpleStringProperty periodProperty(){
        return period;
    }

    private String getPeriod() {
        return period.get();
    }

    private void setPeriod(String period) {
        this.period.set(period);
    }

    public SimpleStringProperty returnsProperty(){
        return returns;
    }

    private String getReturns(){
        return returns.get();
    }

    private void setReturns(String returns){
        this.returns.set(returns);
    }

    public SimpleStringProperty percentProperty(){
        return percent;
    }

    private String getPercent(){
        return percent.get();
    }

    private void setPercent(String percent){
        this.percent.set(percent);
    }


}
