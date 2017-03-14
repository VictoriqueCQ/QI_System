package ui.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import quantour.vo.MarketVO;
import ui.JsonUtil;
import ui.Main;
import ui.Net;

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

//    private List<MarketPO> marketPOList;//市场上所有股票数据


    @FXML
    private DatePicker datePicker;

    @FXML
    private Button searchButton;

    @FXML
    private TextField volumnTextField;

    private static final int TOTAL_NUMBER_OF_STOCKS = 791;

    private int NumberOfStocksChangedWithinFivePerCent;

    private String volumn;//交易量

    private int NumberOfStocksLimitedUp;//涨停股票

    private int NumberOfStocksLimitedDown;//跌停股票

    private int NumberOfStocksUpOverFivePerCent;//涨幅超过5%的股票数

    private int NumberOfStocksDownOverFivePerCent;//跌幅超过5%的股票数

    private int NumberOfStocksUpOverFivePerCentPerDay;//开盘-收盘大于5%*上一个交易日收盘价的股票个数

    private int NumberOfStocksDownOverFivePerCentPerDay;//开盘-收盘小于-5%*上一个交易日收盘价的股票个数

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

    ObservableList<PieChart.Data> pieChartData;

    @FXML
    private PieChart pieChart = new PieChart();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

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

    //以下都是模拟数据
    private void setBarChart_1() {
        barChart_1.setCategoryGap(90);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("涨跌停股票情况");
        series1.getData().add(new XYChart.Data<>("涨停股票数", NumberOfStocksLimitedUp));
        series1.getData().add(new XYChart.Data<>("跌停股票数", NumberOfStocksLimitedDown));
        barChart_1.getData().clear();
        barChart_1.layout();
        barChart_1.getData().addAll(series1);
        barChart_1.setAnimated(false);
    }

    private void setBarChart_2() {
        barChart_2.setCategoryGap(90);
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("涨跌幅超过5%股票情况");
        series2.getData().add(new XYChart.Data<>("涨幅超过5%股票数", NumberOfStocksUpOverFivePerCent));
        series2.getData().add(new XYChart.Data<>("跌幅超过5%股票数", NumberOfStocksDownOverFivePerCent));
        barChart_2.getData().clear();
        barChart_2.layout();
        barChart_2.getData().add(series2);
        barChart_2.setAnimated(false);
    }

    private void setBarChart_3() {
        barChart_3.setCategoryGap(90);
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("日增幅超过5%股票情况");
        series3.getData().add(new XYChart.Data<>("日增幅超过5%股票数", NumberOfStocksUpOverFivePerCentPerDay));
        series3.getData().add(new XYChart.Data<>("日跌幅超过5%股票数", NumberOfStocksDownOverFivePerCentPerDay));
        barChart_3.getData().clear();
        barChart_3.layout();
        barChart_3.getData().add(series3);
        barChart_3.setAnimated(false);
    }

    public void setPieChart() {
//        显示区域所占比例，暂时尚未时间如何添加
//        Label caption = new Label("");
//        caption.setTextFill(Color.DARKORANGE);
//        caption.setStyle("-fx-font: 24 arial;");
//        pieChart.getData().stream().forEach((data) -> {
//            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
//                    (MouseEvent e) -> {
//                        caption.setTranslateX(e.getSceneX());
//                        caption.setTranslateY(e.getSceneY());
//                        caption.setText(String.valueOf(data.getPieValue())
//                                + "%");
//                    });
//        });
//        当鼠标移入饼图块时，该块位置发生偏移，鼠标离开后，该块回复到原来位置。暂时尚未实现效果
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
        pieChart.setAnimated(false);
    }

