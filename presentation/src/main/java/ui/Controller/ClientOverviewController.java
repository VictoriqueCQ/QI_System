package ui.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import ui.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientOverviewController implements Initializable {
    private Main main;

    @FXML
    private SplitPane topSplitPane;

    @FXML
    private Button exitButton;

    @FXML
    private Button imagekButton;

    @FXML
    private Button comparsionButton;

    @FXML
    private Button thermometerButton;

    @FXML
    private Button marketConditionButton;

    @FXML
    private Button returnsButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label telLabel;

    @FXML
    private void exit() {
        main.exitSystem();
    }

    @FXML
    private void gotoMarketCondition(){
        main.gotoMarketCondition();
    }

    @FXML
    private void gotoCandlestickChar() {
        main.gotoCandlestickChart();
    }

    @FXML
    private void gotoComparsion()  {
        main.gotoCompareFunction();
    }

    @FXML
    private void gotoThermometer() {
        main.gotoThermometer();
    }

    @FXML
    private void gotoReturns(){

    }

    @FXML
    private void gotoLogin() {
        main.gotoLogin();
    }

    @FXML
    private void gotoRegist() {
        main.gotoRegist();
    }

    /**
     * 设置按钮样式
     */
    public void setButtonText() {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0f);
        Lighting l = new Lighting();
        l.setLight(light);
        l.setSurfaceScale(5.0f);
        imagekButton.setEffect(l);
        comparsionButton.setEffect(l);
        thermometerButton.setEffect(l);
        marketConditionButton.setEffect(l);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public ClientOverviewController() {
    }

    public void setMain(Main main) {
        this.main = main;
        setButtonText();
        this.gotoMarketCondition();
    }

}
