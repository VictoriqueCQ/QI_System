package ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import quantour.vo.UserVO;
import ui.Controller.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    Net net;

    // 主窗口
    private Stage stage;
    private Stage stage1 = new Stage(StageStyle.UNDECORATED);
    private Stage stage2 = new Stage(StageStyle.TRANSPARENT);
    private Stage stage3 = new Stage(StageStyle.UNDECORATED);
    private Stage stage4 = new Stage(StageStyle.UNDECORATED);


    // 内部窗口
    private SplitPane rootLayout;
    private Scene scene;
    private final double MINIMUM_WINDOW_WIDTH = 400.0;
    private final double MINIMUM_WINDOW_HEIGHT = 250.0;

    /**
     * 初始化界面
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        net = new Net();
        net.setMain(this);
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setResizable(false);
        net.setUpNet();
        UserVO userVO = new UserVO();
        this.gotoStart();
        this.gotoClientOverview(false, userVO);
    }

    public void gotoStart() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Start.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            insidePane.setPrefSize(600, 400);
            StartController controller = (StartController) fxmlLoader.getController();
            controller.setMain(this, net, stage);
            Scene scene = new Scene(insidePane);
            scene.setFill(null);
            stage4.setScene(scene);
            stage4.setAlwaysOnTop(true);
            stage4.centerOnScreen();
            stage4.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void lodaing() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Loading.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            insidePane.setPrefSize(800, 600);
            LoadingController controller = (LoadingController) fxmlLoader.getController();
            controller.setMain(this);
            Scene scene = new Scene(insidePane);
            scene.setFill(null);
            stage2.setScene(scene);
            stage2.setAlwaysOnTop(true);
            stage2.centerOnScreen();
            stage2.show();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4.5), ev -> {
                stage2.close();
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 登录界面
     */
    public void gotoLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Login.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            LoginController controller = (LoginController) fxmlLoader.getController();
            controller.setMain(this, net);
            stage1.setScene(new Scene(insidePane));
            stage1.setAlwaysOnTop(true);
            stage1.centerOnScreen();
            stage1.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 跳转到注册界面
     */
    public void gotoRegist() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Regist.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            RegistController registController = (RegistController) fxmlLoader.getController();
            registController.setMain(this, net);
            stage1.setScene(new Scene(insidePane));
            stage1.setAlwaysOnTop(true);
            stage1.centerOnScreen();
            stage1.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 跳转到更改密码界面
     */
    public void gotoChangePassword() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/ChangePassword.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            ChangePasswordController registController = (ChangePasswordController) fxmlLoader.getController();
            registController.setMain(this, net);
            stage1.setScene(new Scene(insidePane));
            stage1.setAlwaysOnTop(true);
            stage1.centerOnScreen();
            stage1.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    /**
     * 跳转到客户界面
     */
    public void gotoClientOverview(boolean t, UserVO userVO) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/ClientOverview.fxml"));
            rootLayout = (SplitPane) fxmlLoader.load();
            rootLayout.setPrefSize(1200, 800);
            rootLayout.setDividerPositions(0.15f);
            ClientOverviewController controller = (ClientOverviewController) fxmlLoader.getController();
            controller.setMain(this, t, userVO);
            scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.centerOnScreen();
//            stage.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 跳转到市场情况界面
     */
    public void gotoMarketCondition() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/MarketCondition.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            insidePane.setPrefSize(1200, 680);
            rootLayout.getItems().set(1, insidePane);
            MarketConditionController controller = (MarketConditionController) fxmlLoader.getController();
            controller.setMain(this, net);
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 跳转到K线图界面
     */
    public void gotoCandlestickChart() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/CandlestickChart.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            insidePane.setPrefSize(1200, 680);
            rootLayout.getItems().set(1, insidePane);
            CandlestickChartController controller = (CandlestickChartController) fxmlLoader.getController();
            controller.setMain(this, net);
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 跳转到K线图界面
     */
    public void gotoCandlestickChart(String name, String code, LocalDate startdate, LocalDate enddate) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/CandlestickChart.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            insidePane.setPrefSize(1200, 680);
            rootLayout.getItems().set(1, insidePane);
            CandlestickChartController controller = (CandlestickChartController) fxmlLoader.getController();
            controller.setMain(this, net, name, code, startdate, enddate);
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 跳转到股票比较功能界面
     */
    public void gotoCompareFunction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Contrast.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            insidePane.setPrefSize(1200, 680);
            rootLayout.getItems().set(1, insidePane);
            ContrastController controller = (ContrastController) fxmlLoader.getController();
            controller.setMain(this, net);
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 跳转到市场温度计界面
     */
    public void gotoThermometer() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Thermometer.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            insidePane.setPrefSize(1200, 680);
            rootLayout.getItems().set(1, insidePane);
            ThermometerController controller = (ThermometerController) fxmlLoader.getController();
            controller.setMain(this, net);
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 跳转到策略界面
     */
    public void gotoReturns() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Returns.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            insidePane.setPrefSize(1200, 680);
            rootLayout.getItems().set(1, insidePane);
            ReturnsController controller = (ReturnsController) fxmlLoader.getController();
            controller.setMain(this, net);
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 跳转到股票选择界面
     */
    public void gotoSelectStock(ReturnsController returnsController, ArrayList<String> stockCodeList) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/SelectStock.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            SelectStockController controller = (SelectStockController) fxmlLoader.getController();
            controller.setMain(this, net, returnsController, stockCodeList);
            stage3.setScene(new Scene(insidePane));
            stage3.centerOnScreen();
            stage3.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 退出系统
     */
    public void exitSystem() {
        stage.close();
    }

    /**
     * 退出弹窗
     */
    public void exitSystem1() {
        stage3.close();
    }

    /**
     * 系统缩小至任务栏
     */
    public void zoomoutButton() {
        stage.setIconified(true);
    }

    /**
     * 弹窗缩小至任务栏
     */
    public void zoomoutButton1() {
        stage3.setIconified(true);
    }

    /**
     * 关闭弹窗
     */
    public void closeExtraStage() {
        if (stage1 != null && stage1.isShowing()) {
            stage1.hide();
        }
        if (stage2 != null && stage2.isShowing()) {
            stage2.hide();
        }
        if (stage3 != null && stage3.isShowing()) {
            stage3.hide();
        }
    }

    public void closeStage4() {
        if (stage4 != null && stage4.isShowing()) {
            stage4.hide();
        }
    }
//
//    /**
//     *  界面跳转
//     * @param fxml
//     * @return
//     * @throws Exception
//     */
//    private Initializable replaceSceneContent(String fxml) throws Exception {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getResource(fxml));
//        AnchorPane pane = (AnchorPane) loader.load();
//        this.scene = new Scene(pane);
//        stage1.setScene(scene);
//        stage1.sizeToScene();
//        stage1.centerOnScreen();
//        stage1.setResizable(false);
//        return (Initializable) loader.getController();
//    }

    public static void main(String[] args) {
        launch(args);
    }
}

