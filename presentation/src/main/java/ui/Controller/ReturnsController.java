package ui.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ui.Main;
import ui.Net;
import ui.ReturnsModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2017/3/24.
 */
public class ReturnsController implements Initializable {
    private Main main;

    private Net net;

    @FXML
    private TableView<ReturnsModel> tableView;

    @FXML
    private TableColumn<ReturnsModel, String> period;

    @FXML
    private TableColumn<ReturnsModel, String> returns;

    @FXML
    private TableColumn<ReturnsModel, String> percent;

    private ObservableList<ReturnsModel> data;

    @FXML
    private NumberAxis PercentNumber_1 = new NumberAxis();

    @FXML
    private NumberAxis PercentNumber_2 = new NumberAxis();

    @FXML
    private NumberAxis PeriodNumber_1 = new NumberAxis();

    @FXML
    private NumberAxis PeriodNumber_2 = new NumberAxis();

    @FXML
    private AreaChart<Number, Number> areaChart_1 = new AreaChart<Number, Number>(PeriodNumber_1, PercentNumber_1);

    @FXML
    private AreaChart<Number, Number> areaChart_2 = new AreaChart<Number, Number>(PeriodNumber_2, PercentNumber_2);

    @FXML
    private CategoryAxis ReturnsNumber = new CategoryAxis();

    @FXML
    private NumberAxis FrequencyNumber = new NumberAxis();

    @FXML
    private BarChart<String, Number> barChart = new BarChart<String, Number>(ReturnsNumber, FrequencyNumber);

    private void setTableView() {
        period.setCellValueFactory(celldata -> celldata.getValue().periodProperty());
        returns.setCellValueFactory(celldata -> celldata.getValue().returnsProperty());
        percent.setCellValueFactory(celldata -> celldata.getValue().percentProperty());

        data = FXCollections.observableArrayList(
                new ReturnsModel("2", "0.8%", "58%"),
                new ReturnsModel("4", "2.9%", "65%"),
                new ReturnsModel("6", "3.0%", "65%"),
                new ReturnsModel("8", "2.7%", "585"),
                new ReturnsModel("10", "2.5%", "60%"),
                new ReturnsModel("12", "1.2%", "53%"),
                new ReturnsModel("14", "0.8%", "53%"),
                new ReturnsModel("16", "0.2%", "48%"),
                new ReturnsModel("18", "-0.1%", "48%"),
                new ReturnsModel("20", "-0.1%", "48%"),
                new ReturnsModel("22", "-0.1%", "52%"),
                new ReturnsModel("24", "-0.7%", "47%"),
                new ReturnsModel("26", "-0.8%", "52%"),
                new ReturnsModel("28", "-1.1%", "44%"),
                new ReturnsModel("30", "-1.0%", "43%"),
                new ReturnsModel("32", "-1.0%", "44%"),
                new ReturnsModel("34", "-1.5%", "43%"),
                new ReturnsModel("36", "-1.5%", "38%"),
                new ReturnsModel("38", "-1.1%", "45%"),
                new ReturnsModel("40", "-1.7%", "43%"),
                new ReturnsModel("42", "-1.4%", "45%"),
                new ReturnsModel("44", "-1.6%", "47%"),
                new ReturnsModel("46", "-1.4%", "48%"),
                new ReturnsModel("48", "-1.0%", "45%"),
                new ReturnsModel("50", "-0.9%", "47%"),
                new ReturnsModel("52", "-0.5%", "45%"),
                new ReturnsModel("54", "-0.5%", "48%"),
                new ReturnsModel("56", "-0.1%", "49%"),
                new ReturnsModel("58", "-0.1%", "51%"),
                new ReturnsModel("60", "-0.2%", "51%")
        );

        tableView.getStyleClass().add("edge-to-edge");
        tableView.getStyleClass().add("noborder");
        tableView.setItems(data);
    }

