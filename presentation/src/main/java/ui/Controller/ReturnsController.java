package ui.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.util.Callback;
import ui.CumulativeReturnsModel;
import ui.Main;
import ui.Net;
import ui.ReturnsModel;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2017/3/24.
 */
public class ReturnsController implements Initializable {
    private Main main;

    private Net net;

    /*
    *以下是累计收益率中的变量（累计收益率有两个方法，分别是setCumulative（）和setLineChart（））
    *
     */
    //累计收益率图表
    @FXML
    private TableView<CumulativeReturnsModel> cumulativeTableView;

    //年化收益率
    @FXML
    private TableColumn<CumulativeReturnsModel, String> yearReturns;

    //基准年化收益率
    @FXML
    private TableColumn<CumulativeReturnsModel, String> standardYearReturns;

    //阿尔法
    @FXML
    private TableColumn<CumulativeReturnsModel, String> alpha;

    //贝塔
    @FXML
    private TableColumn<CumulativeReturnsModel, String> beta;

    //夏普比率
    @FXML
    private TableColumn<CumulativeReturnsModel, String> sharp;

    //收益波动率
    @FXML
    private TableColumn<CumulativeReturnsModel, String> wave;

    //信息比率
    @FXML
    private TableColumn<CumulativeReturnsModel, String> information;

    //最大回撤
    @FXML
    private TableColumn<CumulativeReturnsModel, String> retreats;

    //换手率
    @FXML
    private TableColumn<CumulativeReturnsModel, String> changeHands;

    private ObservableList<CumulativeReturnsModel> cumulativeData;

    @FXML
    private NumberAxis returnsPercent = new NumberAxis();

    @FXML
    private CategoryAxis date = new CategoryAxis();

    @FXML
    private LineChart<String, Number> lineChart = new LineChart<String, Number>(date, returnsPercent);


    /*
    *以下是超额收益率的变量
    *
     */
    //超额收益率中的表格
    @FXML
    private TableView<ReturnsModel> tableView;

    //相对强弱计算周期
    @FXML
    private TableColumn<ReturnsModel, String> period;

    //超额收益
    @FXML
    private TableColumn<ReturnsModel, String> returns;

    //1年内收益
    @FXML
    private TableColumn<ReturnsModel, String> percent;

    //表格中数据
    private ObservableList<ReturnsModel> data;

    @FXML
    private NumberAxis PercentNumber_1 = new NumberAxis();

    @FXML
    private NumberAxis PercentNumber_2 = new NumberAxis();

    @FXML
    private NumberAxis PeriodNumber_1 = new NumberAxis();

    @FXML
    private NumberAxis PeriodNumber_2 = new NumberAxis();

    @FXML
    private AreaChart<Number, Number> areaChart_1 = new AreaChart<Number, Number>(PeriodNumber_1, PercentNumber_1);

    @FXML
    private AreaChart<Number, Number> areaChart_2 = new AreaChart<Number, Number>(PeriodNumber_2, PercentNumber_2);

    /*
    *以下是相对收益指数的数据
    *
     */
    @FXML
    private CategoryAxis ReturnsNumber = new CategoryAxis();

    @FXML
    private NumberAxis FrequencyNumber = new NumberAxis();

    @FXML
    private BarChart<String, Number> barChart = new BarChart<String, Number>(ReturnsNumber, FrequencyNumber);



    /*
    这里是动量策略和均值回归的变量
     */

    @FXML
    private DatePicker StartDate_MomentumStrategy;

    @FXML
    private DatePicker EndDate_MomentumStrategy;

    @FXML
    private TextField HoldingPeriod_MomentumStrategy;

    @FXML
    private TextField FormativePeriod_MomentumStrategy;

    @FXML
    private TextField StockheldInHouse_MomentumStrategy;

    @FXML
    private ComboBox<String> Plate_MomentumStrategy;

    @FXML
    private Button ChooseStock_MomentumStrategy;

    @FXML
    private Button search_MomentumStrategy;

    @FXML
    private DatePicker StartDate_MeanReversio;

