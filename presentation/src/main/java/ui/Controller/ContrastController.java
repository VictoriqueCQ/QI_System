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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    private TableColumn<StockModel, String> variance;

    @FXML
    private TableColumn<StockModel, String> stockName;

    private StockModel stockModel1;

    private StockModel stockModel2;

    private ObservableList<StockModel> models;

    private Map<String, XYChart.Series<String, Number>> seriesMap;

    /**
     * 在开始时间选取后更新结束时间可选日期
     */
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

    /**
     * 在结束时间选取后更新开始时间可选日期
     */
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


    /**
     * 初始化日期选择器可选时间
     */
    private void setDatePicker() {
        startTimeDatePicker.setValue(LocalDate.of(2005, 2, 1));
        endTimeDatePicker.setValue(LocalDate.of(2014, 4, 30));
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
        startTimeDatePicker.setDayCellFactory(dayCellFactory1);
        endTimeDatePicker.setDayCellFactory(dayCellFactory1);
    }


    @Override
    public void start(Stage primaryStage) {
    }

    /**
     * 比较按钮，根据条件显示各种图表
     */
    @FXML
    public void addCompare() {
        String stockName1 = nameTextField1.getText();
        String stockName2 = nameTextField2.getText();
        LocalDate startLocalDate = startTimeDatePicker.getValue();
        LocalDate endLocalDate = endTimeDatePicker.getValue();
        Date startDate = this.changeDateStyle(startLocalDate);
        Date endDate = this.changeDateStyle(endLocalDate);

        StockSearchConditionVO searchConditionVO1 = new StockSearchConditionVO(stockName1, null, startDate, endDate);
        StockSearchConditionVO searchConditionVO2 = new StockSearchConditionVO(stockName2, null, startDate, endDate);

        stock1 = getStockVoByCondition(searchConditionVO1);
        if (stock1 == null) {
            AlertUtil.showErrorAlert("对不起，您输入的股票一不存在");
        }
        stock2 = getStockVoByCondition(searchConditionVO2);
        if (stock2 == null) {
            AlertUtil.showErrorAlert("对不起，您输入的股票二不存在");
        }

        setTableContrast();
        setClosePriceLine(stock1.getClose(), stock1.getDates(), stock1.getName());
        setClosePriceLine(stock2.getClose(), stock2.getDates(), stock2.getName());
        setIncomeLine(stock1.getDates(), stock1.getName(), stock1.getProfit());
        setIncomeLine(stock2.getDates(), stock2.getName(), stock2.getProfit());
        setVariance();
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
        DecimalFormat df = new DecimalFormat("#.00");
        df.format(riseAndDown);
        model.setRiseAndDown(riseAndDown * 100 + "%");
        model.setVariance(stockVO.getVariance());
        return model;
    }

    public void setClosePriceLine(double[] close, List<Date> dates, String name) {
        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        series1.setName(name);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        for (int i = dates.size() - 1; i >= 0; i--) {
            String s = format.format(dates.get(i));
            series1.getData().add(new XYChart.Data(s, close[i]));
        }
        closePriceLine.getData().add(series1);

    }


    public void setIncomeLine(List<Date> dates, String name, ArrayList<Double> income) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(name);
        //populating the series with data
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        for (int i = income.size() - 1; i >= 0; i--) {
            String s = format.format(dates.get(i + 1));
            series.getData().add(new XYChart.Data(s, income.get(i)));
        }
        IncomeLine.getData().add(series);
    }

    public void setVariance() {
        stockName.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
        variance.setCellValueFactory(celldata -> celldata.getValue().varianceProperty());
        models = FXCollections.observableArrayList();
        models.add(stockModel1);
        models.add(stockModel2);
        varianceTable.setItems(models);
    }

    private StockVO getStockVoByCondition(StockSearchConditionVO searchConditionVO) {
        if (searchConditionVO.getStockName() == "") {
            AlertUtil.showErrorAlert("对不起，您未输入股票信息");
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy ");
        String starttime = format.format(searchConditionVO.getStartTime());
        String endtime = format.format(searchConditionVO.getEndTime());
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
    private void removeCompare() {
        stockTable.getItems().removeAll(models);
        closePriceLine.getData().removeAll();
    }

    private Date changeDateStyle(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    public void setMain(Main main, Net net) {
        this.main = main;
        this.net = net;
        this.setDatePicker();
    }
}
