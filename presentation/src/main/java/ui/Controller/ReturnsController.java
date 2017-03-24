package ui.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ui.Main;
import ui.Net;

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
    private TableColumn<ReturnsModel, String> column_1;

    @FXML
    private TableColumn<ReturnsModel, String> column_2;

    @FXML
    private TableColumn<ReturnsModel, String> column_3;

    @FXML
    private AreaChart areaChart_1;

    @FXML
    private AreaChart areaChart_2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setMain(Main main, Net net) {

        this.main = main;
        this.net = net;

    }
}
