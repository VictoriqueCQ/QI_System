package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2017/3/3.
 */
public class ThermometerController implements Initializable {
    private Main main;

    @FXML
    private DatePicker datepicker;

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

    @FXML
    private BarChart<String, Number> barChart_1;

    @FXML
    private BarChart<String, Number> barChart_2;

    @FXML
    private BarChart<String, Number> barChart_3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //以下都是模拟数据
    private void setBarChart_1(){
        TypeOfStock_1 = new CategoryAxis();
        NumberOfStock_1 = new NumberAxis();
        barChart_1 = new BarChart<String, Number>(TypeOfStock_1, NumberOfStock_1);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        System.out.println("图表1");
        series1.getData().add(new XYChart.Data<>("涨停股票数",30));
        System.out.println("图表2");
        series1.getData().add(new XYChart.Data<>("跌停股票数",10));
        System.out.println("图表3");
        barChart_1.getData().add(series1);
        System.out.println("图表4");
    }

    private void setBarChart_2(){
        TypeOfStock_2 = new CategoryAxis();
        NumberOfStock_2 = new NumberAxis();
        barChart_2 = new BarChart<String, Number>(TypeOfStock_2, NumberOfStock_2);
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.getData().add(new XYChart.Data<>("涨幅超过5%股票数", 60));
        series2.getData().add(new XYChart.Data<>("跌幅超过5%股票数", 20));

        barChart_2.getData().add(series2);
    }

    private void setBarChart_3(){
        TypeOfStock_3 = new CategoryAxis();
        NumberOfStock_3 = new NumberAxis();
        barChart_3 = new BarChart<String, Number>(TypeOfStock_3, NumberOfStock_3);
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.getData().add(new XYChart.Data<>("日增幅超过5%股票数", 45));
        series3.getData().add(new XYChart.Data<>("日跌幅超过5%股票数", 15));

        barChart_3.getData().add(series3);
    }

    public ThermometerController(){}

    public void setMain(Main main) {
        this.main = main;
        setBarChart_1();
        setBarChart_2();
        setBarChart_3();
    }
}
