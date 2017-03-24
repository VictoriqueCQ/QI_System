package ui.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

    private void setTableView(){
        period.setCellValueFactory(celldata->celldata.getValue().periodProperty());
        returns.setCellValueFactory(celldata->celldata.getValue().returnsProperty());
        percent.setCellValueFactory(celldata->celldata.getValue().percentProperty());

        data = FXCollections.observableArrayList(
            new ReturnsModel()
        );

        tableView.getStyleClass().add("edge-to-edge");
        tableView.getStyleClass().add("noborder");
        tableView.setItems(data);
    }

    private void setAreaChart_1(){
        PeriodNumber_1.setForceZeroInRange(false);
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>());
        areaChart_1.setHorizontalZeroLineVisible(true);
    }

    private void setAreaChart_2(){
        PeriodNumber_2.setForceZeroInRange(false);
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>());
        areaChart_2.setHorizontalZeroLineVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setMain(Main main, Net net) {
        setTableView();
        setAreaChart_1();
        setAreaChart_2();
        this.main = main;
        this.net = net;

    }
}
