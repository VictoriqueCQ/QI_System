package ui.Controller;
/**
 * Created by chenyuyan on 17/3/3.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import quantour.vo.StockSearchConditionVO;
import quantour.vo.StockVO;
import ui.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.text.DecimalFormat;
public class ContrastController extends Application {
    private Main main;

    private Net net;
    private List<StockVO> allStock;//所有股票
    private StockVO stock1;
    private StockVO stock2;


    @FXML
    private TextField nameTextField1;

    @FXML
    private TextField nameTextField2;

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

    @FXML
    private TableView<StockModel> stockTable;

    @FXML
    private TableView<StockModel> varianceTable;


    @FXML
    private TableColumn<StockModel, String> stockName1;

    @FXML
    private TableColumn<StockModel, String> stockID;

    @FXML
    private TableColumn<StockModel, String> minPrice;

    @FXML
    private TableColumn<StockModel, String> maxPrice;

    @FXML
    private TableColumn<StockModel, String> riseAndDown;

    @FXML
    private  TableColumn<StockModel,String> variance;

    @FXML
    private TableColumn<StockModel,String> stockName;

    private  StockModel stockModel1;

    private StockModel stockModel2;

    private ObservableList<StockModel> models;

    private Map<String, XYChart.Series<String, Number>> seriesMap;

//    private Map<String, XYChart.Series<String, Number>> seriesMap;

   /* @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;*/


