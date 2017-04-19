package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import quantour.vo.UserVO;
import ui.Main;

public class ClientOverviewController {
    private Main main;

    @FXML
    private SplitPane topSplitPane;

    @FXML
    private Button exitButton;

    @FXML
    private Button zoomoutButton;

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
    private Button exitLoginButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label telLabel;

    @FXML
    private void exit() {
        main.exitSystem();
    }

    @FXML
    private void zoomout(){
        main.zoomoutButton();
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
        main.gotoReturns();
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
     * 退出登录
     */
    @FXML
    private void exitLogin(){
        loginButton.setVisible(true);
        registerButton.setVisible(true);
        exitLoginButton.setVisible(false);
        comparsionButton.setDisable(true);
        returnsButton.setDisable(true);
        nameLabel.setText("   游客");
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
        returnsButton.setEffect(l);
    }

    public void setMain(Main main, boolean t, UserVO userVO) {
        this.main = main;
        setButtonText();
        this.gotoMarketCondition();
//        if(t){
//            nameLabel.setText("   姓名: "+userVO.getName());
//            loginButton.setVisible(false);
//            registerButton.setVisible(false);
//            exitLoginButton.setVisible(true);
//            comparsionButton.setDisable(false);
//            returnsButton.setDisable(false);
//        }
//        else{
//            nameLabel.setText("   游客访问");
//            loginButton.setVisible(true);
//            registerButton.setVisible(true);
//            exitLoginButton.setVisible(false);
//            comparsionButton.setDisable(true);
//            returnsButton.setDisable(true);
//        }
    }

}
