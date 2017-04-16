package ui.Controller;
/**
 * Created by chenyuyan on 17/3/3.
 */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContrastController extends Application {
    private Main main;

    private Net net;
    private List<StockVO> allStock;//所有股票
    private StockVO stock1;
    private StockVO stock2;


    @FXML
    private TextField nameTextField1;//////

    @FXML
    private TextField nameTextField2;/////

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

    private ObservableList<StockModel> models2;


    private Map<String, XYChart.Series<String, Double>> seriesMap;

    @FXML
    private ChoiceBox<String> searchWayChoice;

    @FXML
    private TextField stockField;

    private int number;//最多可设置五只股票同时比较




    @FXML
    private ListView<String> fuzzyCheck;//模糊搜索


    static String stockNameImport;//输入的股票名
    static String reStockName;

    private String[] allStockName;//存储所有股票的名字用于模糊搜索

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
        startTimeDatePicker.setValue(LocalDate.of(2014, 2, 1));
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
        fuzzyCheck.setVisible(false);
        if(number<5){
        number++;
        StockSearchConditionVO searchConditionVO1;
        String stockName1 = stockField.getText();
        LocalDate startLocalDate = startTimeDatePicker.getValue();
        LocalDate endLocalDate = endTimeDatePicker.getValue();
        Date startDate = this.changeDateStyle(startLocalDate);
        Date endDate = this.changeDateStyle(endLocalDate);

        if (searchWayChoice.getValue().equals("股票名称搜索")) {
            searchConditionVO1 = new StockSearchConditionVO(null, stockName1, startDate, endDate);

        } else {
            searchConditionVO1 = new StockSearchConditionVO(stockName1, null, startDate, endDate);

        }

        stock1 = getStockVoByCondition(searchConditionVO1);
        if (stock1 == null) {
            AlertUtil.showErrorAlert("对不起，您输入的股票不存在");
        }

        setTableContrast();
        setClosePriceLine(stock1.getClose(), stock1.getDates(), stock1.getName());
        setIncomeLine(stock1.getDates(), stock1.getName(), stock1.getProfit());
        setVariance();
        } else{
            AlertUtil.showConfirmingAlert("对不起，您选择比较股票数过多");
        }


    }



        /*stock1 = getStockVoByCondition(searchConditionVO1);
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
        setVariance();*/


    /**
     * cyy
     * 判断是否存在
     */
    /*
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

    }*/

    public void setTableContrast() {
        stockName1.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
        stockID.setCellValueFactory(celldata -> celldata.getValue().idProperty());
        minPrice.setCellValueFactory(celldata -> celldata.getValue().minPriceProperty());
        maxPrice.setCellValueFactory(celldata -> celldata.getValue().maxPriceProperty());
        riseAndDown.setCellValueFactory(celldata -> celldata.getValue().riseAndDownProperty());
        stockModel1 = stockVOtoStockModle(stock1);
//        stockModel2 = stockVOtoStockModle(stock2);
//        System.out.print(stockModel1.getName());
//        models = FXCollections.observableArrayList();
        models.add(stockModel1);
//        models.add(stockModel2);
        stockTable.setItems(models);
    }

    public StockModel stockVOtoStockModle(StockVO stockVO) {
        StockModel model = new StockModel();
        model.setName(stockVO.getName());
        model.setID(stockVO.getCode());
        double[] low = stockVO.getLow();
        double minTemp = low[0];
//        System.out.print("dhaudgaygduyagd"+minTemp);
        for (int i = 0; i < low.length; i++) {
            if (low[i] < minTemp) {
                minTemp = low[i];
            }
        }

        model.setMinPrice(minTemp);

        double[] high = stockVO.getHigh();
        double maxTemp = high[0];
        for (int i = 0; i < high.length; i++) {
            if (low[i] > maxTemp) {
                maxTemp = high[i];
            }
        }
        model.setMaxPrice(maxTemp);
        double dd = 2.00;
        double riseAndDown = (stockVO.getClose()[stockVO.getClose().length - 1] - stockVO.getClose()[0]) / stockVO.getClose()[0];
        riseAndDown = riseAndDown * 100;
        DecimalFormat df = new DecimalFormat("#.00");
        model.setRiseAndDown(df.format(riseAndDown) + "%");
        double d = stockVO.getVariance();
        BigDecimal bd = new BigDecimal(d);
        DecimalFormat df2 = new DecimalFormat("0.0000");
        model.setVariance(df2.format(d));
        return model;
    }

    /**
     * chenyuyan
     * 设置每日收盘价折线图
     *
     * @param close
     * @param dates
     * @param name
     */

    public void setClosePriceLine(double[] close, List<Date> dates, String name) {
        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        series1.setName(name);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = dates.size() - 1; i >= 0; i--) {
            String s = format.format(dates.get(i));
            series1.getData().add(new XYChart.Data(s, close[i]));
        }

        closePriceLine.getData().add(series1);

    }

    /**
     * chenyuyan
     * 设置收益率折线图
     *
     * @param dates
     * @param name
     * @param income
     */


    public void setIncomeLine(List<Date> dates, String name, ArrayList<Double> income) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(name);
        //populating the series with data
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = income.size() - 1; i >= 0; i--) {
            String s = format.format(dates.get(i + 1));
            series.getData().add(new XYChart.Data(s, income.get(i)));
        }
        IncomeLine.getData().add(series);
    }

    /**
     * chenyuyan
     * 设置收益率方差
     */
    public void setVariance() {
        stockName.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
        variance.setCellValueFactory(celldata -> celldata.getValue().varianceProperty());
        models2.add(stockModel1);
        varianceTable.setItems(models2);
    }

    private StockVO getStockVoByCondition(StockSearchConditionVO searchConditionVO) {
        if (searchConditionVO.getStockName() == "" && searchConditionVO.getStockID() == "") {
            AlertUtil.showErrorAlert("对不起，您未输入股票信息");
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy ");
        String starttime = format.format(searchConditionVO.getStartTime());
        String endtime = format.format(searchConditionVO.getEndTime());
        String stockID = searchConditionVO.getStockID();
        String stockName = searchConditionVO.getStockName();
        String input;
        if (stockID == null && stockName != null) {
            input = "STOCK\t" + "NULL" + "\t" + stockName + "\t" + starttime + "\t" + endtime + "\n";
        } else {
            input = "STOCK\t" + stockID + "\t" + "NULL" + "\t" + starttime + "\t" + endtime + "\n";
        }
        net.actionPerformed(input);
        String json = net.run();
        JsonUtil jsonUtil = new JsonUtil();
        StockVO stockVO1 = new StockVO();
        StockVO stockVO = (StockVO) jsonUtil.JSONToObj(json, stockVO1.getClass());
        return stockVO;
    }

    @FXML
    private void removeCompare() {
        number = 0;
        stockTable.getItems().removeAll(models);
        IncomeLine.getData().removeAll();
        varianceTable.getItems().removeAll(models2);
        closePriceLine.getData().clear();
        IncomeLine.getData().clear();
    }


    public Date changeDateStyle(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    public void setMain(Main main, Net net) {
        this.main = main;
        this.net = net;
        this.setDatePicker();

        //输入股票名进行模糊查找的监听
        stockField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fuzzyCheck.getItems().removeAll();
//                System.out.println("Remove!");
                if(searchWayChoice.getValue().equals("股票名称搜索")){

                    reStockName = ".*";

                    if(stockField.getText()!=""){
                        stockNameImport = stockField.getText();
                        if(stockNameImport!=null) {
                            for (int i = 0; i < stockNameImport.length(); i++) {
                                reStockName = reStockName + stockNameImport.charAt(i) + ".*";
//                                System.out.println("正则表达式为：" + reStockName);
                            }

                            ArrayList<String> fuzzy = FuzzyCheck();

                            if(fuzzy!=null) {
                                ObservableList<String> items = FXCollections.observableArrayList(
                                        fuzzy);
                                fuzzyCheck.setItems(items);

                                fuzzyCheck.setVisible(true);
                            }else{
                                fuzzyCheck.setVisible(false);
                            }
                        }
                    }
                }
            }
        });

        fuzzyCheck.getSelectionModel().selectedItemProperty().addListener(

                new ChangeListener<String>() {

                    public void changed(ObservableValue<? extends String> ov,

                                        String old_val, String new_val) {


                        stockField.setText(new_val);



                    }

                });



    }

    public ArrayList<String> FuzzyCheck(){
        ArrayList<String> result = new ArrayList<>();
        if(reStockName!=".*"){
        Pattern pattern = Pattern.compile(reStockName);
        for(int i=0;i<allStockName.length;i++){
            Matcher matcher = pattern.matcher(allStockName[i]);
            if(matcher.matches()){
//                System.out.println("匹配："+allStockName[i]);
                result.add(allStockName[i]);

                }

            }
            return result;
        }else{
            return  null;
        }
    }

    /**
     * get a list in the filepath
     * @param filePath
     * @return
     */
    public String[] getNameList(String filePath){
        List<String> content=new ArrayList<String>();

        content = this.readFile(filePath);

        int nums = content.size();
        String[] nameList = new String[nums];

        for(int i = 0;i<nums;i++){

            String tempName = content.get(i).split("\t")[1];
            nameList[i] = tempName;

        }

        return nameList;
    }

    private  List<String> readFile(String path){
        List<String> content=new ArrayList<String>();

        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line);
