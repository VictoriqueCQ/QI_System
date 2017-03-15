package ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Administrator on 2017/3/14.
 */
public class AllStockConditionModel {
    private final SimpleStringProperty StockType;
    private final SimpleStringProperty StockNumber;

    public AllStockConditionModel(String type, String number) {
        this.StockType = new SimpleStringProperty(type);
        this.StockNumber = new SimpleStringProperty(number);
    }

    public SimpleStringProperty StockTypeProperty() {
        return StockType;
    }

    public String getStockType() {
        return StockType.get();
    }

    public void setStockType(String stockType) {
        this.StockType.set(stockType);
    }

    public SimpleStringProperty StockNumberProperty() {
        return StockNumber;
    }

    public String getNumber() {
        return StockNumber.get();
    }

    public void setNumber(String number) {
        this.StockNumber.set(number);
    }
}
