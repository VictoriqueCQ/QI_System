package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.DatePicker;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2017/3/3.
 */
public class ThermometerController implements Initializable {

    @FXML
    private Label date;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label TradingVolumnTodayTradingVolumnToday;

    @FXML
    private Label NumberOfTradingVolumnToday;

    @FXML
    private BarChart<String, Integer> barChart_1;

    @FXML
    private BarChart<String, Integer> barChart_2;

    @FXML
    private BarChart<String, Integer> barChart_3;

    @FXML
    private NumberAxis NumberOfStock_1;

    @FXML
    private NumberAxis NumberOfStock_2;

    @FXML
    private NumberAxis NumberOfStock_3;

    @FXML
    private CategoryAxis TypeOfStock_1;

    @FXML
    private CategoryAxis TypeOfStock_2;

    @FXML
    private CategoryAxis TypeOfStock_3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public ThermometerController(){}
}
