package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import quantour.vo.UserVO;
import ui.Controller.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class  Main extends Application {

    Net net;

    // 主窗口
    private Stage stage;
    private Stage stage1=new Stage(StageStyle.UNDECORATED);


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
        net.setUpNet();
        UserVO userVO=new UserVO();
        this.gotoClientOverview(false,userVO);
//        this.lodaing();
//        net.actionPerformed("GET");
//        String json = net.run();
//        JsonUtil jsonUtil = new JsonUtil();
//        NameVO nameVO1 = new NameVO();
//        NameVO nameVO= (NameVO) jsonUtil.JSONToObj(json, nameVO1.getClass());
//        File file=new File("C:\\Users\\xjwhh\\Desktop\\stock.txt");
//        if(!file.exists())
//            file.createNewFile();
//        FileWriter fw = new FileWriter(file, true);
//        BufferedWriter bw = new BufferedWriter(fw);
//
//
//        for(int i=0;i<nameVO.getCodes().size();i++){
//            int code=nameVO.getCodes().get(i);
//            String code1=String.valueOf(code);
//            char[] code2=code1.toCharArray();
//            int j=code2.length;
//            for(int m=0;m<6-j;m++){
//                bw.write("0");
//            }
//            bw.write(nameVO.getCodes().get(i)+System.getProperty("line.separator"));
//            bw.flush();
//        }
//
//        bw.close();
//        System.out.print("success");

}

    public void lodaing(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Loading.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            insidePane.setPrefSize(800,600);
            LoadingController controller = (LoadingController) fxmlLoader.getController();
            controller.setMain(this);
            Stage stage2=new Stage(StageStyle.TRANSPARENT);
            stage2.setScene(new Scene(insidePane));
            stage2.setAlwaysOnTop(true);
            stage2.centerOnScreen();
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.show();
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
            controller.setMain(this,net);
            stage1.setScene(new Scene(insidePane));
            stage1.setAlwaysOnTop(true);
            stage1.centerOnScreen();
            stage1.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     *  跳转到注册界面
     */
    public void gotoRegist() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/Regist.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            RegistController registController = (RegistController) fxmlLoader.getController();
            registController.setMain(this,net);
            stage1.setScene(new Scene(insidePane));
            stage1.setAlwaysOnTop(true);
            stage1.centerOnScreen();
            stage1.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     *  跳转到更改密码界面
     */
    public void gotoChangePassword() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/ChangePassword.fxml"));
            AnchorPane insidePane = (AnchorPane) fxmlLoader.load();
            ChangePasswordController registController = (ChangePasswordController) fxmlLoader.getController();
            registController.setMain(this,net);
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
            controller.setMain(this,t,userVO);
            scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 跳转到市场情况界面
     */
    public void gotoMarketCondition(){
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

    public void gotoReturns(){
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
     * 退出系统
     */
    public void exitSystem() {
        stage.close();
    }

    /**
     *  关闭弹窗
     */
    public void closeExtraStage() {
        if (stage1!=null&&stage1.isShowing()) {
            stage1.hide();
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