    @FXML
    private DatePicker EndDate_MeanReversio;

    @FXML
    private TextField HoldingPeriod_MeanReversio;

    @FXML
    private TextField FormativePeriod_MeanReversio;

    @FXML
    private TextField StockHeldInHouse_MeanReversio;

    @FXML
    private ComboBox<String> Plate_MeanReversio;

    @FXML
    private Button ChooseStock_MeanReversio;

    @FXML
    private Button search_MeanReversio;


    @FXML
    private void gotoSelectStock(){
        main.gotoSelectStock();
    }

    /*
    这里是自选股票板块情况（表格）

     */

    private void setStockComboBox(){
        ObservableList<String> plate_MS = FXCollections.observableArrayList();
        List<String> plateName_MS = new ArrayList<String>();
        plateName_MS.add("板块1");
        plateName_MS.add("板块2");
        plate_MS.addAll(plateName_MS);
        Plate_MomentumStrategy.setItems(plate_MS);

        ObservableList<String> plate_MR = FXCollections.observableArrayList();
        List<String> plateName_MR = new ArrayList<String>();
        plateName_MR.add("板块1");
        plateName_MR.add("板块2");
        plate_MR.addAll(plateName_MR);
        Plate_MeanReversio.setItems(plate_MR);
    }

    private void setHeldComboBox(){
        ObservableList<String> HeldPeriod = FXCollections.observableArrayList();
        List<String> HeldPeriodName = new ArrayList<>();
    }

    public void setStartDatePicker_MomentumStrategy(){
        StartDate_MomentumStrategy.setValue(LocalDate.of(2005, 2, 1));
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
        StartDate_MomentumStrategy.setDayCellFactory(dayCellFactory1);
    }

    public void setEndDatePicker_MomentumStrategy(){
        EndDate_MomentumStrategy.setValue(LocalDate.of(2005, 2, 1));
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
        EndDate_MomentumStrategy.setDayCellFactory(dayCellFactory1);
    }

    public void setStartDatePicker_MeanReversio(){
        StartDate_MeanReversio.setValue(LocalDate.of(2005, 2, 1));
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
        StartDate_MeanReversio.setDayCellFactory(dayCellFactory1);
    }

    public void setEndDatePicker_MeanReversio(){
        EndDate_MeanReversio.setValue(LocalDate.of(2005, 2, 1));
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
        EndDate_MeanReversio.setDayCellFactory(dayCellFactory1);
    }

    //将datepicker获取的时间转为date类
    public Date changeDateStyle(LocalDate localDate) {

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);

