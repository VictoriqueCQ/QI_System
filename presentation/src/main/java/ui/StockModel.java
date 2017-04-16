package ui;

import javafx.beans.property.SimpleIntegerProperty;
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

    private final SimpleStringProperty isChoosen;

    private final SimpleStringProperty rank;

    public StockModel() {
        name = new SimpleStringProperty();
        id = new SimpleStringProperty();
        minPrice = new SimpleStringProperty();
        maxPrice = new SimpleStringProperty();
        riseAndDown = new SimpleStringProperty();
        variance = new SimpleStringProperty();
        isChoosen = new SimpleStringProperty();
        rank = new SimpleStringProperty();
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

    public void setID(String id) {
        this.id.set(id);
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

    public String getVariance() {
        return variance.get();
    }

    public void setVariance(String variance) {
        this.variance.set(variance);
    }

    public SimpleStringProperty varianceProperty() {
        return variance;
    }

    public String getisChoosen() {
        return isChoosen.get();
    }

    public void setIsChoosen(String isChoosen) {
        this.isChoosen.set(isChoosen);
    }

    public SimpleStringProperty isChoosenProperty() {
        return isChoosen;
    }

    public String getRank() {
        return rank.get();
    }

    public void setRank(String rank) {
        this.rank.set(rank);
    }

    public SimpleStringProperty rankProperty() {
        return rank;
    }
}
