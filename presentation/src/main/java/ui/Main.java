package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.Controller.CandlestickChartController;
import ui.Controller.ClientOverviewController;
import ui.Controller.ContrastController;
import ui.Controller.ThermometerController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    Net net;
    // 主窗口
    private Stage stage;


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
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setResizable(false);

        net.setupNet();
        this.gotoClientOverview();

    }


    /**
     * 跳转到客户界面
     */
    public void gotoClientOverview() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/ClientOverview.fxml"));
            rootLayout = (SplitPane) fxmlLoader.load();
            rootLayout.setPrefSize(1200, 800);
            rootLayout.setDividerPositions(0.2f);
            ClientOverviewController controller = (ClientOverviewController) fxmlLoader.getController();
            controller.setMain(this);
            scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
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
            insidePane.setPrefSize(1200, 640);
            rootLayout.getItems().set(1, insidePane);
            CandlestickChartController controller = (CandlestickChartController) fxmlLoader.getController();
            controller.setMain(this,net);
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
            insidePane.setPrefSize(1200, 640);
            rootLayout.getItems().set(1, insidePane);
            ContrastController controller = (ContrastController) fxmlLoader.getController();
            controller.setMain(this);
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
            insidePane.setPrefSize(1200, 640);
            rootLayout.getItems().set(1, insidePane);
            ThermometerController controller = (ThermometerController) fxmlLoader.getController();
            controller.setMain(this);
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

    public static void main(String[] args) {
        launch(args);
    }
}

