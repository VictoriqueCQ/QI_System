package ui;/**
 * Created by chenyuyan on 17/3/3.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;

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
//    private Map<String, XYChart.Series<String, Number>> seriesMap;

   /* @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;*/


//    private Map<String, XYChart.Series<Number, Number>> seriesMap;
@FXML
private void updateEndTimeDatePicker(){
    final Callback<DatePicker, DateCell> dayCellFactory1 =
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item.isBefore(
                                    startDate.getValue().plusDays(1))
                                    ){
                                setDisable(true);
                                setStyle("-fx-background-color: #000000;");
                            }
                            if (item.isAfter(
                                    LocalDate.of(2014,4,30))
                                    ){
                                setDisable(true);
                                setStyle("-fx-background-color: #000000;");
                            }
                        }
                    };

                }
            };
    endDate.setDayCellFactory(dayCellFactory1);
}

    @FXML
    private void updateStartTimeDatePicker(){
        final Callback<DatePicker, DateCell> dayCellFactory1 =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isAfter(
                                        endDate.getValue().minusDays(1))
                                        ){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #000000;");
                                }
                                if (item.isBefore(
                                        LocalDate.of(2005,2,1))
                                        ){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #000000;");
                                }
                            }
                        };

                    }
                };
        startDate.setDayCellFactory(dayCellFactory1);
    }


    private void setDatePicker(){
        startDate.setValue(LocalDate.of(2005,2,1));
        final Callback<DatePicker, DateCell> dayCellFactory1 =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        LocalDate.of(2005,2,1))
                                        ){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #000000;");
                                }
                            }
                        };
                    }
                };
        startDate.setDayCellFactory(dayCellFactory1);
        final Callback<DatePicker, DateCell> dayCellFactory2 =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isAfter(
                                        LocalDate.of(2014,4,30))
                                        ){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #000000;");
                                }
                            }
                        };
                    }
                };
        endDate.setDayCellFactory(dayCellFactory2);
        endDate.setValue(LocalDate.of(2014,4,30));

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public void setMain(Main main) {
//        setCompare();
        this.main = main;
        this.setDatePicker();

    }

    public void setCompare(){

//        xAxis.setLabel("Number of Month");
        //creating the chart
//        final LineChart<Number,Number> lineChart =
//                new LineChart<Number,Number>(xAxis,yAxis);

//        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series

        setTableContrast();
        setClosePriceLine();
        setIncomeLine();
        setIncomeLine2();



//        seriesMap.put(stock.getStockCode(), series);
    }

    public void setTableContrast(){



    }
    public void setClosePriceLine(){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("股票一");
        //populating the series with data
        series.getData().add(new XYChart.Data("a", 23));
        series.getData().add(new XYChart.Data("b", 14));
        series.getData().add(new XYChart.Data("c", 15));
        series.getData().add(new XYChart.Data("d", 24));
        series.getData().add(new XYChart.Data("e", 34));
        series.getData().add(new XYChart.Data("f", 36));
        series.getData().add(new XYChart.Data("g", 22));
        series.getData().add(new XYChart.Data("h", 45));
        series.getData().add(new XYChart.Data("i", 43));
        series.getData().add(new XYChart.Data("j", 17));
        series.getData().add(new XYChart.Data("k", 29));
        series.getData().add(new XYChart.Data("l", 25));
        closePriceLine.getData().add(series);
        System.out.print("aaaaaaa");
    }
    public void setIncomeLine(){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("股票一");
        //populating the series with data
        series.getData().add(new XYChart.Data("a", 23));
        series.getData().add(new XYChart.Data("b", 14));
        series.getData().add(new XYChart.Data("c", 15));
        series.getData().add(new XYChart.Data("d", 24));
        series.getData().add(new XYChart.Data("e", 34));
        series.getData().add(new XYChart.Data("f", 36));
        series.getData().add(new XYChart.Data("g", 22));
        series.getData().add(new XYChart.Data("h", 45));
        series.getData().add(new XYChart.Data("i", 43));
        series.getData().add(new XYChart.Data("j", 17));
        series.getData().add(new XYChart.Data("k", 29));
        series.getData().add(new XYChart.Data("l", 25));
        IncomeLine.getData().add(series);
        System.out.print("aaaaaaa");
    }
    public void setIncomeLine2(){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("股票一");
        //populating the series with data
        series.getData().add(new XYChart.Data("a", 23));
        series.getData().add(new XYChart.Data("b", 14));
        series.getData().add(new XYChart.Data("c", 15));
        series.getData().add(new XYChart.Data("d", 24));
        series.getData().add(new XYChart.Data("e", 34));
        series.getData().add(new XYChart.Data("f", 36));
        series.getData().add(new XYChart.Data("g", 22));
        series.getData().add(new XYChart.Data("h", 45));
        series.getData().add(new XYChart.Data("i", 43));
        series.getData().add(new XYChart.Data("j", 17));
        series.getData().add(new XYChart.Data("k", 29));
        series.getData().add(new XYChart.Data("l", 25));
        IncomeLine2.getData().add(series);
        System.out.print("aaaaaaa");

    }
}
