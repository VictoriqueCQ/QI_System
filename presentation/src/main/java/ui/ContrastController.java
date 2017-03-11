package ui;/**
 * Created by chenyuyan on 17/3/3.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import quantour.vo.StockSearchConditionVO;
import quantour.vo.StockVO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class ContrastController extends Application {
    private Main main;

    private List<StockVO> allStock ;//所有股票
    @FXML
    private TextArea addName1;

    @FXML
    private  TextArea addName2;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;



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

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

//    private Map<String, XYChart.Series<String, Number>> seriesMap;

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
//        setCompare();
        this.main = main;

    }

    public void addCompare(){

//        xAxis.setLabel("Number of Month");
        //creating the chart
//        final LineChart<Number,Number> lineChart =
//                new LineChart<Number,Number>(xAxis,yAxis);

//        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
//        String stockName =
        String stockName1 = addName1.getText();
        String stockName2 = addName2.getText();
        boolean b1 = Judge(stockName1);
        boolean b2 = Judge(stockName2);
        if(stockName1==stockName2){
            ExceptionTips repeat = new ExceptionTips("股票名重复");
        }else if(b1==false||b2==false){
            ExceptionTips illegal = new ExceptionTips("输入股票名不存在");


        }else{

            LocalDate start = startDatePicker.getValue();
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = start.atStartOfDay().atZone(zone).toInstant();
            Date startTime = Date.from(instant);
            LocalDate end = endDatePicker.getValue();
            ZoneId zone1 = ZoneId.systemDefault();
            Instant instant1 = end.atStartOfDay().atZone(zone).toInstant();
            Date endTime = Date.from(instant);

            StockSearchConditionVO searchVO = new StockSearchConditionVO(null, stockName1, startTime, endTime);


        }




        setTableContrast();
        setClosePriceLine();
        setIncomeLine();
        setIncomeLine2();



//        seriesMap.put(stock.getStockCode(), series);
    }

    /**
     * cyy
     * 判断是否存在
     */
    public boolean Judge(String name){
        boolean b =false;

        Iterator<StockVO> iter = allStock.iterator();
        while(iter.hasNext()){
            StockVO stock = iter.next();
            if(name==stock.getName()){b=true;}

        }
        return b;


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
