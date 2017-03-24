package ui.Controller;

import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setMain(Main main, Net net) {

        this.main = main;
        this.net = net;

    }
}
