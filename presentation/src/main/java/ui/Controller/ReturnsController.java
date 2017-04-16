package ui.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import quantour.vo.StockVO;
import quantour.vo.StockSetVO;
import quantour.vo.StrategyDataVO;
import ui.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by Administrator on 2017/3/24.
 */
public class ReturnsController implements Initializable {
    private Main main;

    private Net net;

    //持有期个数
    private int number;

    //是否自选
    private boolean isyourchoice;

    //自选股票名
    private ArrayList<String> stockNameList = new ArrayList<String>();

    //自选股票代码，两者不对应
    private ArrayList<String> stockCodeList = new ArrayList<String>();

    //板块名
    private ArrayList<String> sectionNameList = new ArrayList<String>();

    //股票表格
    @FXML
    private TableView<StockModel> StockTableView;

    //第几个持有期
    @FXML
    private ComboBox<String> HoldPeriodRank;

    //股票排名
    @FXML
    private TableColumn<StockModel, String> StockRank;

    //股票名
    @FXML
    private TableColumn<StockModel, String> StockName;

    //股票代码
    @FXML
    private TableColumn<StockModel, String> StockCode;

    @FXML
    private TableView<StockModel> stockTable;


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
    private double annualReturn;//年化收益率

    //基准年化收益率
    @FXML
    private TableColumn<CumulativeReturnsModel, String> standardYearReturns;
    private double basicAnnualReturn;//基准年化收益率

    //阿尔法
    @FXML
    private TableColumn<CumulativeReturnsModel, String> alpha;
    private double alphaNum;

    //贝塔
    @FXML
    private TableColumn<CumulativeReturnsModel, String> beta;
    private double betaNum;

    //夏普比率
    @FXML
    private TableColumn<CumulativeReturnsModel, String> sharp;
    private double sharpeRatio;

    //最大回撤
    @FXML
    private TableColumn<CumulativeReturnsModel, String> retreats;
    private double maxDrawDown;//最大回撤

    private ObservableList<CumulativeReturnsModel> cumulativeData;

    @FXML
    private NumberAxis returnsPercent = new NumberAxis();

    @FXML
    private CategoryAxis date = new CategoryAxis();

    @FXML
    private LineChart<String, Number> lineChart = new LineChart<String, Number>(date, returnsPercent);

    @FXML
    private Tab MomentumStrategyTab;

    @FXML
    private Tab MeanReversioTab;


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
    private void gotoSelectStock() {
        main.gotoSelectStock(this);
    }

    /**
     * 从股票选择页面返回后显示所选股票
     *
     * @param stockNameList
     */
    public void setSelectStockComboBox(ArrayList<String> stockNameList, ArrayList<String> stockCodeList) {
        if (!isyourchoice) {
            Plate_MeanReversio.getItems().clear();
            Plate_MomentumStrategy.getItems().clear();
        }
        Plate_MomentumStrategy.getItems().addAll(stockNameList);
        Plate_MomentumStrategy.setValue(stockNameList.get(0));
        Plate_MeanReversio.getItems().addAll(stockNameList);
        Plate_MeanReversio.setValue(stockNameList.get(0));
        isyourchoice = true;
        this.stockNameList = stockNameList;
        this.stockCodeList = stockCodeList;
    }

    public void setSectionComboBox(ArrayList<String> sectionNameList) {
        if (isyourchoice) {
            Plate_MeanReversio.getItems().clear();
            Plate_MomentumStrategy.getItems().clear();
        }
        Plate_MomentumStrategy.getItems().addAll(sectionNameList);
        Plate_MomentumStrategy.setValue(sectionNameList.get(0));
        Plate_MeanReversio.getItems().addAll(sectionNameList);
        Plate_MeanReversio.setValue(sectionNameList.get(0));
        isyourchoice = false;
        this.sectionNameList = sectionNameList;
    }

    /*
    这里是自选股票板块情况（表格）

     */