//                System.out.println(line);
            }
        }catch (Exception e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

//
        /*for (String temp:content
                ) {
            System.out.println(temp);
        }*/
        return content;

    }

//    public static void main(String[] args) {
//        ContrastController test = new ContrastController();
//        test.readFile("presentation/name_code.csv");
//    }

    @FXML
    private void initialize() {
//        main.lodaing();
        searchWayChoice.setItems(FXCollections.observableArrayList(
                "股票名称搜索", "股票编号搜索"));
        searchWayChoice.getSelectionModel().select(0);
        number = 0;
        models = FXCollections.observableArrayList();
        models2 = FXCollections.observableArrayList();

        fuzzyCheck.setVisible(false);
        reStockName = ".*";

/*        net.actionPerformed("GET");
        String json = net.run();
        JsonUtil jsonUtil = new JsonUtil();*/

//        StockVO stockVO1 = new StockVO();
//        StockVO stockVO = (StockVO) jsonUtil.JSONToObj(json, stockVO1.getClass());
        allStockName = this.getNameList("presentation/name_code.csv");







//        allStockName[0] = "深发展A";
//        allStockName[1] = "b股";
       /* String  customerName = "深";
        String reCustomerName = ".*";
        if(customerName!=null){
            for(int i = 0 ; i < customerName.length(); i++){
                reCustomerName= reCustomerName + customerName.charAt(i)  +".*";
            }
        }
        System.out.println("姓名正则:   "+reCustomerName);

        String s = "aaaaaabcc";

        Pattern pattern = Pattern.compile(reCustomerName);
        Matcher matcher = pattern.matcher(s);
       System.out.print(matcher.matches());*/
//       main.closeExtraStage();
    }
}