//    public void setDatepicker(){
//        datePicker.setOnAction((ActionEvent e)->{
//
//        });
//    }
//
//    public void getMarketVO() {
//
//        Iterator<MarketPO> iter = marketPOList.iterator();
//
//        while (iter.hasNext()) {
//            MarketPO marketPO = iter.next();
//
//            volumn = String.valueOf(marketPO.getTotalDeal());//获取交易量信息
//
//            NumberOfStocksLimitedUp = marketPO.getLimitUpNum();
//
//            NumberOfStocksLimitedDown = marketPO.getLimitDownNum();
//
//            NumberOfStocksUpOverFivePerCent = marketPO.getOverFivePerNum();
//
//            NumberOfStocksDownOverFivePerCent = marketPO.getBelowFivePerNum();
//
//            NumberOfStocksUpOverFivePerCentPerDay = marketPO.getOc_overPFivePerNum();
//
//            NumberOfStocksDownOverFivePerCentPerDay = marketPO.getOc_belowMFivePerNum();
//
//            NumberOfStocksChangedWithinFivePerCent = TOTAL_NUMBER_OF_STOCKS
//                    - (NumberOfStocksLimitedUp + NumberOfStocksLimitedDown
//                    + NumberOfStocksUpOverFivePerCent + NumberOfStocksDownOverFivePerCent
//                    + NumberOfStocksUpOverFivePerCentPerDay + NumberOfStocksDownOverFivePerCent);
//
//        }
//    }


    //这个方法之后会删除
