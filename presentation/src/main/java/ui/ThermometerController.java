package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

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
}
