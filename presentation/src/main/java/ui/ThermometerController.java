package ui;

import javafx.animation.TranslateTransitionBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import static javax.swing.text.html.CSS.Attribute.BACKGROUND_COLOR;

/**
 * Created by Administrator on 2017/3/3.
 */
public class ThermometerController implements Initializable {
    private Main main;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Button searchButton;


    @FXML
    private TextArea volumnText;

    @FXML
    private NumberAxis NumberOfStock_1 = new NumberAxis();

    @FXML
    private NumberAxis NumberOfStock_2 = new NumberAxis();

    @FXML
    private NumberAxis NumberOfStock_3 = new NumberAxis();

    @FXML
    private CategoryAxis TypeOfStock_1 = new CategoryAxis();

    @FXML
    private CategoryAxis TypeOfStock_2 = new CategoryAxis();

    @FXML
    private CategoryAxis TypeOfStock_3 = new CategoryAxis();

    @FXML
    private BarChart<String, Number> barChart_1 = new BarChart<String, Number>(TypeOfStock_1, NumberOfStock_1);

    @FXML
    private BarChart<String, Number> barChart_2 = new BarChart<String, Number>(TypeOfStock_2, NumberOfStock_2);

    @FXML
    private BarChart<String, Number> barChart_3 = new BarChart<String, Number>(TypeOfStock_3, NumberOfStock_3);

    PieChart.Data d1 = new PieChart.Data("涨停股票",30);
    PieChart.Data d2 = new PieChart.Data("涨幅超过5%股票",80);
    PieChart.Data d3 = new PieChart.Data("涨跌幅小于5股票",260);
    PieChart.Data d4 = new PieChart.Data("跌幅超过5%股票",20);
    PieChart.Data d5 = new PieChart.Data("跌停股票",10);

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(d1,d2,d3,d4,d5);

    @FXML
    private PieChart pieChart = new PieChart();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //以下都是模拟数据
    private void setBarChart_1(){
        barChart_1.setCategoryGap(90);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("涨跌停股票情况");
        series1.getData().add(new XYChart.Data<>("涨停股票数",30));
        series1.getData().add(new XYChart.Data<>("跌停股票数",10));
        barChart_1.getData().addAll(series1);
    }

    private void setBarChart_2(){
        barChart_2.setCategoryGap(90);
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("涨跌幅超过5%股票情况");
        series2.getData().add(new XYChart.Data<>("涨幅超过5%股票数", 80));
        series2.getData().add(new XYChart.Data<>("跌幅超过5%股票数", 20));

        barChart_2.getData().add(series2);
    }

    private void setBarChart_3(){
        barChart_3.setCategoryGap(90);
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("日增幅超过5%股票情况");
        series3.getData().add(new XYChart.Data<>("日增幅超过5%股票数", 50));
        series3.getData().add(new XYChart.Data<>("日跌幅超过5%股票数", 15));

        barChart_3.getData().add(series3);
    }

    public void setPieChart(){
//        显示区域所占比例，暂时尚未时间如何添加
        Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");
        pieChart.getData().stream().forEach((data) -> {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    (MouseEvent e) -> {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf(data.getPieValue())
                                + "%");
                    });
        });

        //当鼠标移入饼图块时，该块位置发生偏移，鼠标离开后，该块回复到原来位置。暂时尚未实现效果
//        d1.getNode().setOnMouseEntered(new MouseHoverAnimation(d1, pieChart));
//        d1.getNode().setOnMouseExited(new MouseExitAnimation());
//
//        d2.getNode().setOnMouseEntered(new MouseHoverAnimation(d2, pieChart));
//        d2.getNode().setOnMouseExited(new MouseExitAnimation());
//
//        d3.getNode().setOnMouseEntered(new MouseHoverAnimation(d3, pieChart));
//        d3.getNode().setOnMouseExited(new MouseExitAnimation());
//
//        d4.getNode().setOnMouseEntered(new MouseHoverAnimation(d4, pieChart));
//        d4.getNode().setOnMouseExited(new MouseExitAnimation());
//
//        d5.getNode().setOnMouseEntered(new MouseHoverAnimation(d5, pieChart));
//        d5.getNode().setOnMouseExited(new MouseExitAnimation());

        pieChart.getData().setAll(pieChartData);

    }

    public void setDatepicker(){
        datepicker.setOnAction((ActionEvent e)->{
            
        });
    }


    public void setSearchButton(){

        searchButton.getStyleClass().add("button");
        //处理Action
        searchButton.setOnAction((ActionEvent e)->{

            System.out.println("Search the data and show the volumn.");
            volumnText.setPromptText("100000(瞎写的)");


        });

        setBarChart_1();
        setBarChart_2();
        setBarChart_3();
        setPieChart();


        //当鼠标进入按钮时添加阴影特效
        DropShadow shadow = new DropShadow();
        searchButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            searchButton.setEffect(shadow);
        });

        //当鼠标离开按钮时移除阴影效果
        searchButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)->{
            searchButton.setEffect(null);
        });
    }

    public ThermometerController(){

    }

    public void setMain(Main main) {


        setSearchButton();
        this.main = main;
    }

    static class MouseHoverAnimation implements EventHandler {
        static final Duration ANIMATION_DURATION = new Duration(500);
        static final double ANIMATION_DISTANCE = 0.15;
        private double cos;
        private double sin;
        private PieChart chart;

        public MouseHoverAnimation(PieChart.Data d, PieChart chart) {
            this.chart = chart;
            double start = 0;
            double angle = calcAngle(d);
            for (PieChart.Data tmp : chart.getData()) {
                if (tmp == d) {
                    break;
                }
                start += calcAngle(tmp);
            }

            cos = Math.cos(Math.toRadians(start + angle / 2));
            sin = Math.sin(Math.toRadians(start + angle / 2));
        }

        public void handle(MouseEvent arg0) {
            Node n = (Node) arg0.getSource();
            double minX = Double.MAX_VALUE;
            double maxX = Double.MAX_VALUE * -1;

            for (PieChart.Data d : chart.getData()) {
                minX = Math
                        .min(minX, d.getNode().getBoundsInParent().getMinX());
                maxX = Math
                        .max(maxX, d.getNode().getBoundsInParent().getMaxX());
            }

            double radius = maxX - minX;
            System.out.println("cos:" + cos);
            System.out.println("sin" + sin);
            TranslateTransitionBuilder.create()
                    .toX((radius * ANIMATION_DISTANCE) * cos)
                    .toY((radius * ANIMATION_DISTANCE) * (-sin))
                    .duration(ANIMATION_DURATION).node(n).build().play();
        }

        private static double calcAngle(PieChart.Data d) {
            double total = 0;
            for (PieChart.Data tmp : d.getChart().getData()) {
                total += tmp.getPieValue();
            }
            return 360 * (d.getPieValue() / total);
        }

        @Override
        public void handle(Event event) {
            // TODO Auto-generated method stub

        }
    }

    static class MouseExitAnimation implements EventHandler {
        public void handle(MouseEvent event) {
            TranslateTransitionBuilder.create().toX(0).toY(0)
                    .duration(new Duration(500)).node((Node) event.getSource())
                    .build().play();
        }

        @Override
        public void handle(Event event) {
            // TODO Auto-generated method stub

        }
    }
}
