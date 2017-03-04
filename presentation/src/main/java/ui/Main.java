package ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    // 主窗口
    private Stage stage;



    // 内部窗口
    private SplitPane rootLayout;

    private Scene scene;
    private final double MINIMUM_WINDOW_WIDTH = 400.0;
    private final double MINIMUM_WINDOW_HEIGHT = 250.0;

    /**
     * 跳转到客户界面
     */
    public void gotoClientOverview() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/ClientOverview.fxml"));
            rootLayout = (SplitPane) fxmlLoader.load();
            rootLayout.setPrefSize(1200, 800);
//            rootLayout.setOrientation();
            rootLayout.setDividerPositions(0.2f);
            ClientOverviewController controller = (ClientOverviewController) fxmlLoader.getController();
            controller.setMain(this);
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setResizable(false);
        this.gotoClientOverview();
    }

    /**
     * 退出系统
     */
    public void exitSystem() {
        stage.close();
    }

}

