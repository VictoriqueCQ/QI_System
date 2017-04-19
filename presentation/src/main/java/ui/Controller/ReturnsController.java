package ui.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import net.sf.json.JSONObject;
import quantour.vo.FormativeNHoldingVO;
import quantour.vo.StockSetVO;
import quantour.vo.StrategyDataVO;
import ui.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


/**
 * Created by Administrator on 2017/3/24.
 */
public class ReturnsController implements Initializable {
    /*
    * 以下4个变量是应用于全局的重要变量
    * 用于类之间交互的main
    * 用于类之间交互的net
    * 用于表格和图表数据填充的strategyDataVO
     */
    private Main main;

    private Net net;

    private StrategyDataVO strategyDataVO_MS;

    private StrategyDataVO strategyDataVO_MR;

    @FXML
    private Tab chaoetab;

    //是否自选
    private boolean isyourchoice;





    /*
    * 以下5个变量是用于起始界面的变量，分别是：
    * 用于确定持有期个数的number
    * 用于得到股票名称列表的stockNameList
    * 用于得到股票代码列表的stockCodeList
    * 用于获得板块名列表的sectionNameList
    * 用于确定持有期个数的HoldPeriodRank

     */
    //持有期个数
    private int number;

    //自选股票名
    private ArrayList<String> stockNameList = new ArrayList<String>();

    //自选股票代码，两者不对应
    private ArrayList<String> stockCodeList = new ArrayList<String>();

    //板块名
    private ArrayList<String> sectionNameList = new ArrayList<String>();

    //第几个持有期
    @FXML
    private ComboBox<String> HoldPeriodRank;

    private ObservableList<String> HoldPeriod;

    /*
    * 以下4个变量是用于股票表格的，分别是
    * 用于显示股票排名的StockRank
    * 用于显示股票名称的StockName
    * 用于显示股票代码的StockCode
    * 用于表格的stockTable
     */
    //股票排名
    @FXML
    private TableColumn<StockModel, Integer> StockRank;

    //股票名
    @FXML
    private TableColumn<StockModel, String> StockName;

    //股票代码
    @FXML
    private TableColumn<StockModel, String> StockCode;

    //股票表格
    @FXML
    private TableView<StockModel> stockTable;


    /*
    *以下是累计收益率中的变量
    * 分别是
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
    //选择形成期还是持有期
    @FXML
    private ComboBox<String> ChooseFPorHP_MS;
    @FXML
    private ComboBox<String> ChooseFPorHP_MR;


    @FXML
    private void gotoSelectStock() {
        main.gotoSelectStock(this, stockCodeList);
    }

    /**
     * 在开始时间选取后更新结束时间可选日期
     */
    @FXML
    private void updateEndTimeDatePicker1() {
        final Callback<DatePicker, DateCell> dayCellFactory1 =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        StartDate_MomentumStrategy.getValue().plusDays(1))
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

    /**
     * 在结束时间选取后更新开始时间可选日期
     */
    @FXML
    private void updateStartTimeDatePicker1() {
        final Callback<DatePicker, DateCell> dayCellFactory1 =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isAfter(
                                        EndDate_MomentumStrategy.getValue().minusDays(1))
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
        StartDate_MomentumStrategy.setDayCellFactory(dayCellFactory1);
    }

    /**
     * 在开始时间选取后更新结束时间可选日期
     */
    @FXML
    private void updateEndTimeDatePicker2() {
        final Callback<DatePicker, DateCell> dayCellFactory1 =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        StartDate_MeanReversio.getValue().plusDays(1))
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

    /**
     * 在结束时间选取后更新开始时间可选日期
     */
    @FXML
    private void updateStartTimeDatePicker2() {
        final Callback<DatePicker, DateCell> dayCellFactory1 =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isAfter(
                                        EndDate_MeanReversio.getValue().minusDays(1))
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
        StartDate_MeanReversio.setDayCellFactory(dayCellFactory1);
    }


    /**
     * 初始化日期选择器可选时间
     */
    private void setDatePicker() {
        StartDate_MomentumStrategy.setValue(LocalDate.of(2014, 2, 1));
        EndDate_MomentumStrategy.setValue(LocalDate.of(2014, 4, 30));
        StartDate_MeanReversio.setValue(LocalDate.of(2014, 2, 1));
        EndDate_MeanReversio.setValue(LocalDate.of(2014, 4, 30));
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
        EndDate_MomentumStrategy.setDayCellFactory(dayCellFactory1);
        StartDate_MeanReversio.setDayCellFactory(dayCellFactory1);
        EndDate_MomentumStrategy.setDayCellFactory(dayCellFactory1);
    }


    /**
     * 从股票选择页面返回后显示所选股票
     *
     * @param stockNameList
     */
    public void setSelectStockComboBox(ArrayList<String> stockNameList, ArrayList<String> stockCodeList) {
        Plate_MeanReversio.getItems().clear();
        Plate_MomentumStrategy.getItems().clear();
        Plate_MomentumStrategy.getItems().addAll(stockNameList);
        Plate_MomentumStrategy.setValue(stockNameList.get(0));
        Plate_MeanReversio.getItems().addAll(stockNameList);
        Plate_MeanReversio.setValue(stockNameList.get(0));
        StockheldInHouse_MomentumStrategy.setText(String.valueOf(stockNameList.size()));
        StockHeldInHouse_MeanReversio.setText(String.valueOf(stockNameList.size()));
        isyourchoice = true;
        this.stockNameList = stockNameList;
        this.stockCodeList = stockCodeList;
    }

