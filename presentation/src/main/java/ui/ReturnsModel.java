package ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Administrator on 2017/3/24.
 */
public class ReturnsModel {
    private final SimpleStringProperty period;

    private final SimpleStringProperty returns;

    private final SimpleStringProperty percent;

    public ReturnsModel() {
        period = new SimpleStringProperty();
        returns = new SimpleStringProperty();
        percent = new SimpleStringProperty();
    }

    private String getPeriod() {
        return period.get();
    }

    private void setPeriod(String period) {
        this.period.set(period);
    }

    public SimpleStringProperty periodProperty(){
        return period;
    }

    private String getReturns(){
        return returns.get();
    }

    private void setReturns(String returns){
        this.returns.set(returns);
    }

    public SimpleStringProperty returnsProperty(){
        return returns;
    }

    private String getPercent(){
        return percent.get();
    }

    private void setPercent(String percent){
        this.percent.set(percent);
    }

    public SimpleStringProperty percentProperty(){
        return percent;
    }
}