    private void setStockComboBox() {
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

    private void setHeldComboBox() {
        ObservableList<String> HeldPeriod = FXCollections.observableArrayList();
        List<String> HeldPeriodName = new ArrayList<>();
    }

    public void setStartDatePicker_MomentumStrategy() {
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

    public void setEndDatePicker_MomentumStrategy() {
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

    //将datepicker获取的时间转为date类
    public Date changeDateStyle(LocalDate localDate) {

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);

        return date;
    }

    //这里是股票表格，包括股票排名，股票名和股票代码
    private void setStockTableView(ArrayList<StockVO> stockVOList) {
        ObservableList<StockModel> models = FXCollections.observableArrayList();
        //填充第几个持有期的combobox
        ObservableList<String> HoldPeriod = FXCollections.observableArrayList();
        List<String> HoldPeriodString = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            HoldPeriodString.add("第" + i + "个持有期");
        }
        HoldPeriod.addAll(HoldPeriodString);
        HoldPeriodRank.setItems(HoldPeriod);

        StockRank.setCellValueFactory(celldata -> celldata.getValue().rankProperty());
        StockRank.setCellFactory(new Callback<TableColumn<StockModel, String>, TableCell<StockModel, String>>() {

            @Override
            public TableCell<StockModel, String> call(TableColumn<StockModel, String> param) {
                TextFieldTableCell<StockModel, String> cell = new TextFieldTableCell<>();

                cell.setOnMouseClicked((MouseEvent t) -> {
                    String name = (stockTable.getItems().get(cell.getIndex()).getName());
                    String code = (stockTable.getItems().get(cell.getIndex()).getID());
                    if (t.getClickCount() == 2) {
                        if (cell.getIndex() <= models.size()) {
                            //
                            System.out.print("2");
                            if (MomentumStrategyTab.isSelected()) {
                                main.gotoCandlestickChart(name, code, StartDate_MomentumStrategy.getValue(), EndDate_MomentumStrategy.getValue());
                            } else {
                                main.gotoCandlestickChart(name, code, StartDate_MeanReversio.getValue(), EndDate_MeanReversio.getValue());
                            }
                        }
                    }
                });
                return cell;
            }
        });

        StockName.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
        StockName.setCellFactory(new Callback<TableColumn<StockModel, String>, TableCell<StockModel, String>>() {

            @Override
            public TableCell<StockModel, String> call(TableColumn<StockModel, String> param) {
                TextFieldTableCell<StockModel, String> cell = new TextFieldTableCell<>();

                cell.setOnMouseClicked((MouseEvent t) -> {
                    String name = (stockTable.getItems().get(cell.getIndex()).getName());
                    String code = (stockTable.getItems().get(cell.getIndex()).getID());
                    if (t.getClickCount() == 2) {
                        if (cell.getIndex() <= models.size()) {
                            //
                            System.out.print("2");
                            if (MomentumStrategyTab.isSelected()) {
                                main.gotoCandlestickChart(name, code, StartDate_MomentumStrategy.getValue(), EndDate_MomentumStrategy.getValue());
                            } else {
                                main.gotoCandlestickChart(name, code, StartDate_MeanReversio.getValue(), EndDate_MeanReversio.getValue());
                            }
                        }
                    }
                });
                return cell;
            }
        });

        StockCode.setCellValueFactory(celldata -> celldata.getValue().idProperty());
        StockCode.setCellFactory(new Callback<TableColumn<StockModel, String>, TableCell<StockModel, String>>() {

            @Override
            public TableCell<StockModel, String> call(TableColumn<StockModel, String> param) {
                TextFieldTableCell<StockModel, String> cell = new TextFieldTableCell<>();

                cell.setOnMouseClicked((MouseEvent t) -> {
                    String name = (stockTable.getItems().get(cell.getIndex()).getName());
                    String code = (stockTable.getItems().get(cell.getIndex()).getID());
                    if (t.getClickCount() == 2) {
                        if (cell.getIndex() <= models.size()) {
                            //
                            System.out.print("2");
                            if (MomentumStrategyTab.isSelected()) {
                                main.gotoCandlestickChart(name, code, StartDate_MomentumStrategy.getValue(), EndDate_MomentumStrategy.getValue());
                            } else {
                                main.gotoCandlestickChart(name, code, StartDate_MeanReversio.getValue(), EndDate_MeanReversio.getValue());
                            }
                        }
                    }
                });
                return cell;
            }
        });

        for (int i = 0; i < stockVOList.size(); i++) {
            StockVO stockVO = stockVOList.get(i);
            StockModel stockModel = stockVOtoStockModle(stockVO);
            models.add(stockModel);
        }
        stockTable.setItems(models);
    }

    public StockModel stockVOtoStockModle(StockVO stockVO) {
        StockModel model = new StockModel();
        model.setName(stockVO.getName());
        int code = stockVO.getCode();
        String code1 = String.valueOf(code);
        char[] code2 = code1.toCharArray();
        int t = 6 - code2.length;
        for (int i = 0; i < t; i++) {
            code1 = "0" + code1;
        }
        model.setID(Integer.parseInt(code1));
//        model.setRank(String.valueOf());
//
//        double[] low = stockVO.getLow();
//        double minTemp = low[0];
////        System.out.print("dhaudgaygduyagd"+minTemp);
//        for (int i = 0; i < low.length; i++) {
//            if (low[i] < minTemp) {
//                minTemp = low[i];
//            }
//        }
//
//        model.setMinPrice(minTemp);
//
//        double[] high = stockVO.getHigh();
//        double maxTemp = high[0];
//        for (int i = 0; i < high.length; i++) {
//            if (low[i] > maxTemp) {
//                maxTemp = high[i];
//            }
//        }
//        model.setMaxPrice(maxTemp);
//        double dd = 2.00;
//        double riseAndDown = (stockVO.getClose()[stockVO.getClose().length - 1] - stockVO.getClose()[0]) / stockVO.getClose()[0];
//        riseAndDown = riseAndDown * 100;
//        DecimalFormat df = new DecimalFormat("#.00");
//        model.setRiseAndDown(df.format(riseAndDown) + "%");
//        double d = stockVO.getVariance();
//        BigDecimal bd = new BigDecimal(d);
//        DecimalFormat df2 = new DecimalFormat("0.0000");
//        model.setVariance(df2.format(d));

        return model;
    }

    private void setMomentumStrategyInputSearch() {
        LocalDate StartDate_MS = StartDate_MomentumStrategy.getValue();
        LocalDate EndDate_MS = EndDate_MomentumStrategy.getValue();

        SimpleDateFormat simpleDateFormat_1 = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MS = simpleDateFormat_1.format(this.changeDateStyle(StartDate_MS));
        String EndDateString_MS = simpleDateFormat_1.format(this.changeDateStyle(EndDate_MS));
        if (FormativePeriod_MomentumStrategy.getText() != null && !FormativePeriod_MomentumStrategy.getText().isEmpty()
                && HoldingPeriod_MomentumStrategy.getText() != null && !HoldingPeriod_MomentumStrategy.getText().isEmpty()
                && StockheldInHouse_MomentumStrategy.getText() != null && !StockheldInHouse_MomentumStrategy.getText().isEmpty()) {
            net.actionPerformed("Strategy\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                    + FormativePeriod_MomentumStrategy.getText() + "\t" + "T\t" + HoldingPeriod_MomentumStrategy.getText() + "\t" + StockheldInHouse_MomentumStrategy.getText() + "\t");
        }

        String ReturnsMessage;
        ReturnsMessage = net.run();
        if (ReturnsMessage == null) {
            System.out.println("No data on that day!");
        } else {
            System.out.println(ReturnsMessage + "getted");
            //json字符串转成MarketVO类型
            JsonUtil jsonUtil = new JsonUtil();
            StrategyDataVO StrategyDataVO_middleState = new StrategyDataVO();
            StrategyDataVO StrategyDataVO = (StrategyDataVO) jsonUtil.JSONToObj(ReturnsMessage, StrategyDataVO_middleState.getClass());

            annualReturn = StrategyDataVO.getAnnualReturn();

            basicAnnualReturn = StrategyDataVO.getBasicAnnualReturn();

            alphaNum = StrategyDataVO.getAlpha();

            betaNum = StrategyDataVO.getBeta();

            sharpeRatio = StrategyDataVO.getSharpeRatio();

            maxDrawDown = StrategyDataVO.getMaxDrawDown();

            setCumulativeTableView();

            List<Double> profits = StrategyDataVO.getProfits();

            List<Double> basicProfits = StrategyDataVO.getBasicProfits();

            number = profits.size();//形成期+持有期的个数

            long period = (this.changeDateStyle(EndDate_MS).getTime() - this.changeDateStyle(StartDate_MS).getTime()) / number;

            SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat("yyyy-MM");

            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();

            for (int i = 0; i < number; i++) {
                String time = simpleDateFormat_2.format(this.changeDateStyle(StartDate_MS).getTime() + i * period);
                series1.getData().add(new XYChart.Data<>(time, profits.get(i)));
                series2.getData().add(new XYChart.Data<>(time, basicProfits.get(i)));
            }

            lineChart.getData().addAll(series1, series2);

            List<Double> relativeProfits = new ArrayList<>();

            int[] frequentNumber = new int[10];
            for (int i = 0;i<10;i++){
                frequentNumber[i] = 0;
            }
            for (int i = 0; i < number; i++) {
                relativeProfits.add(profits.get(i)-basicProfits.get(i));
            }
            for (int i = 0;i<number;i++){
                if(relativeProfits.get(i)<=0.015&&relativeProfits.get(i)>=0){
                    frequentNumber[0]++;
                }else if(relativeProfits.get(i)>0.015&&relativeProfits.get(i)<+0.025){
                    frequentNumber[1]++;
                }else if(relativeProfits.get(i)>0.025&&relativeProfits.get(i)<+0.035){
                    frequentNumber[2]++;
                }else if(relativeProfits.get(i)>0.035&&relativeProfits.get(i)<+0.045){
                    frequentNumber[3]++;
                }else if(relativeProfits.get(i)>0.045){
                    frequentNumber[4]++;
                }else if(relativeProfits.get(i)>-0.015&&relativeProfits.get(i)<0){
                    frequentNumber[5]++;
                }else if(relativeProfits.get(i)>-0.025&&relativeProfits.get(i)<-0.015){
                    frequentNumber[6]++;
                }else if(relativeProfits.get(i)>-0.035&&relativeProfits.get(i)<-0.025){
                    frequentNumber[7]++;
                }else if(relativeProfits.get(i)>-0.045&&relativeProfits.get(i)<-0.035){
                    frequentNumber[8]++;
                }else if(relativeProfits.get(i)<-0.045){
                    frequentNumber[9]++;
                }
            }

            //以下是相对收益指数图表的数据
            barChart.setBarGap(3);
            barChart.setCategoryGap(20);
            XYChart.Series<String, Number> series3 = new XYChart.Series<>();
            XYChart.Series<String, Number> series4 = new XYChart.Series<>();

            series3.getData().add(new XYChart.Data<>("1.00%",frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("2.00%",frequentNumber[1]));
            series3.getData().add(new XYChart.Data<>("3.00%",frequentNumber[2]));
            series3.getData().add(new XYChart.Data<>("4.00%",frequentNumber[3]));
            series3.getData().add(new XYChart.Data<>("5.00%",frequentNumber[4]));

            series4.getData().add(new XYChart.Data<>("1.00%",-frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("2.00%",-frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("3.00%",-frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("4.00%",-frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("5.00%",-frequentNumber[0]));

            barChart.getData().clear();
            barChart.layout();
            barChart.getData().addAll(series3, series4);
            barChart.setAnimated(false);
        }
    }

    private void setMeanReversioInputSearch() {
        LocalDate StartDate_MR = StartDate_MeanReversio.getValue();
        LocalDate EndDate_MR = EndDate_MeanReversio.getValue();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MR = simpleDateFormat.format(this.changeDateStyle(StartDate_MR));
        String EndDateString_MR = simpleDateFormat.format(this.changeDateStyle(EndDate_MR));
        if (FormativePeriod_MeanReversio.getText() != null && !FormativePeriod_MeanReversio.getText().isEmpty()
                && HoldingPeriod_MeanReversio.getText() != null && !HoldingPeriod_MeanReversio.getText().isEmpty()
                && StockHeldInHouse_MeanReversio.getText() != null && !StockHeldInHouse_MeanReversio.getText().isEmpty()) {
            net.actionPerformed("Strategy\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                    + FormativePeriod_MomentumStrategy.getText() + "\t" + "T\t" + HoldingPeriod_MomentumStrategy.getText() + "\t" + StockheldInHouse_MomentumStrategy.getText() + "\t");
        }
        String ReturnsMessage;
        ReturnsMessage = net.run();
        if (ReturnsMessage == null) {
            System.out.println("No data on that day!");
        } else {
            System.out.println(ReturnsMessage + "getted");
            //json字符串转成MarketVO类型
            JsonUtil jsonUtil = new JsonUtil();
            StrategyDataVO StrategyDataVO_middleState = new StrategyDataVO();
            StrategyDataVO StrategyDataVO = (StrategyDataVO) jsonUtil.JSONToObj(ReturnsMessage, StrategyDataVO_middleState.getClass());

            annualReturn = StrategyDataVO.getAnnualReturn();

            basicAnnualReturn = StrategyDataVO.getBasicAnnualReturn();

            alphaNum = StrategyDataVO.getAlpha();

            betaNum = StrategyDataVO.getBeta();

            sharpeRatio = StrategyDataVO.getSharpeRatio();

            maxDrawDown = StrategyDataVO.getMaxDrawDown();

            setCumulativeTableView();

            List<Double> profits = StrategyDataVO.getProfits();

            List<Double> basicProfits = StrategyDataVO.getBasicProfits();

            int number = profits.size();//形成期+持有期的个数

            long period = (this.changeDateStyle(EndDate_MR).getTime() - this.changeDateStyle(StartDate_MR).getTime()) / number;

            SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat("yyyy-MM");

            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();

            for (int i = 0; i < number; i++) {
                String time = simpleDateFormat_2.format(this.changeDateStyle(StartDate_MR).getTime() + i * period);
                series1.getData().add(new XYChart.Data<>(time, profits.get(i)));
                series2.getData().add(new XYChart.Data<>(time, basicProfits.get(i)));
            }

            lineChart.getData().addAll(series1, series2);

            List<Double> relativeProfits = new ArrayList<>();

            int[] frequentNumber = new int[10];
            for (int i = 0;i<10;i++){
                frequentNumber[i] = 0;
            }
            for (int i = 0; i < number; i++) {
                relativeProfits.add(profits.get(i)-basicProfits.get(i));
            }
            for (int i = 0;i<number;i++){
                if(relativeProfits.get(i)<=0.015&&relativeProfits.get(i)>=0){
                    frequentNumber[0]++;
                }else if(relativeProfits.get(i)>0.015&&relativeProfits.get(i)<+0.025){
                    frequentNumber[1]++;
                }else if(relativeProfits.get(i)>0.025&&relativeProfits.get(i)<+0.035){
                    frequentNumber[2]++;
                }else if(relativeProfits.get(i)>0.035&&relativeProfits.get(i)<+0.045){
                    frequentNumber[3]++;
                }else if(relativeProfits.get(i)>0.045){
                    frequentNumber[4]++;
                }else if(relativeProfits.get(i)>-0.015&&relativeProfits.get(i)<0){
                    frequentNumber[5]++;
                }else if(relativeProfits.get(i)>-0.025&&relativeProfits.get(i)<-0.015){
                    frequentNumber[6]++;
                }else if(relativeProfits.get(i)>-0.035&&relativeProfits.get(i)<-0.025){
                    frequentNumber[7]++;
                }else if(relativeProfits.get(i)>-0.045&&relativeProfits.get(i)<-0.035){
                    frequentNumber[8]++;
                }else if(relativeProfits.get(i)<-0.045){
                    frequentNumber[9]++;
                }
            }

            //以下是相对收益指数图表的数据
            barChart.setBarGap(3);
            barChart.setCategoryGap(20);
            XYChart.Series<String, Number> series3 = new XYChart.Series<>();
            XYChart.Series<String, Number> series4 = new XYChart.Series<>();

            series3.getData().add(new XYChart.Data<>("1.00%",frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("2.00%",frequentNumber[1]));
            series3.getData().add(new XYChart.Data<>("3.00%",frequentNumber[2]));
            series3.getData().add(new XYChart.Data<>("4.00%",frequentNumber[3]));
            series3.getData().add(new XYChart.Data<>("5.00%",frequentNumber[4]));

            series4.getData().add(new XYChart.Data<>("1.00%",-frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("2.00%",-frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("3.00%",-frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("4.00%",-frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("5.00%",-frequentNumber[0]));

            barChart.getData().clear();
            barChart.layout();
            barChart.getData().addAll(series3, series4);
            barChart.setAnimated(false);
        }
    }

    private void setCumulativeTableView() {
        yearReturns.setCellValueFactory(celldata -> celldata.getValue().yearReturnsProperty());
        standardYearReturns.setCellValueFactory(celldata -> celldata.getValue().standardYearReturnsProperty());
        alpha.setCellValueFactory(celldata -> celldata.getValue().alphaProperty());
        beta.setCellValueFactory(celldata -> celldata.getValue().betaProperty());
        sharp.setCellValueFactory(celldata -> celldata.getValue().sharpProperty());
        retreats.setCellValueFactory(celldata -> celldata.getValue().retreatsProperty());

        //测试数据，数据层完成后将修改
        cumulativeData = FXCollections.observableArrayList(
                new CumulativeReturnsModel(String.valueOf(annualReturn), String.valueOf(basicAnnualReturn),
                        String.valueOf(alphaNum), String.valueOf(betaNum), String.valueOf(sharpeRatio), String.valueOf(maxDrawDown))
        );

        cumulativeTableView.getStyleClass().add("edge-to-edge");
        cumulativeTableView.getStyleClass().add("noborder");
        cumulativeTableView.setItems(cumulativeData);
    }

//    private void setLineChart() {
//        XYChart.Series series1 = new XYChart.Series();
//        series1.getData().add(new XYChart.Data<>("2005-07", 10));
//        series1.getData().add(new XYChart.Data<>("2006-01", 30));
//        series1.getData().add(new XYChart.Data<>("2006-07", 40));
//        series1.getData().add(new XYChart.Data<>("2007-01", 50));
//        series1.getData().add(new XYChart.Data<>("2007-07", 70));
//        series1.getData().add(new XYChart.Data<>("2008-01", 80));
//        series1.getData().add(new XYChart.Data<>("2008-07", 100));
//        series1.getData().add(new XYChart.Data<>("2009-01", 110));
//        series1.getData().add(new XYChart.Data<>("2009-07", 130));
//        series1.getData().add(new XYChart.Data<>("2010-01", 120));
//        series1.getData().add(new XYChart.Data<>("2010-07", 100));
//        series1.getData().add(new XYChart.Data<>("2011-01", 90));
//        series1.getData().add(new XYChart.Data<>("2011-07", 70));
//        series1.getData().add(new XYChart.Data<>("2012-01", 80));
//        series1.getData().add(new XYChart.Data<>("2012-07", 100));
//        series1.getData().add(new XYChart.Data<>("2013-01", 120));
//        series1.getData().add(new XYChart.Data<>("2013-07", 150));
//        series1.getData().add(new XYChart.Data<>("2014-01", 190));
//
//        XYChart.Series series2 = new XYChart.Series();
//        series2.getData().add(new XYChart.Data<>("2005-07", 10));
//        series2.getData().add(new XYChart.Data<>("2006-01", 15));
//        series2.getData().add(new XYChart.Data<>("2006-07", 20));
//        series2.getData().add(new XYChart.Data<>("2007-01", 25));
//        series2.getData().add(new XYChart.Data<>("2007-07", 35));
//        series2.getData().add(new XYChart.Data<>("2008-01", 40));
//        series2.getData().add(new XYChart.Data<>("2008-07", 50));
//        series2.getData().add(new XYChart.Data<>("2009-01", 55));
//        series2.getData().add(new XYChart.Data<>("2009-07", 65));
//        series2.getData().add(new XYChart.Data<>("2010-01", 60));
//        series2.getData().add(new XYChart.Data<>("2010-07", 50));
//        series2.getData().add(new XYChart.Data<>("2011-01", 45));
//        series2.getData().add(new XYChart.Data<>("2011-07", 35));
//        series2.getData().add(new XYChart.Data<>("2012-01", 40));
//        series2.getData().add(new XYChart.Data<>("2012-07", 50));
//        series2.getData().add(new XYChart.Data<>("2013-01", 60));
//        series2.getData().add(new XYChart.Data<>("2013-07", 75));
//        series2.getData().add(new XYChart.Data<>("2014-01", 95));
//
//        lineChart.getData().addAll(series1, series2);
//    }


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

//    private void setBarChart() {
//        barChart.setBarGap(3);
//        barChart.setCategoryGap(20);
//        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
//        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
//
//        series1.getData().add(new XYChart.Data<>("2.00%", 100));
//        series1.getData().add(new XYChart.Data<>("4.00%", 90));
//        series1.getData().add(new XYChart.Data<>("6.00%", 80));
//        series1.getData().add(new XYChart.Data<>("8.00%", 70));
//        series1.getData().add(new XYChart.Data<>("10.00%", 60));
//        series1.getData().add(new XYChart.Data<>("12.00%", 50));
//        series1.getData().add(new XYChart.Data<>("14.00%", 40));
//        series1.getData().add(new XYChart.Data<>("16.00%", 30));
//        series1.getData().add(new XYChart.Data<>("18.00%", 20));
//        series1.getData().add(new XYChart.Data<>("20.00%", 10));
//
//
//        series2.getData().add(new XYChart.Data<>("2.00%", -100));
//        series2.getData().add(new XYChart.Data<>("4.00%", -90));
//        series2.getData().add(new XYChart.Data<>("6.00%", -80));
//        series2.getData().add(new XYChart.Data<>("8.00%", -70));
//        series2.getData().add(new XYChart.Data<>("10.00%", -60));
//        series2.getData().add(new XYChart.Data<>("12.00%", -50));
//        series2.getData().add(new XYChart.Data<>("14.00%", -40));
//        series2.getData().add(new XYChart.Data<>("16.00%", -30));
//        series2.getData().add(new XYChart.Data<>("18.00%", -20));
//        series2.getData().add(new XYChart.Data<>("20.00%", -10));
//
//        barChart.getData().clear();
//        barChart.layout();
//        barChart.getData().addAll(series1, series2);
//        barChart.setAnimated(false);
//    }

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
//        setBarChart();
        setCumulativeTableView();
//        setLineChart();
        this.main = main;
        this.net = net;

    }
}