    public void setSectionComboBox(ArrayList<String> sectionNameList) {
        Plate_MeanReversio.getItems().clear();
        Plate_MomentumStrategy.getItems().clear();
        Plate_MomentumStrategy.getItems().addAll(sectionNameList);
        Plate_MomentumStrategy.setValue(sectionNameList.get(0));
        Plate_MeanReversio.getItems().addAll(sectionNameList);
        Plate_MeanReversio.setValue(sectionNameList.get(0));
        isyourchoice = false;
        this.sectionNameList = sectionNameList;
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

    /**
     * 表格设置
     */
    private void setStockTableView() {
        List<StockSetVO> stockSetVOS;
        if (MomentumStrategyTab.isSelected()) {
            stockSetVOS = strategyDataVO_MS.getStockSetVOS();
//            stockSetVOS=stockSetVOS1;
        } else {
            stockSetVOS = strategyDataVO_MR.getStockSetVOS();
//            stockSetVOS=stockSetVOS1;
        }

                for(int j=0;j<stockSetVOS.size();j++){
            System.out.println("a"+stockSetVOS.get(j).getStockSets().toString());
        }
        ObservableList<StockModel> stockModels = FXCollections.observableArrayList();

        //填充第几个持有期的combobox,根据stocksetvos的长度
//        HoldPeriod = FXCollections.observableArrayList();
        List<String> HoldPeriodString = new ArrayList<>();
//        HoldPeriodRank.getItems().clear();
//        int t=HoldPeriodRank.getItems().size();
//        HoldPeriodRank.getItems().remove(0,t-1);
        HoldPeriodRank.getItems().clear();

        for (int i = 1; i <= stockSetVOS.size(); i++) {
            HoldPeriodString.add("第" + i + "个持有期");
            HoldPeriodRank.getItems().add("第" + i + "个持有期");

        }
        HoldPeriodRank.setValue("第1个持有期");
//        HoldPeriod.addAll(HoldPeriodString);
//        System.out.print(HoldPeriod.toString());
//        System.out.println(HoldPeriodRank.getItems().toString());
//        HoldPeriodRank=new ComboBox<String>();
//        System.out.println(HoldPeriodRank.getItems().toString());
//        System.out.print(HoldPeriodString.toString());
//
//        HoldPeriodRank.getItems().clear();
//
//        HoldPeriodRank.getItems().addAll(HoldPeriodString);

//        ObservableList<TableColumn> observableList=stockTable.getColumns();




//        StockRank.setCellValueFactory(celldata -> celldata.getValue().rankProperty());
        StockRank.setCellValueFactory(new PropertyValueFactory<StockModel,Integer>("rank"));
//        StockRank.setSortType(TableColumn.SortType.ASCENDING);

//        StockRank.setCellFactory(new PropertyValueFactory<StockModel,Integer>("rankProperty"));
        StockRank.setCellFactory(new Callback<TableColumn<StockModel,Integer>, TableCell<StockModel, Integer>>() {

            @Override
            public TableCell<StockModel, Integer> call(TableColumn<StockModel, Integer> param) {
                TextFieldTableCell<StockModel, Integer> cell = new TextFieldTableCell<>();

                cell.setOnMouseClicked((MouseEvent t) -> {
                    String name = (stockTable.getItems().get(cell.getIndex()).getName());
                    String code = (stockTable.getItems().get(cell.getIndex()).getID());
                    if (t.getClickCount() == 2) {
                        if (cell.getIndex() <= stockModels.size()) {
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
                        if (cell.getIndex() <= stockModels.size()) {
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
                        if (cell.getIndex() <= stockModels.size()) {
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



        //默认展示第一个持有期
        ArrayList<StockModel> stockModelArrayList = this.getbeststock(stockSetVOS, 1);
        for (int i = 0; i < stockModelArrayList.size(); i++) {
            stockModels.add(stockModelArrayList.get(i));
        }
        stockTable.setItems(stockModels);

    }


    /**
     * 根据第几个持有期展示最终表格
     */
    @FXML
    private void showTabelByHoldPeriod() {
        String holdPeriod = HoldPeriodRank.getValue();
//        char[] h = holdPeriod.toCharArray();
//        int time = h[1]-48;
        int time=0;
        for(int i=0;i<HoldPeriodRank.getItems().size();i++){
            if(holdPeriod.equals(HoldPeriodRank.getItems().get(i))){
                time=i+1;
                break;
            }
        }
        System.out.println(time);
        List<StockSetVO> stockSetVOS = strategyDataVO_MS.getStockSetVOS();
//        for(int i=0;i<stockSetVOS.size();i++){
////            System.out.println("c"+stockSetVOS.get(i).getStockSets().toString());
//        }
        ObservableList<StockModel> stockModels = FXCollections.observableArrayList();
        ArrayList<StockModel> stockModelArrayList = this.getbeststock(stockSetVOS, time);
//        ArrayList<Integer> rankList=new ArrayList<Integer>();
//        for(int i=0;i<stockModelArrayList.size();i++){
//            rankList.add(Integer.parseInt(stockModelArrayList.get(i).getRank()));
//        }

        //TODO
        // 排序
        for(int i=1;i<=stockModelArrayList.size();i++){
            for(int j=0;j<stockModelArrayList.size();j++){
                if(stockModelArrayList.get(j).getRank()==i){
                    System.out.println(i);
                    stockTable.getItems().add(stockModelArrayList.get(j));

//                    stockModels.add(stockModelArrayList.get(j));
                    break;
                }
            }
        }
//        for (int i = 0; i < stockModelArrayList.size(); i++) {
//            stockModels.add(stockModelArrayList.get(i));
//        }
//        stockTable.setItems(stockModels);
        System.out.print("success");
    }

    /**
     * 根据第几个持有期返回对应数据
     *
     * @param stockSetVOS
     * @param i
     */
    private ArrayList<StockModel> getbeststock(List<StockSetVO> stockSetVOS, int i) {
        if(i<1){
            i=1;
        }
        System.out.println("di"+i);
        StockSetVO stockSetVO = stockSetVOS.get(i-1);
//        for(int j=0;j<stockSetVOS.size();j++){
//            System.out.println("a"+stockSetVOS.get(j).getStockSets().toString());
//        }
        Map<String,String> stockSets = stockSetVO.getStockSets();
        ArrayList<StockModel> stockModelArrayList = stockSetstoStockModel(stockSets);
        return stockModelArrayList;
    }


    /**
     * stockSets转ArrayList<StockModel>
     *
     * @param stockSets
     * @return
     */
    private ArrayList<StockModel> stockSetstoStockModel(Map<String,String> stockSets) {
        ArrayList<StockModel> stockModelArrayList = new ArrayList<StockModel>();
        HashMap<String, String> name_code = this.getNameList("presentation/name_code.csv");

        for (Map.Entry<String,String> entry : stockSets.entrySet()) {
                    StockModel model = new StockModel();

            model.setRank(Integer.parseInt(entry.getKey()));
            String code1 = entry.getValue();
            char[] code2 = code1.toCharArray();
            int t = 6 - code2.length;
            for (int j = 0; j < t; j++) {
                code1 = "0" + code1;
            }
            model.setID(code1);
            model.setName(name_code.get(code1));
            stockModelArrayList.add(model);
        }
        return stockModelArrayList;
    }

    /**
     * get a list in the filepath
     *
     * @param filePath
     * @return
     */
    private HashMap<String, String> getNameList(String filePath) {
        List<String> content = new ArrayList<String>();

        content = this.readFile(filePath);

        int nums = content.size();
        HashMap<String, String> name_code = new HashMap<String, String>(nums);

        for (int i = 0; i < nums; i++) {
            String tempName = content.get(i).split("\t")[1];
            String tempCode = content.get(i).split("\t")[0];
            char[] code2 = tempCode.toCharArray();
            int t = 6 - code2.length;
            for (int j = 0; j < t; j++) {
                tempCode = "0" + tempCode;
            }
            name_code.put(tempCode, tempName);
        }

        return name_code;
    }

    private List<String> readFile(String path) {
        List<String> content = new ArrayList<String>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (Exception e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;

    }

    /*
    * 这个方法是使用动量策略时处理用户输入，同时获取股票表格，累计收益率，相对收益指数的数据，将用于查询按钮
     */
    private void setMomentumStrategyInputSearch() {
        LocalDate StartDate_MS = StartDate_MomentumStrategy.getValue();
        LocalDate EndDate_MS = EndDate_MomentumStrategy.getValue();
        SimpleDateFormat simpleDateFormat_1 = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MS = simpleDateFormat_1.format(this.changeDateStyle(StartDate_MS));
        String EndDateString_MS = simpleDateFormat_1.format(this.changeDateStyle(EndDate_MS));
        if (FormativePeriod_MomentumStrategy.getText() != null && !FormativePeriod_MomentumStrategy.getText().isEmpty()
                && HoldingPeriod_MomentumStrategy.getText() != null && !HoldingPeriod_MomentumStrategy.getText().isEmpty()) {
            String instruction;
//            isyourchoice=true;
            if (isyourchoice == true) {
                instruction = "Strategy\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + FormativePeriod_MomentumStrategy.getText() + "\t" + "T\t" + HoldingPeriod_MomentumStrategy.getText() + "\t" + null + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "Strategy\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + FormativePeriod_MomentumStrategy.getText() + "\t" + "F\t" + HoldingPeriod_MomentumStrategy.getText() + "\t"+null+"\t";
                for (int i = 0; i < sectionNameList.size(); i++) {
                    instruction += sectionNameList.get(i) + "\t";
                }
            }
            net.actionPerformed(instruction);
        } else {
            System.out.print("error");
        }


        String ReturnsMessage = net.run();
        if (ReturnsMessage == null) {
            System.out.println("No data on that day!");
        } else {
            System.out.println(ReturnsMessage + "getted");
            //json字符串转成MarketVO类型
            JsonUtil jsonUtil = new JsonUtil();
            StrategyDataVO StrategyDataVO_middleState = new StrategyDataVO();
            strategyDataVO_MS = (StrategyDataVO) jsonUtil.JSONToObj(ReturnsMessage, StrategyDataVO_middleState.getClass());

            //处理map

            ArrayList<StockSetVO> stockSetVOS1=new ArrayList<StockSetVO>();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for(int j=0;j<strategyDataVO_MS.getStockSetVOS().size();j++) {
                String year="";
                String month="";
                String day="";
                HashMap<String,String> hashMap=new HashMap<String,String>();
                Date date=new Date();
                JSONObject jsonObject = JSONObject.fromObject(strategyDataVO_MS.getStockSetVOS().get(j));
                String key = null;
                String value = null;
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    key = keys.next();
                    value = jsonObject.get(key).toString();
                    JSONObject jsonObject1 = JSONObject.fromObject(value);
                    String key1 = null;
                    String value1 = null;

                    Iterator<String> keys1 = jsonObject1.keys();
                    while(keys1.hasNext()){
                        key1=keys1.next();
                        if((!key1.equals("date"))&&(!key1.equals("hours"))&&(!key1.equals("month"))&&(!key1.equals("seconds"))&&(!key1.equals("timezoneOffset"))&&(!key1.equals("year"))&&(!key1.equals("minutes"))&&(!key1.equals("time"))&&(!key1.equals("day"))) {
                            value1 = jsonObject1.get(key1).toString();
                            hashMap.put(key1, value1);
                        }
                        else if(key1.equals("year")){
                            year=jsonObject1.get(key1).toString();
                            int t= Integer.parseInt(year);
                            t+=1900;
                            year=String.valueOf(t);
                            System.out.println(year);
                        }
                        else if(key1.equals("month")){
                            month=jsonObject1.get(key1).toString();
                            System.out.println(month);
                        }
                        else if(key1.equals("date")){
                            day=jsonObject1.get(key1).toString();
                            System.out.println(day);
                        }
                    }
                }
                try {
                    date = format.parse(year + "-"+month+"-"+day);
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                System.out.println(date);
                StockSetVO stockSetVO=new StockSetVO(hashMap);
                stockSetVO.setDate(date);
                stockSetVOS1.add(stockSetVO);
            }
//            for(int i=0;i<stockSetVOS1.size();i++){
//                System.out.println("d"+stockSetVOS1.get(i).getStockSets().toString());
//            }
            strategyDataVO_MS.setStockSetPOS(stockSetVOS1);


            //以下是累计收益率
            annualReturn = ((double)((int)(strategyDataVO_MS.getAnnualReturn()*1000)))/10;

            basicAnnualReturn = ((double)((int)(strategyDataVO_MS.getBasicAnnualReturn()*1000)))/10;

            alphaNum = ((double)((int)(strategyDataVO_MS.getAlpha()*1000)))/10;

            betaNum = ((double)((int)(strategyDataVO_MS.getBeta()*1000)))/1000;

            sharpeRatio = ((double)((int)(strategyDataVO_MS.getSharpeRatio()*1000)))/1000;

            maxDrawDown = ((double)((int)(strategyDataVO_MS.getMaxDrawDown()*1000)))/10;

            setCumulativeTableView();

            List<Double> profits = strategyDataVO_MS.getProfits();

            List<Double> basicProfits = strategyDataVO_MS.getBasicProfits();

            number = profits.size();//形成期+持有期的个数

//            long period = (this.changeDateStyle(EndDate_MS).getTime() - this.changeDateStyle(StartDate_MS).getTime()) / number;
//            long period = Integer.parseInt(HoldingPeriod_MomentumStrategy.getText()) * 24 * 60 * 60;

            SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat("yyyy-MM-dd");

            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();

            List<Double> relativeProfits = new ArrayList<>();//相对收益指数

            String time;
//            System.err.println(strategyDataVO_MS.getStockSetVOS().get(0).getDate());
            Date startTime = this.changeDateStyle(StartDate_MS);
            for (int i = 0; i < number; i++) {
//                System.out.print(strategyDataVO_MS.getStockSetVOS().get(i).toString());
                time = simpleDateFormat_2.format(strategyDataVO_MS.getStockSetVOS().get(i).getDate());
//                time = simpleDateFormat_2.format(startTime);
                series1.getData().add(new XYChart.Data<>(time, profits.get(i)));
                series2.getData().add(new XYChart.Data<>(time, basicProfits.get(i)));
                System.out.println("profits="+profits.get(i));
                System.out.println("basicProfits="+basicProfits.get(i));
                relativeProfits.add(profits.get(i)-basicProfits.get(i));
//                Calendar c = new  GregorianCalendar();
//                c.setTime(startTime);
//                c.add(c.DATE,Integer.parseInt(HoldingPeriod_MomentumStrategy.getText()));
//                startTime = c.getTime();
            }

            lineChart.getData().addAll(series1, series2);
            lineChart.setAnimated(false);


            //以下是相对收益指数
            for (int i = 0;i<relativeProfits.size();i++){
                System.out.println("relativesProfits="+relativeProfits.get(i));
                }

            int[] frequentNumber = new int[10];
            for (int i = 0; i < 10; i++) {
                frequentNumber[i] = 0;
            }

            for (int i = 0; i < number; i++) {
                if (relativeProfits.get(i) <= 0.02 && relativeProfits.get(i) >= 0) {
                    frequentNumber[0]++;
                } else if (relativeProfits.get(i) > 0.02 && relativeProfits.get(i) <= +0.04) {
                    frequentNumber[1]++;
                } else if (relativeProfits.get(i) > 0.04 && relativeProfits.get(i) <= +0.06) {
                    frequentNumber[2]++;
                } else if (relativeProfits.get(i) > 0.06 && relativeProfits.get(i) <= +0.08) {
                    frequentNumber[3]++;
                } else if (relativeProfits.get(i) > 0.08) {
                    frequentNumber[4]++;
                } else if (relativeProfits.get(i) > -0.02 && relativeProfits.get(i) < 0) {
                    frequentNumber[5]++;
                } else if (relativeProfits.get(i) > -0.04 && relativeProfits.get(i) <= -0.02) {
                    frequentNumber[6]++;
                } else if (relativeProfits.get(i) > -0.06 && relativeProfits.get(i) <= -0.04) {
                    frequentNumber[7]++;
                } else if (relativeProfits.get(i) > -0.08 && relativeProfits.get(i) <= -0.06) {
                    frequentNumber[8]++;
                } else if (relativeProfits.get(i) <= -0.08) {
                    frequentNumber[9]++;
                }
            }

            //以下是相对收益指数图表的数据
            barChart.setBarGap(10);
            barChart.setCategoryGap(100);
            XYChart.Series<String, Number> series3 = new XYChart.Series<>();
            XYChart.Series<String, Number> series4 = new XYChart.Series<>();

            series3.getData().add(new XYChart.Data<>("1.00%", frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("3.00%", frequentNumber[1]));
            series3.getData().add(new XYChart.Data<>("5.00%", frequentNumber[2]));
            series3.getData().add(new XYChart.Data<>("7.00%", frequentNumber[3]));
            series3.getData().add(new XYChart.Data<>("9.00%", frequentNumber[4]));
            System.err.println(frequentNumber[4]);
            series4.getData().add(new XYChart.Data<>("1.00%", -frequentNumber[5]));
            series4.getData().add(new XYChart.Data<>("3.00%", -frequentNumber[6]));
            series4.getData().add(new XYChart.Data<>("5.00%", -frequentNumber[7]));
            series4.getData().add(new XYChart.Data<>("7.00%", -frequentNumber[8]));
            series4.getData().add(new XYChart.Data<>("9.00%", -frequentNumber[9]));
            System.err.println(frequentNumber[9]);
            barChart.getData().clear();
            barChart.layout();
            barChart.getData().addAll(series3, series4);
            barChart.setAnimated(false);

            setStockTableView();
        }
    }

    /*
    * 这个方法是使用均值回归时处理用户输入，同时获取股票表格，累计收益率，相对收益指数的数据，将用于查询按钮
     */
    private void setMeanReversioInputSearch() {
        System.out.print(2345);
        LocalDate StartDate_MR = StartDate_MeanReversio.getValue();
        LocalDate EndDate_MR = EndDate_MeanReversio.getValue();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MR = simpleDateFormat.format(this.changeDateStyle(StartDate_MR));
        String EndDateString_MR = simpleDateFormat.format(this.changeDateStyle(EndDate_MR));
        if (FormativePeriod_MeanReversio.getText() != null && !FormativePeriod_MeanReversio.getText().isEmpty()
                && HoldingPeriod_MeanReversio.getText() != null && !HoldingPeriod_MeanReversio.getText().isEmpty()
                && StockHeldInHouse_MeanReversio.getText() != null && !StockHeldInHouse_MeanReversio.getText().isEmpty()) {
            String instruction;
//            isyourchoice=true;
            if (isyourchoice == true) {
                instruction = "Strategy\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + FormativePeriod_MeanReversio.getText() + "\t" + "T\t" + HoldingPeriod_MeanReversio.getText() + "\t"
                        + StockHeldInHouse_MeanReversio.getText() + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "Strategy\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + FormativePeriod_MeanReversio.getText() + "\t" + "F\t" + HoldingPeriod_MeanReversio.getText() + "\t"
                        + StockHeldInHouse_MeanReversio.getText() + "\t";
                for (int i = 0; i < sectionNameList.size(); i++) {
                    instruction += sectionNameList.get(i) + "\t";
                }
            }
            net.actionPerformed(instruction);
            System.out.print(instruction);

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
            strategyDataVO_MR = (StrategyDataVO) jsonUtil.JSONToObj(ReturnsMessage, StrategyDataVO_middleState.getClass());

            //处理map
            ArrayList<StockSetVO> stockSetVOS1=new ArrayList<StockSetVO>();
            Date date=new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            HashMap<String,String> hashMap=new HashMap<String,String>();
            for(int j=0;j<strategyDataVO_MR.getStockSetVOS().size();j++) {
                String year="";
                String month="";
                String day="";
                JSONObject jsonObject = JSONObject.fromObject(strategyDataVO_MR.getStockSetVOS().get(j));
                String key = null;
                String value = null;
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    key = keys.next();
                    value = jsonObject.get(key).toString();
                    JSONObject jsonObject1 = JSONObject.fromObject(value);
                    String key1 = null;
                    String value1 = null;
                    Iterator<String> keys1 = jsonObject1.keys();
                    while(keys1.hasNext()){
                        key1=keys1.next();
                        if((!key1.equals("date"))&&(!key1.equals("hours"))&&(!key1.equals("month"))&&(!key1.equals("seconds"))&&(!key1.equals("timezoneOffset"))&&(!key1.equals("year"))&&(!key1.equals("minutes"))&&(!key1.equals("time"))&&(!key1.equals("day"))) {
                            value1 = jsonObject1.get(key1).toString();
                            hashMap.put(key1, value1);
                        }
                        else if(key1.equals("year")){
                            year=jsonObject1.get(key1).toString();
                            int t= Integer.parseInt(year);
                            t+=1900;
                            year=String.valueOf(t);
                            System.out.println(year);
                        }
                        else if(key1.equals("month")){
                            month=jsonObject1.get(key1).toString();
                            System.out.println(month);
                        }
                        else if(key1.equals("date")){
                            day=jsonObject1.get(key1).toString();
                            System.out.println(day);
                        }
                    }
                }
                try {
                    date = format.parse(year + "-"+month+"-"+day);
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
//                System.out.println(date);
                StockSetVO stockSetVO=new StockSetVO(hashMap);
                stockSetVO.setDate(date);
                stockSetVOS1.add(stockSetVO);
            }

            strategyDataVO_MR.setStockSetPOS(stockSetVOS1);

            annualReturn = ((double)((int)(strategyDataVO_MR.getAnnualReturn()*1000)))/10;

            basicAnnualReturn = ((double)((int)(strategyDataVO_MR.getBasicAnnualReturn()*1000)))/10;

            alphaNum = ((double)((int)(strategyDataVO_MR.getAlpha()*1000)))/10;

            betaNum = ((double)((int)(strategyDataVO_MR.getBeta()*1000)))/1000;

            sharpeRatio = ((double)((int)(strategyDataVO_MR.getSharpeRatio()*1000)))/1000;

            maxDrawDown = ((double)((int)(strategyDataVO_MR.getMaxDrawDown()*1000)))/10;

            setCumulativeTableView();

            List<Double> profits = strategyDataVO_MR.getProfits();

            List<Double> basicProfits = strategyDataVO_MR.getBasicProfits();

            number = profits.size();//形成期+持有期的个数

//            long period = (this.changeDateStyle(EndDate_MR).getTime() - this.changeDateStyle(StartDate_MR).getTime()) / number;
            long period = Integer.parseInt(HoldingPeriod_MeanReversio.getText()) * 24 * 60 * 60;

            SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat("yyyy-MM");

            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();
            List<Double> relativeProfits = new ArrayList<>();

            String time;
            Date startTime = this.changeDateStyle(StartDate_MR);
            for (int i = 0; i < number; i++) {
                time = simpleDateFormat_2.format(startTime);
                series1.getData().add(new XYChart.Data<>(time, profits.get(i)));
                series2.getData().add(new XYChart.Data<>(time, basicProfits.get(i)));
                relativeProfits.add(profits.get(i)-basicProfits.get(i));
                Calendar c = new  GregorianCalendar();
                c.setTime(startTime);
                c.add(c.DATE,Integer.parseInt(HoldingPeriod_MeanReversio.getText()));
                startTime = c.getTime();
            }

            lineChart.getData().addAll(series1, series2);
            lineChart.setAnimated(false);


            int[] frequentNumber = new int[10];
            for (int i = 0; i < 10; i++) {
                frequentNumber[i] = 0;
            }
            for (int i = 0; i < number; i++) {
                if (relativeProfits.get(i) <= 0.02 && relativeProfits.get(i) >= 0) {
                    frequentNumber[0]++;
                } else if (relativeProfits.get(i) > 0.02 && relativeProfits.get(i) <= +0.04) {
                    frequentNumber[1]++;
                } else if (relativeProfits.get(i) > 0.04 && relativeProfits.get(i) <= +0.06) {
                    frequentNumber[2]++;
                } else if (relativeProfits.get(i) > 0.06 && relativeProfits.get(i) <= +0.08) {
                    frequentNumber[3]++;
                } else if (relativeProfits.get(i) > 0.08) {
                    frequentNumber[4]++;
                } else if (relativeProfits.get(i) > -0.02 && relativeProfits.get(i) < 0) {
                    frequentNumber[5]++;
                } else if (relativeProfits.get(i) > -0.04 && relativeProfits.get(i) <= -0.02) {
                    frequentNumber[6]++;
                } else if (relativeProfits.get(i) > -0.06 && relativeProfits.get(i) <= -0.04) {
                    frequentNumber[7]++;
                } else if (relativeProfits.get(i) > -0.08 && relativeProfits.get(i) <= -0.06) {
                    frequentNumber[8]++;
                } else if (relativeProfits.get(i) <= -0.08) {
                    frequentNumber[9]++;
                }
            }

            //以下是相对收益指数图表的数据
            barChart.setBarGap(10);
            barChart.setCategoryGap(100);
            XYChart.Series<String, Number> series3 = new XYChart.Series<>();
            XYChart.Series<String, Number> series4 = new XYChart.Series<>();

            series3.getData().add(new XYChart.Data<>("1.00%", frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("3.00%", frequentNumber[1]));
            series3.getData().add(new XYChart.Data<>("5.00%", frequentNumber[2]));
            series3.getData().add(new XYChart.Data<>("7.00%", frequentNumber[3]));
            series3.getData().add(new XYChart.Data<>("9.00%", frequentNumber[4]));

            series4.getData().add(new XYChart.Data<>("1.00%", -frequentNumber[5]));
            series4.getData().add(new XYChart.Data<>("3.00%", -frequentNumber[6]));
            series4.getData().add(new XYChart.Data<>("5.00%", -frequentNumber[7]));
            series4.getData().add(new XYChart.Data<>("7.00%", -frequentNumber[8]));
            series4.getData().add(new XYChart.Data<>("9.00%", -frequentNumber[9]));

            barChart.getData().clear();
            barChart.layout();
            barChart.getData().addAll(series3, series4);
            barChart.setAnimated(false);

            setStockTableView();
        }
    }

    /*
    * 这个方法是累计收益率表格填充
     */
    private void setCumulativeTableView() {
        yearReturns.setCellValueFactory(celldata -> celldata.getValue().yearReturnsProperty());
        standardYearReturns.setCellValueFactory(celldata -> celldata.getValue().standardYearReturnsProperty());
        alpha.setCellValueFactory(celldata -> celldata.getValue().alphaProperty());
        beta.setCellValueFactory(celldata -> celldata.getValue().betaProperty());
        sharp.setCellValueFactory(celldata -> celldata.getValue().sharpProperty());
        retreats.setCellValueFactory(celldata -> celldata.getValue().retreatsProperty());

        //TODO

        DecimalFormat decimalFormat=new DecimalFormat();
        decimalFormat.applyPattern("####0.00");
//        String testdf=decimalFormat.format(annualReturn);

        //测试数据，数据层完成后将修改
//        cumulativeData = FXCollections.observableArrayList(
//                new CumulativeReturnsModel(String.valueOf(annualReturn), String.valueOf(basicAnnualReturn),
//                        String.valueOf(alphaNum), String.valueOf(betaNum), String.valueOf(sharpeRatio), String.valueOf(maxDrawDown))
//        );

        cumulativeData = FXCollections.observableArrayList(
                new CumulativeReturnsModel(String.valueOf(annualReturn+"%"), String.valueOf(basicAnnualReturn+"%"),
                        String.valueOf(alphaNum+"%"), String.valueOf(betaNum), String.valueOf(sharpeRatio), String.valueOf(maxDrawDown+"%"))
        );
        cumulativeTableView.getStyleClass().add("edge-to-edge");
        cumulativeTableView.getStyleClass().add("noborder");
        cumulativeTableView.setItems(cumulativeData);
    }

    private void setChooseFPorHP(){
        //根据选择形成期还是持有期判定是否能输入形成期或持久期
//        ObservableList<String> content = FXCollections.observableArrayList();
        List<String> list = new ArrayList<>();
        list.add("形成期");
        list.add("持有期");
//        content.addAll(list);
//        for (int i = 0;i<content.size();i++){
//            System.out.println(content.get(i));
//        }
        ChooseFPorHP_MS.getItems().addAll(list);
        ChooseFPorHP_MR.getItems().addAll(list);
    }

    /*
    * 这个方法是超额收益率图表与表格的填充
     */
    private void setOverProfitsUI_MS() {

        if (ChooseFPorHP_MS.getItems().equals("形成期")) {
            HoldingPeriod_MomentumStrategy.setDisable(true);
            System.out.println("get choose");
        } else {
            FormativePeriod_MomentumStrategy.setDisable(true);
            System.out.println("get choose");
        }

        LocalDate StartDate_MS = StartDate_MomentumStrategy.getValue();
        LocalDate EndDate_MS = EndDate_MomentumStrategy.getValue();

        SimpleDateFormat simpleDateFormat_1 = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MS = simpleDateFormat_1.format(this.changeDateStyle(StartDate_MS));
        String EndDateString_MS = simpleDateFormat_1.format(this.changeDateStyle(EndDate_MS));
        if (FormativePeriod_MomentumStrategy.getText() != null && !FormativePeriod_MomentumStrategy.getText().isEmpty()) {
            //如果选择形成期
            String instruction;
            if (isyourchoice == true) {
                instruction = "FNH\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + "F" + "\t" + "T\t" + FormativePeriod_MomentumStrategy.getText() + "\t" + StockheldInHouse_MomentumStrategy.getText() + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "FNH\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + "F" + "\t" + "F\t" + FormativePeriod_MomentumStrategy.getText() + "\t" + StockheldInHouse_MomentumStrategy.getText() + "\t";
                for (int i = 0; i < sectionNameList.size(); i++) {
                    instruction += sectionNameList.get(i) + "\t";
                }
            }
            System.out.println(instruction);
            net.actionPerformed(instruction);
        } else {
            //如果选择持有期
            String instruction;
            if (isyourchoice == true) {
                instruction = "FNH\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + "H" + "\t" + "T\t" + HoldingPeriod_MomentumStrategy.getText() + "\t" + StockheldInHouse_MomentumStrategy.getText() + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "FNH\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + "H" + "\t" + "F\t" + HoldingPeriod_MomentumStrategy.getText() + "\t" + StockheldInHouse_MomentumStrategy.getText() + "\t";
                for (int i = 0; i < sectionNameList.size(); i++) {
                    instruction += sectionNameList.get(i) + "\t";
                }
            }
            System.out.println(instruction);
            net.actionPerformed(instruction);
        }

        String ReturnsMessage;
        ReturnsMessage = net.run();
        if (ReturnsMessage == null) {
            System.out.println("No data on that day!");
        } else {
            System.out.println(ReturnsMessage + "getted");
            //json字符串转成MarketVO类型
            JsonUtil jsonUtil = new JsonUtil();
            FormativeNHoldingVO fhVO_middleState = new FormativeNHoldingVO();
            FormativeNHoldingVO fhVO = (FormativeNHoldingVO) jsonUtil.JSONToObj(ReturnsMessage, fhVO_middleState.getClass());

            period.setCellValueFactory(celldata -> celldata.getValue().periodProperty());
            returns.setCellValueFactory(celldata -> celldata.getValue().returnsProperty());
            percent.setCellValueFactory(celldata -> celldata.getValue().percentProperty());

//            System.out.print(fhVO.getOverProfit().toString());
//            System.out.print(fhVO.getClass());
            int ListLength = fhVO.getOverProfit().size();
//            System.out.print(ListLength);

            List<ReturnsModel> models = new ArrayList<>();
            ReturnsModel model;
            int cycle = 2;

            for (int i = 0; i < ListLength; i++) {
                String cycleString = String.valueOf(cycle + i * 1);
                DecimalFormat a = new DecimalFormat("#0.00%");
                String overProfit = a.format(fhVO.getOverProfit().get(i));
                String winChance = a.format(fhVO.getWinChance().get(i));

                model = new ReturnsModel(cycleString, overProfit, winChance);
                models.add(model);
            }

            data = FXCollections.observableArrayList(models);

            tableView.getStyleClass().add("edge-to-edge");
            tableView.getStyleClass().add("noborder");
            tableView.setItems(data);


            PeriodNumber_1.setForceZeroInRange(false);
            PeriodNumber_2.setForceZeroInRange(false);
            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();
            cycle = 2;
            for (int i = 0; i < ListLength; i++) {
                series1.getData().add(new XYChart.Data<>(cycle + i * 1, fhVO.getOverProfit().get(i)));
                series2.getData().add(new XYChart.Data<>(cycle + i * 1, fhVO.getWinChance().get(i)));

            }
            areaChart_1.setHorizontalZeroLineVisible(true);
            areaChart_2.setHorizontalZeroLineVisible(true);
            areaChart_1.getData().addAll(series1);
            areaChart_2.getData().addAll(series2);

            areaChart_1.setAnimated(false);
            areaChart_2.setAnimated(false);
        }

    }

    private void setOverProfitsUI_MR() {

        if (ChooseFPorHP_MR.getItems().equals("形成期")) {
            HoldingPeriod_MeanReversio.setDisable(true);
        } else {
            FormativePeriod_MeanReversio.setDisable(true);
        }

        LocalDate StartDate_MR = StartDate_MeanReversio.getValue();
        LocalDate EndDate_MR = EndDate_MeanReversio.getValue();

        SimpleDateFormat simpleDateFormat_1 = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MR = simpleDateFormat_1.format(this.changeDateStyle(StartDate_MR));
        String EndDateString_MR = simpleDateFormat_1.format(this.changeDateStyle(EndDate_MR));
        if (FormativePeriod_MeanReversio.getText() != null && !FormativePeriod_MeanReversio.getText().isEmpty()) {
            //如果选择形成期
            String instruction;
            if (isyourchoice == true) {
                instruction = "FNH\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + "F" + "\t" + "T\t" + FormativePeriod_MeanReversio.getText() + "\t"+ StockHeldInHouse_MeanReversio.getText() + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "FNH\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + "F" + "\t" + "F\t" + FormativePeriod_MeanReversio.getText() + "\t" + StockHeldInHouse_MeanReversio.getText() + "\t";
                for (int i = 0; i < sectionNameList.size(); i++) {
                    instruction += sectionNameList.get(i) + "\t";
                }
            }
            net.actionPerformed(instruction);
        } else {
            //如果选择持有期
            String instruction;
            if (isyourchoice == true) {
                instruction = "FNH\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + "H" + "\t" + "T\t" + HoldingPeriod_MeanReversio.getText() + "\t"+ StockHeldInHouse_MeanReversio.getText() + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "FNH\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + "H" + "\t" + "F\t" + HoldingPeriod_MeanReversio.getText() + "\t"+ StockHeldInHouse_MeanReversio.getText() + "\t";
                for (int i = 0; i < sectionNameList.size(); i++) {
                    instruction += sectionNameList.get(i) + "\t";
                }
            }
            net.actionPerformed(instruction);
        }


        String ReturnsMessage;
        ReturnsMessage = net.run();
        if (ReturnsMessage == null) {
            System.out.println("No data on that day!");
        } else {
            System.out.println(ReturnsMessage + "getted");
            //json字符串转成MarketVO类型
            JsonUtil jsonUtil = new JsonUtil();
            FormativeNHoldingVO fhVO_middleState = new FormativeNHoldingVO();
            FormativeNHoldingVO fhVO = (FormativeNHoldingVO) jsonUtil.JSONToObj(ReturnsMessage, fhVO_middleState.getClass());


            period.setCellValueFactory(celldata -> celldata.getValue().periodProperty());
            returns.setCellValueFactory(celldata -> celldata.getValue().returnsProperty());
            percent.setCellValueFactory(celldata -> celldata.getValue().percentProperty());


            int ListLength = fhVO.getOverProfit().size();

            List<ReturnsModel> models = new ArrayList<>();
            ReturnsModel model;
            int cycle = 2;

            for (int i = 0; i < ListLength; i++) {
                String cycleString = String.valueOf(cycle + i * 1);
                DecimalFormat a = new DecimalFormat("#0.00%");
                String overProfit = a.format(fhVO.getOverProfit().get(i));
                String winChance = a.format(fhVO.getWinChance().get(i));
                model = new ReturnsModel(cycleString, overProfit, winChance);
                models.add(model);
            }

            data = FXCollections.observableArrayList(models);

            tableView.getStyleClass().add("edge-to-edge");
            tableView.getStyleClass().add("noborder");
            tableView.setItems(data);


            PeriodNumber_1.setForceZeroInRange(false);
            PeriodNumber_2.setForceZeroInRange(false);
            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();
            cycle = 2;
            for (int i = 0; i < ListLength; i++) {
                series1.getData().add(new XYChart.Data<>(cycle + i * 1, fhVO.getOverProfit().get(i)));
                series2.getData().add(new XYChart.Data<>(cycle + i * 1, fhVO.getWinChance().get(i)));
            }
            areaChart_1.setHorizontalZeroLineVisible(true);
            areaChart_2.setHorizontalZeroLineVisible(true);
            areaChart_1.getData().addAll(series1);
            areaChart_2.getData().addAll(series2);

            areaChart_1.setAnimated(false);
            areaChart_2.setAnimated(false);
        }
    }


    @FXML
    private void setChoose_MS() {
//        if(stockCodeList.size()>=100) {
            if (chaoetab.isSelected()) {
//            HoldingPeriod_MeanReversio.setText("");
//            FormativePeriod_MeanReversio.setText("");
//            StockHeldInHouse_MeanReversio.setText("");
////            ChooseFPorHP.setItems();
                setOverProfitsUI_MS();
            } else {
                setMomentumStrategyInputSearch();
            }
//        }
//        else{
//            AlertUtil.showWarningAlert("对不起，您选择的股票不足100只");
//        }

    }

    @FXML
    private void setChoose_MR() {
//        if (stockCodeList.size() >= 100) {
            if (chaoetab.isSelected()) {
//            HoldingPeriod_MeanReversio.setText("");
//            FormativePeriod_MeanReversio.setText("");
//            StockHeldInHouse_MeanReversio.setText("");
                setOverProfitsUI_MR();
            } else {
                setMeanReversioInputSearch();
            }
//        }
//        else{
//            AlertUtil.showWarningAlert("对不起，您选择的股票不足100只");
//        }
    }


    public void ReturnsController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setMain(Main main, Net net) {
//        setComboBox();
//        setTableView();
//        setAreaChart_1();
//        setAreaChart_2();
//        setBarChart();
//        setCumulativeTableView();
//        setLineChart();
//        setMeanReversioInputSearch();
//        setMomentumStrategyInputSearch();
//        setOverProfitsUI_MS();
//        setOverProfitsUI_MR();
        setChooseFPorHP();
        this.setDatePicker();
        this.main = main;
        this.net = net;
        HoldingPeriod_MomentumStrategy.setText("10");
        FormativePeriod_MomentumStrategy.setText("10");

        HoldingPeriod_MeanReversio.setText("");
        FormativePeriod_MeanReversio.setText("2");
        StockHeldInHouse_MeanReversio.setText("9");

        stockCodeList.add("002007");
        stockCodeList.add("002006");
        stockCodeList.add("002002");
        stockCodeList.add("002003");
        stockCodeList.add("000016");
        stockCodeList.add("002004");
        stockCodeList.add("002005");
        stockCodeList.add("002001");
        stockCodeList.add("000100");
        stockCodeList.add("001696");

        isyourchoice=true;

    }
}
