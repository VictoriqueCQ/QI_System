package ui;/**
 * Created by chenyuyan on 17/3/3.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import quantour.vo.StockVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class ContrastController extends Application {
    private Main main;


    private List<StockVO> allStock ;//所有股票
    private StockVO stock1;
    private StockVO stock2;


    @FXML
    private TextField addName1;

    @FXML
    private  TextField addName2;

    @FXML
    private DatePicker startTimeDatePicker;

    @FXML
    private DatePicker endTimeDatePicker;



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
                                        startTimeDatePicker.getValue().plusDays(1))
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
        endTimeDatePicker.setDayCellFactory(dayCellFactory1);
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
                                        endTimeDatePicker.getValue().minusDays(1))
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
        startTimeDatePicker.setDayCellFactory(dayCellFactory1);
    }


//    XYChart.Series series11 = new XYChart.Series();
//    XYChart.Series series22= new XYChart.Series();
//    XYChart.Series series33 = new XYChart.Series();


    private void setDatePicker(){
        startTimeDatePicker.setValue(LocalDate.of(2005,2,1));
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
        startTimeDatePicker.setDayCellFactory(dayCellFactory1);
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
        endTimeDatePicker.setDayCellFactory(dayCellFactory2);
        endTimeDatePicker.setValue(LocalDate.of(2014,4,30));

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

    @FXML
    public void addCompare(){

//        xAxis.setLabel("Number of Month");
        //creating the chart
//        final LineChart<Number,Number> lineChart =
//                new LineChart<Number,Number>(xAxis,yAxis);

//        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
       /* String stockName1 = addName1.getText();
        String stockName2 = addName2.getText();
        boolean b1 = Judge(stockName1);
        boolean b2 = Judge(stockName2);
        if(stockName1==stockName2){
            ExceptionTips repeat = new ExceptionTips("股票名重复");
         }else if(b1==false||b2==false){
            ExceptionTips illegal = new ExceptionTips("输入股票名不存在");

            } else{
            LocalDate start = startTimeDatePicker.getValue();
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = start.atStartOfDay().atZone(zone).toInstant();
            Date startTime = Date.from(instant);
            LocalDate end = endTimeDatePicker.getValue();
            ZoneId zone1 = ZoneId.systemDefault();
            Instant instant1 = end.atStartOfDay().atZone(zone).toInstant();
            Date endTime = Date.from(instant);
            StockSearchConditionVO searchVO = new StockSearchConditionVO(null, stockName1, startTime, endTime);




            setTableContrast();
            setClosePriceLine();
            setIncomeLine();
            setIncomeLine2();

         }*/
        setTableContrast();
        setClosePriceLine();
        setIncomeLine();
        setIncomeLine2();






//        seriesMap.put(stock.getStockCode(), series);
    }


    /**
     * cyy
     * 判断是否存在
     * */
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
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("股票一");
        //populating the series with data
        /*series.getData().add(new XYChart.Data("a", 23));
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
        System.out.print("aaaaaaa");*/
        double[] d = new double[3];
        Date d2=new Date();
        d[0]=100;d[1]=2;d[2]=30;
        Date d1  = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d1 = df.parse("2015-07-07");
        } catch (ParseException e) {
            e.printStackTrace();
        }



        for(int i = 0;i<d.length;i++){


//            Calendar c   =   new GregorianCalendar();
//            c.setTime(d1);


//            c.add(c.DATE,i);


//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date date=new java.util.Date();
//            String s=sdf.format(c.getTime());

            series.getData().add(new XYChart.Data(df.format(new Date(d1.getTime() +(long)i* 24 * 60 * 60 * 1000)), d[i]));


            closePriceLine.getData().add(series);

        }

       /* double[] closePrice = stock1.getClose();
        for(int i = 0;i<closePrice.length;i++){

            Date temp = stock1.getStart();
            Calendar c   =   new GregorianCalendar();
            c.setTime(temp);
            c.add(c.DATE,i);
            series.getData().add(new XYChart.Data(c.getTime().toString(),closePrice[i]));
            closePriceLine.getData().add(series);

        }*/

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
