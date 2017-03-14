package ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by chenyuyan on 12/3/17.
 */
public class StockModel {
    private final SimpleStringProperty name;

    private final SimpleStringProperty id;

    private final SimpleStringProperty minPrice;

    private final SimpleStringProperty maxPrice;

    private final SimpleStringProperty riseAndDown;

    private final SimpleStringProperty variance;

    public StockModel() {
        name = new SimpleStringProperty();
        id = new SimpleStringProperty();
        minPrice = new SimpleStringProperty();
        maxPrice = new SimpleStringProperty();
        riseAndDown = new SimpleStringProperty();
        variance = new SimpleStringProperty();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getID() {
        return id.get();
    }

    public void setID(int id) {
        this.id.set(String.valueOf(id));
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getMinPrice() {
        return minPrice.get();
    }

    public void setMinPrice(double minPrice) {
        this.minPrice.set(String.valueOf(minPrice));
    }

    public SimpleStringProperty minPriceProperty() {
        return minPrice;
    }

    public String getMaxPrice() {
        return maxPrice.get();
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice.set(String.valueOf(maxPrice));
    }

    public SimpleStringProperty maxPriceProperty() {
        return maxPrice;
    }

    public String getRiseAndDown() {
        return riseAndDown.get();
    }

    public void setRiseAndDown(String riseAndDown) {
        this.riseAndDown.set(String.valueOf(riseAndDown));
    }

    public SimpleStringProperty riseAndDownProperty() {
        return riseAndDown;
    }

    public String getVariance(){return variance.get();}

    public void setVariance(double variance){this.variance.set(String.valueOf(variance));}

    public SimpleStringProperty varianceProperty(){return variance;}
}
