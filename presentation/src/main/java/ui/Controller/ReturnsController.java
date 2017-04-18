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
import quantour.vo.FormativeNHoldingVO;
import quantour.vo.StockSetVO;
import quantour.vo.StrategyDataVO;
import ui.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    //选择形成期还是持有期
    @FXML
    private ComboBox<String> ChooseFPorHP_MS;

    @FXML
    private ComboBox<String> ChooseFPorHP_MR;
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

    /*
    * 以下4个变量是用于股票表格的，分别是
    * 用于显示股票排名的StockRank
    * 用于显示股票名称的StockName
    * 用于显示股票代码的StockCode
    * 用于表格的stockTable
     */
    //股票排名
    @FXML
    private TableColumn<StockModel, String> StockRank;

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


    @FXML
    private void gotoSelectStock() {
        main.gotoSelectStock(this);
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

//    private void setHeldComboBox() {
//        ObservableList<String> HeldPeriod = FXCollections.observableArrayList();
//        List<String> HeldPeriodName = new ArrayList<>();
//    }

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
        } else {
            stockSetVOS = strategyDataVO_MR.getStockSetVOS();
        }
        ObservableList<StockModel> stockModels = FXCollections.observableArrayList();

        //填充第几个持有期的combobox,根据stocksetvos的长度
        ObservableList<String> HoldPeriod = FXCollections.observableArrayList();
        List<String> HoldPeriodString = new ArrayList<>();
        for (int i = 1; i <= stockSetVOS.size(); i++) {
            HoldPeriodString.add("第" + i + "个持有期");
        }
        HoldPeriod.addAll(HoldPeriodString);
        HoldPeriodRank.setItems(HoldPeriod);
        //默认值
        HoldPeriodRank.setValue("第一个持有期");

        StockRank.setCellValueFactory(celldata -> celldata.getValue().rankProperty());
        StockRank.setCellFactory(new Callback<TableColumn<StockModel, String>, TableCell<StockModel, String>>() {

            @Override
            public TableCell<StockModel, String> call(TableColumn<StockModel, String> param) {
                TextFieldTableCell<StockModel, String> cell = new TextFieldTableCell<>();

                cell.setOnMouseClicked((MouseEvent t) -> {
                    String name = (stockTable.getItems().get(cell.getIndex()).getName());
                    String code = (stockTable.getItems().get(cell.getIndex()).getID());
                    if (t.getClickCount() == 2) {
                        if (cell.getIndex() <= stockModels.size()) {
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
                        if (cell.getIndex() <= stockModels.size()) {
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
                        if (cell.getIndex() <= stockModels.size()) {
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

        //默认展示第一个持有期
        ArrayList<StockModel> stockModelArrayList = this.getbeststock(stockSetVOS, 0);
        for (int i = 0; i < stockModelArrayList.size(); i++) {
            stockModels.add(stockModelArrayList.get(i));
        }
        stockTable.setItems(stockModels);

    }


    /**
     * 根据第几个持有期展示最终表格
     */
    @FXML
    private void showTabelByHoldPeriod_MS() {
        String holdPeriod = HoldPeriodRank.getValue();
        char[] h = holdPeriod.toCharArray();
        int time = h[1];
        List<StockSetVO> stockSetVOS = strategyDataVO_MS.getStockSetVOS();
        ObservableList<StockModel> stockModels = FXCollections.observableArrayList();
        ArrayList<StockModel> stockModelArrayList = this.getbeststock(stockSetVOS, time);
        for (int i = 0; i < stockModelArrayList.size(); i++) {
            stockModels.add(stockModelArrayList.get(i));
        }
        stockTable.setItems(stockModels);
    }

    /**
     * 根据第几个持有期返回对应数据
     *
     * @param stockSetVOS
     * @param i
     */
    private ArrayList<StockModel> getbeststock(List<StockSetVO> stockSetVOS, int i) {
        StockSetVO stockSetVO = stockSetVOS.get(i);
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
        for (int i = 1; i <= stockSets.size(); i++) {
            StockModel model = new StockModel();
            model.setRank(String.valueOf(i));
            String code1 = stockSets.get(i);
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
        for (String temp : content
                ) {
            System.out.println(temp);
        }
        return content;

    }

    /*
    * 这个方法是使用动量策略时处理用户输入，同时获取股票表格，累计收益率，相对收益指数的数据，将用于查询按钮
     */
    private void setMomentumStrategyInputSearch() {
        System.out.print(1);
        LocalDate StartDate_MS = StartDate_MomentumStrategy.getValue();
        LocalDate EndDate_MS = EndDate_MomentumStrategy.getValue();
        System.out.print(2);
        SimpleDateFormat simpleDateFormat_1 = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MS = simpleDateFormat_1.format(this.changeDateStyle(StartDate_MS));
        String EndDateString_MS = simpleDateFormat_1.format(this.changeDateStyle(EndDate_MS));
        if (FormativePeriod_MomentumStrategy.getText() != null && !FormativePeriod_MomentumStrategy.getText().isEmpty()
                && HoldingPeriod_MomentumStrategy.getText() != null && !HoldingPeriod_MomentumStrategy.getText().isEmpty()) {
            String instruction;
            if (isyourchoice == true) {
                instruction = "Strategy\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + FormativePeriod_MomentumStrategy.getText() + "\t" + "T\t" + HoldingPeriod_MomentumStrategy.getText() + "\t" + null + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "Strategy\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + FormativePeriod_MomentumStrategy.getText() + "\t" + "F\t" + HoldingPeriod_MomentumStrategy.getText() + "\t" + null + "\t";
                for (int i = 0; i < sectionNameList.size(); i++) {
                    instruction += sectionNameList.get(i) + "\t";
                }
            }
            System.out.print("fgh" + instruction);
            net.actionPerformed(instruction);
        } else {
            System.out.print("error");
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
            strategyDataVO_MS = (StrategyDataVO) jsonUtil.JSONToObj(ReturnsMessage, StrategyDataVO_middleState.getClass());


            //以下是累计收益率
            annualReturn = strategyDataVO_MS.getAnnualReturn();

            basicAnnualReturn = strategyDataVO_MS.getBasicAnnualReturn();

            alphaNum = strategyDataVO_MS.getAlpha();

            betaNum = strategyDataVO_MS.getBeta();

            sharpeRatio = strategyDataVO_MS.getSharpeRatio();

            maxDrawDown = strategyDataVO_MS.getMaxDrawDown();

            setCumulativeTableView();

            List<Double> profits = strategyDataVO_MS.getProfits();

            List<Double> basicProfits = strategyDataVO_MS.getBasicProfits();

            number = profits.size();//形成期+持有期的个数

//            long period = (this.changeDateStyle(EndDate_MS).getTime() - this.changeDateStyle(StartDate_MS).getTime()) / number;
            long period = Integer.parseInt(HoldingPeriod_MomentumStrategy.getText()) * 24 * 60 * 60;

            SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat("yyyy-MM");

            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();

            for (int i = 0; i < number; i++) {
                String time = simpleDateFormat_2.format(this.changeDateStyle(StartDate_MS).getTime() + i * period);
                series1.getData().add(new XYChart.Data<>(time, profits.get(i)));
                series2.getData().add(new XYChart.Data<>(time, basicProfits.get(i)));
            }

            lineChart.getData().addAll(series1, series2);


            //以下是相对收益指数
            List<Double> relativeProfits = new ArrayList<>();

            int[] frequentNumber = new int[10];
            for (int i = 0; i < 10; i++) {
                frequentNumber[i] = 0;
            }
            for (int i = 0; i < number; i++) {
                relativeProfits.add(profits.get(i) - basicProfits.get(i));
            }
            for (int i = 0; i < number; i++) {
                if (relativeProfits.get(i) <= 0.015 && relativeProfits.get(i) >= 0) {
                    frequentNumber[0]++;
                } else if (relativeProfits.get(i) > 0.015 && relativeProfits.get(i) < +0.025) {
                    frequentNumber[1]++;
                } else if (relativeProfits.get(i) > 0.025 && relativeProfits.get(i) < +0.035) {
                    frequentNumber[2]++;
                } else if (relativeProfits.get(i) > 0.035 && relativeProfits.get(i) < +0.045) {
                    frequentNumber[3]++;
                } else if (relativeProfits.get(i) > 0.045) {
                    frequentNumber[4]++;
                } else if (relativeProfits.get(i) > -0.015 && relativeProfits.get(i) < 0) {
                    frequentNumber[5]++;
                } else if (relativeProfits.get(i) > -0.025 && relativeProfits.get(i) < -0.015) {
                    frequentNumber[6]++;
                } else if (relativeProfits.get(i) > -0.035 && relativeProfits.get(i) < -0.025) {
                    frequentNumber[7]++;
                } else if (relativeProfits.get(i) > -0.045 && relativeProfits.get(i) < -0.035) {
                    frequentNumber[8]++;
                } else if (relativeProfits.get(i) < -0.045) {
                    frequentNumber[9]++;
                }
            }

            //以下是相对收益指数图表的数据
            barChart.setBarGap(3);
            barChart.setCategoryGap(20);
            XYChart.Series<String, Number> series3 = new XYChart.Series<>();
            XYChart.Series<String, Number> series4 = new XYChart.Series<>();

            series3.getData().add(new XYChart.Data<>("1.00%", frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("2.00%", frequentNumber[1]));
            series3.getData().add(new XYChart.Data<>("3.00%", frequentNumber[2]));
            series3.getData().add(new XYChart.Data<>("4.00%", frequentNumber[3]));
            series3.getData().add(new XYChart.Data<>("5.00%", frequentNumber[4]));

            series4.getData().add(new XYChart.Data<>("1.00%", -frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("2.00%", -frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("3.00%", -frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("4.00%", -frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("5.00%", -frequentNumber[0]));

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
        LocalDate StartDate_MR = StartDate_MeanReversio.getValue();
        LocalDate EndDate_MR = EndDate_MeanReversio.getValue();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MR = simpleDateFormat.format(this.changeDateStyle(StartDate_MR));
        String EndDateString_MR = simpleDateFormat.format(this.changeDateStyle(EndDate_MR));
        if (FormativePeriod_MeanReversio.getText() != null && !FormativePeriod_MeanReversio.getText().isEmpty()
                && HoldingPeriod_MeanReversio.getText() != null && !HoldingPeriod_MeanReversio.getText().isEmpty()
                && StockHeldInHouse_MeanReversio.getText() != null && !StockHeldInHouse_MeanReversio.getText().isEmpty()) {
            String instruction;
            if (isyourchoice == true) {
                instruction = "Strategy\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + FormativePeriod_MomentumStrategy.getText() + "\t" + "T\t" + HoldingPeriod_MomentumStrategy.getText() + "\t"
                        + StockheldInHouse_MomentumStrategy.getText() + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "Strategy\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + FormativePeriod_MomentumStrategy.getText() + "\t" + "F\t" + HoldingPeriod_MomentumStrategy.getText() + "\t"
                        + StockheldInHouse_MomentumStrategy.getText() + "\t";
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
            StrategyDataVO StrategyDataVO_middleState = new StrategyDataVO();
            strategyDataVO_MR = (StrategyDataVO) jsonUtil.JSONToObj(ReturnsMessage, StrategyDataVO_middleState.getClass());

            annualReturn = strategyDataVO_MR.getAnnualReturn();

            basicAnnualReturn = strategyDataVO_MR.getBasicAnnualReturn();

            alphaNum = strategyDataVO_MR.getAlpha();

            betaNum = strategyDataVO_MR.getBeta();

            sharpeRatio = strategyDataVO_MR.getSharpeRatio();

            maxDrawDown = strategyDataVO_MR.getMaxDrawDown();

            setCumulativeTableView();

            List<Double> profits = strategyDataVO_MR.getProfits();

            List<Double> basicProfits = strategyDataVO_MR.getBasicProfits();

            int number = profits.size();//形成期+持有期的个数

//            long period = (this.changeDateStyle(EndDate_MR).getTime() - this.changeDateStyle(StartDate_MR).getTime()) / number;
            long period = Integer.parseInt(HoldingPeriod_MomentumStrategy.getText()) * 24 * 60 * 60;

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
            for (int i = 0; i < 10; i++) {
                frequentNumber[i] = 0;
            }
            for (int i = 0; i < number; i++) {
                relativeProfits.add(profits.get(i) - basicProfits.get(i));
            }
            for (int i = 0; i < number; i++) {
                if (relativeProfits.get(i) <= 0.015 && relativeProfits.get(i) >= 0) {
                    frequentNumber[0]++;
                } else if (relativeProfits.get(i) > 0.015 && relativeProfits.get(i) < +0.025) {
                    frequentNumber[1]++;
                } else if (relativeProfits.get(i) > 0.025 && relativeProfits.get(i) < +0.035) {
                    frequentNumber[2]++;
                } else if (relativeProfits.get(i) > 0.035 && relativeProfits.get(i) < +0.045) {
                    frequentNumber[3]++;
                } else if (relativeProfits.get(i) > 0.045) {
                    frequentNumber[4]++;
                } else if (relativeProfits.get(i) > -0.015 && relativeProfits.get(i) < 0) {
                    frequentNumber[5]++;
                } else if (relativeProfits.get(i) > -0.025 && relativeProfits.get(i) < -0.015) {
                    frequentNumber[6]++;
                } else if (relativeProfits.get(i) > -0.035 && relativeProfits.get(i) < -0.025) {
                    frequentNumber[7]++;
                } else if (relativeProfits.get(i) > -0.045 && relativeProfits.get(i) < -0.035) {
                    frequentNumber[8]++;
                } else if (relativeProfits.get(i) < -0.045) {
                    frequentNumber[9]++;
                }
            }

            //以下是相对收益指数图表的数据
            barChart.setBarGap(3);
            barChart.setCategoryGap(20);
            XYChart.Series<String, Number> series3 = new XYChart.Series<>();
            XYChart.Series<String, Number> series4 = new XYChart.Series<>();

            series3.getData().add(new XYChart.Data<>("1.00%", frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("2.00%", frequentNumber[1]));
            series3.getData().add(new XYChart.Data<>("3.00%", frequentNumber[2]));
            series3.getData().add(new XYChart.Data<>("4.00%", frequentNumber[3]));
            series3.getData().add(new XYChart.Data<>("5.00%", frequentNumber[4]));

            series4.getData().add(new XYChart.Data<>("1.00%", -frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("2.00%", -frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("3.00%", -frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("4.00%", -frequentNumber[0]));
            series3.getData().add(new XYChart.Data<>("5.00%", -frequentNumber[0]));

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

        //测试数据，数据层完成后将修改
        cumulativeData = FXCollections.observableArrayList(
                new CumulativeReturnsModel(String.valueOf(annualReturn), String.valueOf(basicAnnualReturn),
                        String.valueOf(alphaNum), String.valueOf(betaNum), String.valueOf(sharpeRatio), String.valueOf(maxDrawDown))
        );

        cumulativeTableView.getStyleClass().add("edge-to-edge");
        cumulativeTableView.getStyleClass().add("noborder");
        cumulativeTableView.setItems(cumulativeData);
    }


    /*
    * 这个方法是超额收益率图表与表格的填充
     */
    private void setOverProfitsUI_MS() {
        //根据选择形成期还是持有期判定是否能输入形成期或持久期
        ObservableList<String> content = FXCollections.observableArrayList();
        List<String> list = new ArrayList<>();
        list.add("形成期");
        list.add("持有期");
        content.addAll(list);
        ChooseFPorHP_MS.setItems(content);
        if (ChooseFPorHP_MR.getItems().equals("形成期")) {
            HoldingPeriod_MomentumStrategy.setDisable(true);
        } else {
            FormativePeriod_MomentumStrategy.setDisable(true);
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
                        + "F" + "\t" + "T\t" + FormativePeriod_MomentumStrategy.getText() + "\t" + null + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "FNH\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + "F" + "\t" + "F\t" + FormativePeriod_MomentumStrategy.getText() + "\t" + null + "\t";
                for (int i = 0; i < sectionNameList.size(); i++) {
                    instruction += sectionNameList.get(i) + "\t";
                }
            }
            net.actionPerformed(instruction);
        } else {
            //如果选择持有期
            String instruction;
            if (isyourchoice == true) {
                instruction = "FNH\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + "H" + "\t" + "T\t" + HoldingPeriod_MomentumStrategy.getText() + "\t" + null + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "FNH\t" + "M\t" + StartDateString_MS + "\t" + EndDateString_MS + "\t"
                        + "H" + "\t" + "F\t" + HoldingPeriod_MomentumStrategy.getText() + "\t" + null + "\t";
                for (int i = 0; i < sectionNameList.size(); i++) {
                    instruction += sectionNameList.get(i) + "\t";
                }
            }
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
                String overProfit = (double) (((int) (fhVO.getOverProfit().get(i) * 1000)) / 10) + "%";
                String winChance = (double) (((int) (fhVO.getWinChance().get(i) * 1000)) / 10) + "%";
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
                series1.getData().add(new XYChart.Data<>(cycle + i * 1, (double) (((int) (fhVO.getOverProfit().get(i) * 1000)) / 10)));
                series2.getData().add(new XYChart.Data<>(cycle + i * 1, (double) (((int) (fhVO.getWinChance().get(i) * 1000)) / 10)));
            }
            areaChart_1.setHorizontalZeroLineVisible(true);
            areaChart_2.setHorizontalZeroLineVisible(true);
            areaChart_1.getData().addAll(series1);
            areaChart_2.getData().addAll(series2);
        }

    }

    private void setOverProfitsUI_MR() {
        //根据选择的是形成期还是持有期判定另一个是否能填
        ObservableList<String> content = FXCollections.observableArrayList();
        List<String> list = new ArrayList<>();
        list.add("形成期");
        list.add("持有期");
        content.addAll(list);
        ChooseFPorHP_MR.setItems(content);
        if (ChooseFPorHP_MR.getItems().equals("形成期")) {
            HoldingPeriod_MomentumStrategy.setDisable(true);
        } else {
            FormativePeriod_MomentumStrategy.setDisable(true);
        }

        LocalDate StartDate_MR = StartDate_MomentumStrategy.getValue();
        LocalDate EndDate_MR = EndDate_MomentumStrategy.getValue();

        SimpleDateFormat simpleDateFormat_1 = new SimpleDateFormat("MM/dd/yy");
        String StartDateString_MR = simpleDateFormat_1.format(this.changeDateStyle(StartDate_MR));
        String EndDateString_MR = simpleDateFormat_1.format(this.changeDateStyle(EndDate_MR));
        if (FormativePeriod_MomentumStrategy.getText() != null && !FormativePeriod_MomentumStrategy.getText().isEmpty()) {
            //如果选择形成期
            String instruction;
            if (isyourchoice == true) {
                instruction = "FNH\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + "F" + "\t" + "T\t" + FormativePeriod_MomentumStrategy.getText() + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "FNH\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + "F" + "\t" + "F\t" + FormativePeriod_MomentumStrategy.getText() + "\t";
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
                        + "H" + "\t" + "T\t" + HoldingPeriod_MomentumStrategy.getText() + "\t";
                for (int i = 0; i < stockCodeList.size(); i++) {
                    instruction += stockCodeList.get(i) + "\t";
                }
            } else {
                instruction = "FNH\t" + "A\t" + StartDateString_MR + "\t" + EndDateString_MR + "\t"
                        + "H" + "\t" + "F\t" + HoldingPeriod_MomentumStrategy.getText() + "\t";
                for (int i = 0; i < sectionNameList.size(); i++) {
                    instruction += sectionNameList.get(i) + "\t";
                }
            }
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

            fhVO = new FormativeNHoldingVO();
            int ListLength = fhVO.getOverProfit().size();

            List<ReturnsModel> models = new ArrayList<>();
            ReturnsModel model;
            int cycle = 2;

            for (int i = 0; i < ListLength; i++) {
                String cycleString = String.valueOf(cycle + i * 1);
                String overProfit = (double) (((int) (fhVO.getOverProfit().get(i) * 1000)) / 10) + "%";
                String winChance = (double) (((int) (fhVO.getWinChance().get(i) * 1000)) / 10) + "%";
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
                series1.getData().add(new XYChart.Data<>(cycle + i * 1, (double) (((int) (fhVO.getOverProfit().get(i) * 1000)) / 10)));
                series2.getData().add(new XYChart.Data<>(cycle + i * 1, (double) (((int) (fhVO.getWinChance().get(i) * 1000)) / 10)));
            }
            areaChart_1.setHorizontalZeroLineVisible(true);
            areaChart_2.setHorizontalZeroLineVisible(true);
            areaChart_1.getData().addAll(series1);
            areaChart_2.getData().addAll(series2);
        }
    }


    @FXML
    private void setChoose_MS() {
//        if(chaoetab.isSelected()){
//            HoldingPeriod_MeanReversio.setText("");
//            FormativePeriod_MeanReversio.setText("");
//            StockHeldInHouse_MeanReversio.setText("");
//            ChooseFPorHP.setItems();

//            setOverProfitsUI_MS();
//        }else{
        System.out.print("dfghuj");
        setMomentumStrategyInputSearch();

//        }

    }

    @FXML
    private void setChoose_MR() {
//        if(chaoetab.isSelected()){
//            HoldingPeriod_MeanReversio.setText("");
//            FormativePeriod_MeanReversio.setText("");
//            StockHeldInHouse_MeanReversio.setText("");
//            setOverProfitsUI_MR();
//        }else{
        setMeanReversioInputSearch();
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
        this.setDatePicker();
//        setChoose_MR();
//        setChoose_MS();
        this.main = main;
        this.net = net;
//        this.setDatePicker();
    }
}