        return date;
    }

    private void setMomentumStrategyInput(){
        LocalDate StartDate_MS = StartDate_MomentumStrategy.getValue();
        LocalDate EndDate_MS = EndDate_MomentumStrategy.getValue();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MS = simpleDateFormat.format(this.changeDateStyle(StartDate_MS));
        String EndDateString_MS = simpleDateFormat.format(this.changeDateStyle(EndDate_MS));
        net.actionPerformed("Strategy\t"+"M\t"+StartDateString_MS+"\t"+EndDateString_MS+"\t"+"");
    }

    private void setMeanReversioInput(){
        LocalDate StartDate_MR = StartDate_MeanReversio.getValue();
        LocalDate EndDate_MR = EndDate_MeanReversio.getValue();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MR = simpleDateFormat.format(this.changeDateStyle(StartDate_MR));
        String EndDateString_MR = simpleDateFormat.format(this.changeDateStyle(EndDate_MR));
        net.actionPerformed("Strategy\t"+"M\t"+StartDateString_MR+"\t"+EndDateString_MR+"\t"+"");
    }

    private void setCumulativeTableView(){
        yearReturns.setCellValueFactory(celldata -> celldata.getValue().yearReturnsProperty());
        standardYearReturns.setCellValueFactory(celldata -> celldata.getValue().standardYearReturnsProperty());
        alpha.setCellValueFactory(celldata -> celldata.getValue().alphaProperty());
        beta.setCellValueFactory(celldata -> celldata.getValue().betaProperty());
        sharp.setCellValueFactory(celldata -> celldata.getValue().sharpProperty());
        wave.setCellValueFactory(celldata -> celldata.getValue().waveProperty());
        information.setCellValueFactory(celldata -> celldata.getValue().informationProperty());
        retreats.setCellValueFactory(celldata -> celldata.getValue().retreatsProperty());
        changeHands.setCellValueFactory(celldata -> celldata.getValue().changeHandsProperty());

        //测试数据，数据层完成后将修改
        cumulativeData = FXCollections.observableArrayList(
                new CumulativeReturnsModel("35.7%","12.4%","14.6%", "0.97", "1.29", "24.9%", "1.03", "23.8%", "--")
        );

        cumulativeTableView.getStyleClass().add("edge-to-edge");
        cumulativeTableView.getStyleClass().add("noborder");
        cumulativeTableView.setItems(cumulativeData);
    }

    private void setLineChart(){
        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data<>("2005-07",10));
        series1.getData().add(new XYChart.Data<>("2006-01",30));
        series1.getData().add(new XYChart.Data<>("2006-07",40));
        series1.getData().add(new XYChart.Data<>("2007-01",50));
        series1.getData().add(new XYChart.Data<>("2007-07",70));
        series1.getData().add(new XYChart.Data<>("2008-01",80));
        series1.getData().add(new XYChart.Data<>("2008-07",100));
        series1.getData().add(new XYChart.Data<>("2009-01",110));
        series1.getData().add(new XYChart.Data<>("2009-07",130));
        series1.getData().add(new XYChart.Data<>("2010-01",120));
        series1.getData().add(new XYChart.Data<>("2010-07",100));
        series1.getData().add(new XYChart.Data<>("2011-01",90));
        series1.getData().add(new XYChart.Data<>("2011-07",70));
        series1.getData().add(new XYChart.Data<>("2012-01",80));
        series1.getData().add(new XYChart.Data<>("2012-07",100));
        series1.getData().add(new XYChart.Data<>("2013-01",120));
        series1.getData().add(new XYChart.Data<>("2013-07",150));
        series1.getData().add(new XYChart.Data<>("2014-01",190));

        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data<>("2005-07",10));
        series2.getData().add(new XYChart.Data<>("2006-01",15));
        series2.getData().add(new XYChart.Data<>("2006-07",20));
        series2.getData().add(new XYChart.Data<>("2007-01",25));
        series2.getData().add(new XYChart.Data<>("2007-07",35));
        series2.getData().add(new XYChart.Data<>("2008-01",40));
        series2.getData().add(new XYChart.Data<>("2008-07",50));
        series2.getData().add(new XYChart.Data<>("2009-01",55));
        series2.getData().add(new XYChart.Data<>("2009-07",65));
        series2.getData().add(new XYChart.Data<>("2010-01",60));
        series2.getData().add(new XYChart.Data<>("2010-07",50));
        series2.getData().add(new XYChart.Data<>("2011-01",45));
        series2.getData().add(new XYChart.Data<>("2011-07",35));
        series2.getData().add(new XYChart.Data<>("2012-01",40));
        series2.getData().add(new XYChart.Data<>("2012-07",50));
        series2.getData().add(new XYChart.Data<>("2013-01",60));
        series2.getData().add(new XYChart.Data<>("2013-07",75));
        series2.getData().add(new XYChart.Data<>("2014-01",95));

        lineChart.getData().addAll(series1,series2);
    }


    private void setTableView() {
        period.setCellValueFactory(celldata -> celldata.getValue().periodProperty());
        returns.setCellValueFactory(celldata -> celldata.getValue().returnsProperty());
        percent.setCellValueFactory(celldata -> celldata.getValue().percentProperty());

        //测试数据，数据层完成后将修改
        data = FXCollections.observableArrayList(
                new ReturnsModel("2", "0.8%", "58%"),
                new ReturnsModel("4", "2.9%", "65%"),
                new ReturnsModel("6", "3.0%", "65%"),
                new ReturnsModel("8", "2.7%", "58%"),
                new ReturnsModel("10", "2.5%", "60%"),
                new ReturnsModel("12", "1.2%", "53%"),
                new ReturnsModel("14", "0.8%", "53%"),
                new ReturnsModel("16", "0.2%", "48%"),
                new ReturnsModel("18", "-0.1%", "48%"),
                new ReturnsModel("20", "-0.1%", "48%"),
                new ReturnsModel("22", "-0.1%", "52%"),
                new ReturnsModel("24", "-0.7%", "47%"),
                new ReturnsModel("26", "-0.8%", "52%"),
                new ReturnsModel("28", "-1.1%", "44%"),
                new ReturnsModel("30", "-1.0%", "43%"),
                new ReturnsModel("32", "-1.0%", "44%"),
                new ReturnsModel("34", "-1.5%", "43%"),
                new ReturnsModel("36", "-1.5%", "38%"),
                new ReturnsModel("38", "-1.1%", "45%"),
                new ReturnsModel("40", "-1.7%", "43%"),
                new ReturnsModel("42", "-1.4%", "45%"),
                new ReturnsModel("44", "-1.6%", "47%"),
                new ReturnsModel("46", "-1.4%", "48%"),
                new ReturnsModel("48", "-1.0%", "45%"),
                new ReturnsModel("50", "-0.9%", "47%"),
                new ReturnsModel("52", "-0.5%", "45%"),
                new ReturnsModel("54", "-0.5%", "48%"),
                new ReturnsModel("56", "-0.1%", "49%"),
                new ReturnsModel("58", "-0.1%", "51%"),
                new ReturnsModel("60", "-0.2%", "51%")
        );

        tableView.getStyleClass().add("edge-to-edge");
        tableView.getStyleClass().add("noborder");
        tableView.setItems(data);
    }

    private void setAreaChart_1() {
        PeriodNumber_1.setForceZeroInRange(false);
        XYChart.Series series = new XYChart.Series();

        series.getData().add(new XYChart.Data<>(2, 0.8));
        series.getData().add(new XYChart.Data<>(4, 2.9));
        series.getData().add(new XYChart.Data<>(6, 3.0));
        series.getData().add(new XYChart.Data<>(8, 2.7));
        series.getData().add(new XYChart.Data<>(10, 2.5));
        series.getData().add(new XYChart.Data<>(12, 1.2));
        series.getData().add(new XYChart.Data<>(14, 0.8));
        series.getData().add(new XYChart.Data<>(16, 0.2));
        series.getData().add(new XYChart.Data<>(18, -0.1));
        series.getData().add(new XYChart.Data<>(20, -0.1));
        series.getData().add(new XYChart.Data<>(22, -0.1));
        series.getData().add(new XYChart.Data<>(24, -0.7));
        series.getData().add(new XYChart.Data<>(26, -0.8));
        series.getData().add(new XYChart.Data<>(28, -1.1));
        series.getData().add(new XYChart.Data<>(30, -1.0));
        series.getData().add(new XYChart.Data<>(32, -1.0));
        series.getData().add(new XYChart.Data<>(34, -1.5));
        series.getData().add(new XYChart.Data<>(36, -1.5));
        series.getData().add(new XYChart.Data<>(38, -1.1));
        series.getData().add(new XYChart.Data<>(40, -1.7));
        series.getData().add(new XYChart.Data<>(42, -1.4));
        series.getData().add(new XYChart.Data<>(44, -1.6));
        series.getData().add(new XYChart.Data<>(46, -1.4));
        series.getData().add(new XYChart.Data<>(48, -1.0));
        series.getData().add(new XYChart.Data<>(50, -0.9));
        series.getData().add(new XYChart.Data<>(52, -0.5));
        series.getData().add(new XYChart.Data<>(54, -0.5));
        series.getData().add(new XYChart.Data<>(56, -0.1));
        series.getData().add(new XYChart.Data<>(58, -0.1));
        series.getData().add(new XYChart.Data<>(60, -0.2));

        areaChart_1.setHorizontalZeroLineVisible(true);
        areaChart_1.getData().addAll(series);
    }

    private void setAreaChart_2() {
        PeriodNumber_2.setForceZeroInRange(false);
        XYChart.Series series = new XYChart.Series();

        series.getData().add(new XYChart.Data<>(2, 58));
        series.getData().add(new XYChart.Data<>(4, 65));
        series.getData().add(new XYChart.Data<>(6, 65));
        series.getData().add(new XYChart.Data<>(8, 58));
        series.getData().add(new XYChart.Data<>(10, 60));
        series.getData().add(new XYChart.Data<>(12, 53));
        series.getData().add(new XYChart.Data<>(14, 53));
        series.getData().add(new XYChart.Data<>(16, 48));
        series.getData().add(new XYChart.Data<>(18, 48));
        series.getData().add(new XYChart.Data<>(20, 48));
        series.getData().add(new XYChart.Data<>(22, 52));
        series.getData().add(new XYChart.Data<>(24, 47));
        series.getData().add(new XYChart.Data<>(26, 52));
        series.getData().add(new XYChart.Data<>(28, 44));
        series.getData().add(new XYChart.Data<>(30, 43));
        series.getData().add(new XYChart.Data<>(32, 44));
        series.getData().add(new XYChart.Data<>(34, 43));
        series.getData().add(new XYChart.Data<>(36, 38));
        series.getData().add(new XYChart.Data<>(38, 45));
        series.getData().add(new XYChart.Data<>(40, 43));
        series.getData().add(new XYChart.Data<>(42, 45));
        series.getData().add(new XYChart.Data<>(44, 47));
        series.getData().add(new XYChart.Data<>(46, 48));
        series.getData().add(new XYChart.Data<>(48, 45));
        series.getData().add(new XYChart.Data<>(50, 47));
        series.getData().add(new XYChart.Data<>(52, 45));
        series.getData().add(new XYChart.Data<>(54, 48));
        series.getData().add(new XYChart.Data<>(56, 49));
        series.getData().add(new XYChart.Data<>(58, 51));
        series.getData().add(new XYChart.Data<>(60, 51));

        areaChart_2.setHorizontalZeroLineVisible(true);
        areaChart_2.getData().addAll(series);
    }

    private void setBarChart(){
        barChart.setBarGap(3);
        barChart.setCategoryGap(20);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();

        series1.getData().add(new XYChart.Data<>("2.00%",100));
        series1.getData().add(new XYChart.Data<>("4.00%",90));
        series1.getData().add(new XYChart.Data<>("6.00%",80));
        series1.getData().add(new XYChart.Data<>("8.00%",70));
        series1.getData().add(new XYChart.Data<>("10.00%",60));
        series1.getData().add(new XYChart.Data<>("12.00%",50));
        series1.getData().add(new XYChart.Data<>("14.00%",40));
        series1.getData().add(new XYChart.Data<>("16.00%",30));
        series1.getData().add(new XYChart.Data<>("18.00%",20));
        series1.getData().add(new XYChart.Data<>("20.00%",10));


        series2.getData().add(new XYChart.Data<>("2.00%",-100));
        series2.getData().add(new XYChart.Data<>("4.00%",-90));
        series2.getData().add(new XYChart.Data<>("6.00%",-80));
        series2.getData().add(new XYChart.Data<>("8.00%",-70));
        series2.getData().add(new XYChart.Data<>("10.00%",-60));
        series2.getData().add(new XYChart.Data<>("12.00%",-50));
        series2.getData().add(new XYChart.Data<>("14.00%",-40));
        series2.getData().add(new XYChart.Data<>("16.00%",-30));
        series2.getData().add(new XYChart.Data<>("18.00%",-20));
        series2.getData().add(new XYChart.Data<>("20.00%",-10));

        barChart.getData().clear();
        barChart.layout();
        barChart.getData().addAll(series1, series2);
        barChart.setAnimated(false);
    }

    public void ReturnsController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    public void setMain(Main main, Net net) {
//        setComboBox();
        setTableView();
        setAreaChart_1();
        setAreaChart_2();
        setBarChart();
        setCumulativeTableView();
        setLineChart();
        this.main = main;
        this.net = net;

    }
}