    private void setAreaChart_1() {
        PeriodNumber_1.setForceZeroInRange(false);
        XYChart.Series series = new XYChart.Series();

        series.getData().add(new XYChart.Data<>(2, 0.8));
        series.getData().add(new XYChart.Data<>(4, 2.9));
        series.getData().add(new XYChart.Data<>(6, 3.0));
        series.getData().add(new XYChart.Data<>(8, 2.7));
        series.getData().add(new XYChart.Data<>(10, 2.5));
        series.getData().add(new XYChart.Data<>(12, 1.2));
        series.getData().add(new XYChart.Data<>(14, 0.8));
        series.getData().add(new XYChart.Data<>(16, 0.2));
        series.getData().add(new XYChart.Data<>(18, -0.1));
        series.getData().add(new XYChart.Data<>(20, -0.1));
        series.getData().add(new XYChart.Data<>(22, -0.1));
        series.getData().add(new XYChart.Data<>(24, -0.7));
        series.getData().add(new XYChart.Data<>(26, -0.8));
        series.getData().add(new XYChart.Data<>(28, -1.1));
        series.getData().add(new XYChart.Data<>(30, -1.0));
        series.getData().add(new XYChart.Data<>(32, -1.0));
        series.getData().add(new XYChart.Data<>(34, -1.5));
        series.getData().add(new XYChart.Data<>(36, -1.5));
        series.getData().add(new XYChart.Data<>(38, -1.1));
        series.getData().add(new XYChart.Data<>(40, -1.7));
        series.getData().add(new XYChart.Data<>(42, -1.4));
        series.getData().add(new XYChart.Data<>(44, -1.6));
        series.getData().add(new XYChart.Data<>(46, -1.4));
        series.getData().add(new XYChart.Data<>(48, -1.0));
        series.getData().add(new XYChart.Data<>(50, -0.9));
        series.getData().add(new XYChart.Data<>(52, -0.5));
        series.getData().add(new XYChart.Data<>(54, -0.5));
        series.getData().add(new XYChart.Data<>(56, -0.1));
        series.getData().add(new XYChart.Data<>(58, -0.1));
        series.getData().add(new XYChart.Data<>(60, -0.2));

        areaChart_1.setHorizontalZeroLineVisible(true);
        areaChart_1.getData().addAll(series);
    }

    private void setAreaChart_2() {
        PeriodNumber_2.setForceZeroInRange(false);
        XYChart.Series series = new XYChart.Series();

        series.getData().add(new XYChart.Data<>(2, 58));
        series.getData().add(new XYChart.Data<>(4, 65));
        series.getData().add(new XYChart.Data<>(6, 65));
        series.getData().add(new XYChart.Data<>(8, 58));
        series.getData().add(new XYChart.Data<>(10, 60));
        series.getData().add(new XYChart.Data<>(12, 53));
        series.getData().add(new XYChart.Data<>(14, 53));
        series.getData().add(new XYChart.Data<>(16, 48));
        series.getData().add(new XYChart.Data<>(18, 48));
        series.getData().add(new XYChart.Data<>(20, 48));
        series.getData().add(new XYChart.Data<>(22, 52));
        series.getData().add(new XYChart.Data<>(24, 47));
        series.getData().add(new XYChart.Data<>(26, 52));
        series.getData().add(new XYChart.Data<>(28, 44));
        series.getData().add(new XYChart.Data<>(30, 43));
        series.getData().add(new XYChart.Data<>(32, 44));
        series.getData().add(new XYChart.Data<>(34, 43));
        series.getData().add(new XYChart.Data<>(36, 38));
        series.getData().add(new XYChart.Data<>(38, 45));
        series.getData().add(new XYChart.Data<>(40, 43));
        series.getData().add(new XYChart.Data<>(42, 45));
        series.getData().add(new XYChart.Data<>(44, 47));
        series.getData().add(new XYChart.Data<>(46, 48));
        series.getData().add(new XYChart.Data<>(48, 45));
        series.getData().add(new XYChart.Data<>(50, 47));
        series.getData().add(new XYChart.Data<>(52, 45));
        series.getData().add(new XYChart.Data<>(54, 48));
        series.getData().add(new XYChart.Data<>(56, 49));
        series.getData().add(new XYChart.Data<>(58, 51));
        series.getData().add(new XYChart.Data<>(60, 51));

        areaChart_2.setHorizontalZeroLineVisible(true);
        areaChart_2.getData().addAll(series);
    }

    private void setBarChart(){
        barChart.setBarGap(3);
        barChart.setCategoryGap(20);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();

        series1.getData().add(new XYChart.Data<>("2.00%",52));
        series1.getData().add(new XYChart.Data<>("2.00%",-48));

        series2.getData().add(new XYChart.Data<>("4.00%",44));
        series2.getData().add(new XYChart.Data<>("4.00%",-40));

        barChart.getData().clear();
        barChart.layout();
        barChart.getData().addAll(series1, series2);
        barChart.setAnimated(false);
    }

    public void ReturnsController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMain(Main main, Net net) {
        setTableView();
        setAreaChart_1();
        setAreaChart_2();
        setBarChart();
        this.main = main;
        this.net = net;

    }
}