//    private Map<String, XYChart.Series<Number, Number>> seriesMap;

    @FXML
    private void updateEndTimeDatePicker() {
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
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #000000;");
                                }
                                if (item.isAfter(
                                        LocalDate.of(2014, 4, 30))
                                        ) {
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
    private void updateStartTimeDatePicker() {
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
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #000000;");
                                }
                                if (item.isBefore(
                                        LocalDate.of(2005, 2, 1))
                                        ) {
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


    private void setDatePicker() {
        startTimeDatePicker.setValue(LocalDate.of(2005, 2, 1));
        final Callback<DatePicker, DateCell> dayCellFactory1 =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        LocalDate.of(2005, 2, 1))
                                        ) {
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
                                        LocalDate.of(2014, 4, 30))
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #000000;");
                                }
                            }
                        };
                    }
                };
        endTimeDatePicker.setDayCellFactory(dayCellFactory2);
        endTimeDatePicker.setValue(LocalDate.of(2014, 4, 30));

    }



    @Override
    public void start(Stage primaryStage) {

    }



    @FXML
    public void addCompare() {

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
            AlertUtil repeat = new AlertUtil("股票名重复");
         }else if(b1==false||b2==false){
            AlertUtil illegal = new AlertUtil("输入股票名不存在");

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

        String stockName1 = nameTextField1.getText();
        String stockName2 = nameTextField2.getText();
        LocalDate startLocalDate = startTimeDatePicker.getValue();
        LocalDate endLocalDate = endTimeDatePicker.getValue();
        Date startDate = this.changeDateStyle(startLocalDate);
        Date endDate = this.changeDateStyle(endLocalDate);



        StockSearchConditionVO searchConditionVO1 = new StockSearchConditionVO(stockName1,null,startDate,endDate);
        StockSearchConditionVO searchConditionVO2 = new StockSearchConditionVO(stockName2,null,startDate,endDate);

        stock1 = getStockVoByCondition(searchConditionVO1);

//        double[]open = {1,2,3};
//        double[]high = {1,2,3};
//        double[]low = {1,2,3};
//        double[]close={1,2,3};
//        stock1 = new StockVO("aaa",123,null,null,open,high,low,close,null,null,null,null,null,null,null,null,null,2.3);


        if(stock1==null){
            AlertUtil.showErrorAlert("对不起，您输入的股票一不存在");
        }
        stock2 = getStockVoByCondition(searchConditionVO2);
//        stock2 = new StockVO("aaa",123,null,null,open,high,low,close,null,null,null,null,null,null,null,null,null,2.3);
        if(stock2==null){
            AlertUtil.showErrorAlert("对不起，您输入的股票二不存在");
        }



        setTableContrast();
        setClosePriceLine(stock1.getClose(),stock1.getDates(),stock1.getName());
        setClosePriceLine(stock2.getClose(),stock2.getDates(),stock2.getName());

        setIncomeLine(stock1.getDates(), stock1.getName(),stock1.getProfit());
        setIncomeLine(stock2.getDates(),stock2.getName(),stock2.getProfit());
        setVariance();


//        setIncomeLine2();


//        seriesMap.put(stock.getStockCode(), series);
    }


    /**
     * cyy
     * 判断是否存在
     */
    public boolean Judge(String name) {
        boolean b = false;
        Iterator<StockVO> iter = allStock.iterator();
        while (iter.hasNext()) {
            StockVO stock = iter.next();
            if (name == stock.getName()) {
                b = true;
            }

        }
        return b;

    }

    public void setTableContrast() {
        stockName1.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
        stockID.setCellValueFactory(celldata -> celldata.getValue().idProperty());
        minPrice.setCellValueFactory(celldata -> celldata.getValue().minPriceProperty());
        maxPrice.setCellValueFactory(celldata -> celldata.getValue().maxPriceProperty());
        riseAndDown.setCellValueFactory(celldata -> celldata.getValue().riseAndDownProperty());
        stockModel1 = stockVOtoStockModle(stock1);
        stockModel2 = stockVOtoStockModle(stock2);
        System.out.print(stockModel1.getName());
        models = FXCollections.observableArrayList();
        models.add(stockModel1);
        models.add(stockModel2);
        stockTable.setItems(models);

        System.out.print("fghj");
    }

    public StockModel stockVOtoStockModle(StockVO stockVO) {
        StockModel model = new StockModel();
        model.setName(stockVO.getName());
        model.setID(stockVO.getCode());
        double[] low = new double[stockVO.getLow().length];
        double minTemp = low[0];
        for (int i = 0; i < low.length; i++) {
            if (low[i] < minTemp) {
                minTemp = low[i];
            }
        }
        model.setMinPrice(minTemp);

        double[] high = new double[stockVO.getHigh().length];
        double maxTemp = high[0];
        for (int i = 0; i < high.length; i++) {
            if (low[i] > maxTemp) {
                maxTemp = high[i];
            }
        }
        model.setMaxPrice(maxTemp);
        double dd = 2.00;

        double riseAndDown = (stockVO.getClose()[stockVO.getClose().length - 1] - stockVO.getClose()[0]) / stockVO.getClose()[0];
        DecimalFormat   df   =new  DecimalFormat("#.00");
        df.format(riseAndDown);
        model.setRiseAndDown(riseAndDown * 100 + "%");
        model.setVariance(stockVO.getVariance().toString());
        return model;
    }

    public void setClosePriceLine(double[]close,List<Date> dates,String name) {
        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        series1.setName(name);
//        List<Date> dates1 = stock1.getDates();

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        for (int i = dates.size() - 1; i >= 0; i--) {
            String s = format.format(dates.get(i));
            series1.getData().add(new XYChart.Data(s, close[i]));
        }


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
       /* double[] d = new double[3];
        Date d2 = new Date();
        d[0] = 100;
        d[1] = 2;
        d[2] = 30;
        Date d1 = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d1 = df.parse("2015-12-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < d.length; i++) {*/


//            Calendar c   =   new GregorianCalendar();
//            c.setTime(d1);


//            c.add(c.DATE,i);


//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date date=new java.util.Date();
//            String s=sdf.format(c.getTime());

//            series.getData().add(new XYChart.Data(df.format(new Date(d1.getTime() + (long) i * 24 * 60 * 60 * 1000)), d[i]));
            closePriceLine.getData().add(series1);

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



    public void setIncomeLine(List<Date> dates, String name, ArrayList<Double> income) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(name);
        //populating the series with data
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        for (int i = 1 ;i<dates.size();i++) {
            String s = format.format(dates.get(i));
            series.getData().add(new XYChart.Data(s, income.get(i-1)));
        }

        IncomeLine.getData().add(series);
//        System.out.print("aaaaaaa");
    }

    public void setVariance() {
        stockName.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
        variance.setCellValueFactory(celldata -> celldata.getValue().varianceProperty());


        System.out.print(stockModel1.getName());
        models = FXCollections.observableArrayList();
        models.add(stockModel1);
        models.add(stockModel2);
        stockTable.setItems(models);

        System.out.print("fghj");


    }

    private StockVO getStockVoByCondition(StockSearchConditionVO searchConditionVO){
        if(searchConditionVO.getStockName()==""){
            AlertUtil.showErrorAlert("对不起，您未输入股票信息");
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy ");
        String starttime = format.format(searchConditionVO.getStartTime());
        String endtime = format.format(searchConditionVO.getEndTime());

//        String starttime = searchConditionVO.getStartTime();
//        Date endtime = searchConditionVO.getEndTime();
        String stockID = searchConditionVO.getStockID();

        String input = "STOCK\t" + stockID + "\t" + "NULL" + "\t" + starttime + "\t" + endtime + "\n";
        net.actionPerformed(input);
        String json = net.run();
        JsonUtil jsonUtil = new JsonUtil();
        StockVO stockVO1 = new StockVO();
        StockVO stockVO = (StockVO) jsonUtil.JSONToObj(json, stockVO1.getClass());

        return stockVO;




    }
    @FXML
    private void removeCompare(){

            stockTable.getItems().removeAll(models);
            closePriceLine.getData().removeAll();




    }

    private Date changeDateStyle(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    public void setMain(Main main,Net net) {
//        setCompare();
        this.main = main;
        this.net=net;
        this.setDatePicker();
    }

}