//    public void testPresentation(){
//        volumn = "100000";
//
//        NumberOfStocksLimitedUp = 50;
//
//        NumberOfStocksLimitedDown = 25;
//
//        NumberOfStocksUpOverFivePerCent = 80;
//
//        NumberOfStocksDownOverFivePerCent = 40;
//
//        NumberOfStocksUpOverFivePerCentPerDay = 60;
//
//        NumberOfStocksDownOverFivePerCentPerDay = 30;
//
//        NumberOfStocksChangedWithinFivePerCent = TOTAL_NUMBER_OF_STOCKS
//                - (NumberOfStocksLimitedUp + NumberOfStocksLimitedDown
//                + NumberOfStocksUpOverFivePerCent + NumberOfStocksDownOverFivePerCent
//                + NumberOfStocksUpOverFivePerCentPerDay + NumberOfStocksDownOverFivePerCent);
//
//        //以下是饼图数据
//        PieChart.Data d1 = new PieChart.Data("涨停股票", NumberOfStocksLimitedUp);
//
//        PieChart.Data d2 = new PieChart.Data("涨幅超过5%股票", NumberOfStocksUpOverFivePerCent);
//
//        PieChart.Data d3 = new PieChart.Data("涨跌幅小于5%股票", NumberOfStocksChangedWithinFivePerCent);
//
//        PieChart.Data d4 = new PieChart.Data("跌幅超过5%股票", NumberOfStocksDownOverFivePerCent);
//
//        PieChart.Data d5 = new PieChart.Data("跌停股票", NumberOfStocksLimitedDown);
//
//        pieChartData = FXCollections.observableArrayList(d1, d2, d3, d4, d5);
//
//        System.out.println("Search the data and show the volumn.");
//
//        volumnTextField.setText(volumn);
//
//        this.setBarChart_1();
//        this.setBarChart_2();
//        this.setBarChart_3();
//        this.setPieChart();
//
//    }

    //这个方法在消除bug后将启用，测试界面色彩风格阶段将注释掉
    @FXML
    public void setSearchButton() {
        LocalDate time = datePicker.getValue();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = time.atStartOfDay(zone).toInstant();
        Date date = Date.from(instant);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        String dateString = simpleDateFormat.format(date);

        net.actionPerformed("MARKET\t" + dateString + "\n");
        String stocksMessages;
        stocksMessages = net.run();
        if (stocksMessages == null) {
            System.out.println("No data on that day!");
        } else {
            System.out.println(stocksMessages + " getted");

            JsonUtil jsonUtil = new JsonUtil();
            MarketVO marketVO_middleState = new MarketVO();
            MarketVO marketVO = (MarketVO) jsonUtil.JSONToObj(stocksMessages, marketVO_middleState.getClass());

            volumn = String.valueOf(marketVO.getTotalDeal());//获取交易量信息

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


//        volumn = stocksMessages[0];
//
//        NumberOfStocksLimitedUp = Integer.parseInt(stocksMessages[1]);
//
//        NumberOfStocksLimitedDown = Integer.parseInt(stocksMessages[2]);
//
//        NumberOfStocksUpOverFivePerCent = Integer.parseInt(stocksMessages[3]);
//
//        NumberOfStocksDownOverFivePerCent = Integer.parseInt(stocksMessages[4]);
//
//        NumberOfStocksUpOverFivePerCentPerDay = Integer.parseInt(stocksMessages[5]);
//
//        NumberOfStocksDownOverFivePerCentPerDay = Integer.parseInt(stocksMessages[6]);
//
//        NumberOfStocksChangedWithinFivePerCent = TOTAL_NUMBER_OF_STOCKS
//                - (NumberOfStocksLimitedUp + NumberOfStocksLimitedDown
//                + NumberOfStocksUpOverFivePerCent + NumberOfStocksDownOverFivePerCent
//                + NumberOfStocksUpOverFivePerCentPerDay + NumberOfStocksDownOverFivePerCent);



        //以下是饼图数据
        PieChart.Data d1 = new PieChart.Data("涨停股票", NumberOfStocksLimitedUp);

        PieChart.Data d2 = new PieChart.Data("涨幅超过5%股票", NumberOfStocksUpOverFivePerCent);

        PieChart.Data d3 = new PieChart.Data("涨跌幅小于5%股票", NumberOfStocksChangedWithinFivePerCent);

        PieChart.Data d4 = new PieChart.Data("跌幅超过5%股票", NumberOfStocksDownOverFivePerCent);

        PieChart.Data d5 = new PieChart.Data("跌停股票", NumberOfStocksLimitedDown);

        pieChartData = FXCollections.observableArrayList(d1, d2, d3, d4, d5);

        setBarChart_1();

        setBarChart_2();

        setBarChart_3();

        setPieChart();

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

    public void setMain(Main main,Net net) {

//        this.testPresentation();

        this.main = main;
        this.net=net;
        this.setDatePicker();
    }


//    static class MouseHoverAnimation implements EventHandler {
//        static final Duration ANIMATION_DURATION = new Duration(500);
//        static final double ANIMATION_DISTANCE = 0.15;
//        private double cos;
//        private double sin;
//        private PieChart chart;
//
//        public MouseHoverAnimation(PieChart.Data d, PieChart chart) {
//            this.chart = chart;
//            double start = 0;
//            double angle = calcAngle(d);
//            for (PieChart.Data tmp : chart.getData()) {
//                if (tmp == d) {
//                    break;
//                }
//                start += calcAngle(tmp);
//            }
//
//            cos = Math.cos(Math.toRadians(start + angle / 2));
//            sin = Math.sin(Math.toRadians(start + angle / 2));
//        }
//
//        public void handle(MouseEvent arg0) {
//            Node n = (Node) arg0.getSource();
//            double minX = Double.MAX_VALUE;
//            double maxX = Double.MAX_VALUE * -1;
//
//            for (PieChart.Data d : chart.getData()) {
//                minX = Math
//                        .min(minX, d.getNode().getBoundsInParent().getMinX());
//                maxX = Math
//                        .max(maxX, d.getNode().getBoundsInParent().getMaxX());
//            }
//
//            double radius = maxX - minX;
//            System.out.println("cos:" + cos);
//            System.out.println("sin" + sin);
//            TranslateTransitionBuilder.create()
//                    .toX((radius * ANIMATION_DISTANCE) * cos)
//                    .toY((radius * ANIMATION_DISTANCE) * (-sin))
//                    .duration(ANIMATION_DURATION).node(n).build().play();
//        }
//
//        private static double calcAngle(PieChart.Data d) {
//            double total = 0;
//            for (PieChart.Data tmp : d.getChart().getData()) {
//                total += tmp.getPieValue();
//            }
//            return 360 * (d.getPieValue() / total);
//        }
//
//        @Override
//        public void handle(Event event) {
//
//
//        }
//    }
//
//    static class MouseExitAnimation implements EventHandler {
//        public void handle(MouseEvent event) {
//            TranslateTransitionBuilder.create().toX(0).toY(0)
//                    .duration(new Duration(500)).node((Node) event.getSource())
//                    .build().play();
//        }
//
//        @Override
//        public void handle(Event event) {
//
//
//        }
//    }
}
