package ui.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import quantour.vo.MarketVO;
import ui.AllStockConditionModel;
import ui.JsonUtil;
import ui.Main;
import ui.Net;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;


/**
 * Created by Administrator on 2017/3/3.
 */
public class
ThermometerController implements Initializable {

    private Main main;

    private Net net;

    @FXML
    private TableView<AllStockConditionModel> stockConditionTable;

    //股票表格中的“股票类型”列
    @FXML
    private TableColumn<AllStockConditionModel, String> stockType;

    //股票表格中的“股票数量”列
    @FXML
    private TableColumn<AllStockConditionModel, String> stockNumber;

    //股票表格数据
    private ObservableList<AllStockConditionModel> data;

    //日期选择器
    @FXML
    private DatePicker datePicker;

    //“查询”按钮
    @FXML
    private Button searchButton;

    //“今日交易量”具体数目所显示的区域
    @FXML
    private TextField volumnTextField;

    //总股票数
    private static final int TOTAL_NUMBER_OF_STOCKS = 791;

    //当日涨跌幅小于5%的股票数
    private int NumberOfStocksChangedWithinFivePerCent;

    //当日总交易量
    private String volumn;

    //当日涨停股票数
    private int NumberOfStocksLimitedUp;

    //当日跌停股票数
    private int NumberOfStocksLimitedDown;

    //当日涨幅超过5%的股票数
    private int NumberOfStocksUpOverFivePerCent;

    //当日跌幅超过5%的股票数
    private int NumberOfStocksDownOverFivePerCent;//

    //开盘-收盘大于5%*上一个交易日收盘价的股票个数
    private int NumberOfStocksUpOverFivePerCentPerDay;//

    //开盘-收盘小于-5%*上一个交易日收盘价的股票个数
    private int NumberOfStocksDownOverFivePerCentPerDay;//

    //涨跌停股票数柱状图纵坐标
    @FXML
    private NumberAxis NumberOfStock_1 = new NumberAxis();

    //涨跌幅超过5%股票柱状图纵坐标
    @FXML
    private NumberAxis NumberOfStock_2 = new NumberAxis();

    //开盘-收盘大于/小于5%*上一个交易日收盘价的股票柱状图纵坐标
    @FXML
    private NumberAxis NumberOfStock_3 = new NumberAxis();

    //涨跌停股票数柱状图横坐标
    @FXML
    private CategoryAxis TypeOfStock_1 = new CategoryAxis();

    //涨跌幅超过5%股票柱状图横坐标
    @FXML
    private CategoryAxis TypeOfStock_2 = new CategoryAxis();

    //开盘-收盘大于/小于5%*上一个交易日收盘价的股票柱状图横坐标
    @FXML
    private CategoryAxis TypeOfStock_3 = new CategoryAxis();

    //涨跌停股票数柱状图
    @FXML
    private BarChart<String, Number> barChart_1 = new BarChart<String, Number>(TypeOfStock_1, NumberOfStock_1);

    //涨跌幅超过5%股票柱状图
    @FXML
    private BarChart<String, Number> barChart_2 = new BarChart<String, Number>(TypeOfStock_2, NumberOfStock_2);

    //开盘-收盘大于/小于5%*上一个交易日收盘价的股票柱状图
    @FXML
    private BarChart<String, Number> barChart_3 = new BarChart<String, Number>(TypeOfStock_3, NumberOfStock_3);

    //所有股票情况的数据——饼图
    ObservableList<PieChart.Data> pieChartData;

    //所有股票情况——饼图
    @FXML
    private PieChart pieChart = new PieChart();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //表格set函数
    private void setTableView() {

        stockType.setCellValueFactory(celldata -> celldata.getValue().StockTypeProperty());
        stockNumber.setCellValueFactory(celldata -> celldata.getValue().StockNumberProperty());

        data = FXCollections.observableArrayList(
                new AllStockConditionModel("涨停股票数", String.valueOf(NumberOfStocksLimitedUp)),
                new AllStockConditionModel("跌停股票数", String.valueOf(NumberOfStocksLimitedDown)),
                new AllStockConditionModel("涨幅超过5%的股票数", String.valueOf(NumberOfStocksUpOverFivePerCent)),
                new AllStockConditionModel("跌幅超过5%的股票数", String.valueOf(NumberOfStocksDownOverFivePerCent)),
                new AllStockConditionModel("日增幅大于5%股票数", String.valueOf(NumberOfStocksUpOverFivePerCentPerDay)),
                new AllStockConditionModel("日增幅小于5%股票数", String.valueOf(NumberOfStocksDownOverFivePerCentPerDay)),
                new AllStockConditionModel("涨跌幅小于5%股票数", String.valueOf(NumberOfStocksChangedWithinFivePerCent))
        );

        //去掉表格的横向拖动器
        stockConditionTable.getStyleClass().add("edge-to-edge");
        stockConditionTable.getStyleClass().add("noborder");
        stockConditionTable.setItems(data);
    }

    //日起选择器set函数
    private void setDatePicker() {
        datePicker.setValue(LocalDate.of(2005, 2, 1));
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
        datePicker.setDayCellFactory(dayCellFactory1);
    }

    //涨跌停股票柱状图set函数
    private void setBarChart_1() {
        barChart_1.setCategoryGap(107);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("涨跌停股票情况");
        series1.getData().add(new XYChart.Data<>("涨停股票数", NumberOfStocksLimitedUp));
        series1.getData().add(new XYChart.Data<>("跌停股票数", NumberOfStocksLimitedDown));
        barChart_1.getData().clear();
        barChart_1.layout();
        barChart_1.getData().addAll(series1);
        barChart_1.setAnimated(false);
    }

    //涨跌幅超过5%股票柱状图set函数
    private void setBarChart_2() {
        barChart_2.setCategoryGap(107);
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("涨跌幅超过5%股票情况");
        series2.getData().add(new XYChart.Data<>("涨幅超过5%股票数", NumberOfStocksUpOverFivePerCent));
        series2.getData().add(new XYChart.Data<>("跌幅超过5%股票数", NumberOfStocksDownOverFivePerCent));

        //此处来源于stackoverflow，用来解决图表初次显示没有横坐标的bug，代价是图表的构造不再有明显动画效果
        barChart_2.getData().clear();
        barChart_2.layout();
        barChart_2.getData().add(series2);
        barChart_2.setAnimated(false);
    }

    //开盘-收盘大于/小于5%*上一个交易日收盘价的股票柱状图set函数
    private void setBarChart_3() {
        barChart_3.setCategoryGap(107);
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("开盘-收盘超过5%*上一个交易日收盘价的股票情况");
        series3.getData().add(new XYChart.Data<>("日增幅大于5%股票数", NumberOfStocksUpOverFivePerCentPerDay));
        series3.getData().add(new XYChart.Data<>("日增幅小于5%股票数", NumberOfStocksDownOverFivePerCentPerDay));
        barChart_3.getData().clear();
        barChart_3.layout();
        barChart_3.getData().add(series3);
        barChart_3.setAnimated(false);
    }

    //所有股票情况饼图set函数
    public void setPieChart() {

        pieChart.getData().setAll(pieChartData);
        //限制多次查询时饼图构造的动画效果，避免图表被破坏
        pieChart.setAnimated(false);
    }

    //将datepicker获取的时间转为date类
    public Date changeDateStyle(LocalDate localDate) {

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);

        return date;
    }

    //“查询”按钮set函数
    @FXML
    public void setSearchButton() {
        //获取datepicker所选的时间
        LocalDate time = datePicker.getValue();

        //将时间格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        String dateString = simpleDateFormat.format(this.changeDateStyle(time));

        //调用Net类获得所查询日期的股票数据
        net.actionPerformed("MARKET\t" + dateString + "\n");
        String stocksMessages;
        stocksMessages = net.run();
        if (stocksMessages == null) {
            //若当日没有数据，在控制台输出此字符串，界面中不够早任何图表和表格
            System.out.println("No data on that day!");
        } else {
            System.out.println(stocksMessages + " getted");

            //json字符串转成MarketVO类型
            JsonUtil jsonUtil = new JsonUtil();
            MarketVO marketVO_middleState = new MarketVO();
            MarketVO marketVO = (MarketVO) jsonUtil.JSONToObj(stocksMessages, marketVO_middleState.getClass());

            System.err.println(marketVO.getTotalDeal());

            //将交易量转成字符串且不用科学计数法
            BigDecimal bd = new BigDecimal(marketVO.getTotalDeal());

            volumn = bd.toPlainString();//获取交易量信息

            NumberOfStocksLimitedUp = marketVO.getLimitUpNum();

            NumberOfStocksLimitedDown = marketVO.getLimitDownNum();

            NumberOfStocksUpOverFivePerCent = marketVO.getOverFivePerNum();

            NumberOfStocksDownOverFivePerCent = marketVO.getBelowFivePerNum();

            NumberOfStocksUpOverFivePerCentPerDay = marketVO.getOc_overPFivePerNum();

            NumberOfStocksDownOverFivePerCentPerDay = marketVO.getOc_belowMFivePerNum();

            NumberOfStocksChangedWithinFivePerCent = TOTAL_NUMBER_OF_STOCKS
                    - (NumberOfStocksLimitedUp + NumberOfStocksLimitedDown
                    + NumberOfStocksUpOverFivePerCent + NumberOfStocksDownOverFivePerCent
                    + NumberOfStocksUpOverFivePerCentPerDay + NumberOfStocksDownOverFivePerCent);
        }


        //构造饼图数据
        PieChart.Data d1 = new PieChart.Data("涨停股票", NumberOfStocksLimitedUp);

        PieChart.Data d2 = new PieChart.Data("涨幅超过5%股票", NumberOfStocksUpOverFivePerCent);

        PieChart.Data d3 = new PieChart.Data("涨跌幅小于5%股票", NumberOfStocksChangedWithinFivePerCent);

        PieChart.Data d4 = new PieChart.Data("跌幅超过5%股票", NumberOfStocksDownOverFivePerCent);

        PieChart.Data d5 = new PieChart.Data("跌停股票", NumberOfStocksLimitedDown);

        pieChartData = FXCollections.observableArrayList(d1, d2, d3, d4, d5);

        //构造各种图表和表格
        setBarChart_1();

        setBarChart_2();

        setBarChart_3();

        setPieChart();

        setTableView();

        System.out.println("Search the data and show the volumn.");

        volumnTextField.setText(volumn);


        //给按钮添加立体特效
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0f);
        Lighting l = new Lighting();
        l.setLight(light);
        l.setSurfaceScale(5.0f);

        searchButton.setEffect(l);

        //当鼠标进入按钮时添加阴影特效
        DropShadow shadow = new DropShadow();
        searchButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            searchButton.setEffect(shadow);
        });

        //当鼠标离开按钮时移除阴影效果
        searchButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            searchButton.setEffect(null);
        });
    }

    public ThermometerController() {

    }

    public void setMain(Main main, Net net) {

        this.main = main;
        this.net = net;
        this.setDatePicker();
    }

}