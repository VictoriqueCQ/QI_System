package ui;/**
 * Created by chenyuyan on 17/3/3.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;

public class ContrastController extends Application {
    private Main main;

    @FXML
    private ChoiceBox choiceBox1;

    @FXML
    private  ChoiceBox choiceBox2;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;



    @FXML
    private AnchorPane showPane;

    @FXML
    private LineChart riseAndDownLine;


    @FXML
    private LineChart closePriceLine;

    @FXML
    private LineChart IncomeLine;

    @FXML
    private LineChart IncomeLine2;


    @FXML
    private Button ensure;

    @FXML
    private Button delete;

   /* @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;*/


//    private Map<String, XYChart.Series<Number, Number>> seriesMap;




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public void setMain(Main main) {
        setCompare();
        this.main = main;

    }

    public void setCompare(){
          NumberAxis xAxis = new NumberAxis();
          NumberAxis yAxis = new NumberAxis();
          xAxis.setLabel("Numberx");
          yAxis.setLabel("Numbery");
//        xAxis.setLabel("Number of Month");
        //creating the chart
//        final LineChart<Number,Number> lineChart =
//                new LineChart<Number,Number>(xAxis,yAxis);

//        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
         closePriceLine =
                new LineChart<Number,Number>(xAxis,yAxis);

        closePriceLine.getData().add(series);
        System.out.print("aaaaaaa");


//        seriesMap.put(stock.getStockCode(), series);
    }


}
