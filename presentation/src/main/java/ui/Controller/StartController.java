package ui.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.Main;
import ui.Net;

/**
 * Created by xjwhhh on 2017/4/16.
 */
public class StartController {
    private Main main;
    private Net net;



    public void setMain(Main main, Net net, Stage stage){
        this.main=main;
        this.net=net;
        //3.5秒后关闭开场动画，进入主界面
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3.5), ev -> {
            stage.show();
            main.closeExtraStage();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}
