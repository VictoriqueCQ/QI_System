package ui.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import quantour.vo.StockVO;
import ui.AlertUtil;
import ui.JsonUtil;
import ui.Main;
import ui.Net;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by xjwhhh on 2017/3/4.
 */
public class CandlestickChartController {
    private Main main;
    private Net net;
    private StockVO stockVO;

    private String[] allStockName;

    @FXML
    private AnchorPane insidePane;

    @FXML
    private javafx.scene.chart.NumberAxis number = new javafx.scene.chart.NumberAxis();

    @FXML
    private CategoryAxis time = new CategoryAxis();

    @FXML
    private LineChart<String, Number> lineChart = new LineChart<>(time, number);

    @FXML
    private DatePicker startTimeDatePicker;

    @FXML
    private DatePicker endTimeDatePicker;

    @FXML
    private TextField stockNameTextField;

    @FXML
    private ListView<String> fuzzyCheck;//模糊搜索

    static String stockNameImport;//输入的股票名
    static String reStockName;

    @FXML
    private TextField stockNumberTextField;

    @FXML
    private Button searchButton;


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
     * 搜索按钮，根据条件显示K线图和均线图
     */
    @FXML
    private void search() {
        main.lodaing();
        this.createEMA();
        SwingNode swingNode = this.createCandlestickChart();
//        main.closeExtraStage();
        if (swingNode != null) {
            insidePane.getChildren().add(swingNode);
        } else {
            AlertUtil.showErrorAlert("对不起，不存在这只股票");
        }
//        main.closeExtraStage();

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

    /**
     * 绘制K线图
     */
    private SwingNode createCandlestickChart() {
        double highValue = Double.MIN_VALUE;//设置K线数据当中的最大值
        double minValue = Double.MAX_VALUE;//设置K线数据当中的最小值
        double high2Value = Double.MIN_VALUE;//设置成交量的最大值
        double min2Value = Double.MAX_VALUE;//设置成交量的最低值

        if (!(stockVO == null)) {
            OHLCSeries series = new OHLCSeries("");//蜡烛图，高开低收数据序列，股票K线图的四个数据，依次是开，高，低，收
            series = this.addCandlestickChartData(series, stockVO);
            final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();//保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
            seriesCollection.addSeries(series);

            TimeSeries series2 = new TimeSeries("");//对应时间成交量数据series2 = this.adTimeSeriesCollectionData(series2, stockVO);
            series2 = this.adTimeSeriesCollectionData(series2, stockVO);
            TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();//保留成交量数据的集合
            timeSeriesCollection.addSeries(series2);

            //获取K线数据的最高值和最低值
            int seriesCount = seriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
            for (int i = 0; i < seriesCount; i++) {
                int itemCount = seriesCollection.getItemCount(i);//每一个序列有多少个数据项
                for (int j = 0; j < itemCount; j++) {
                    if (highValue < seriesCollection.getHighValue(i, j)) {//取第i个序列中的第j个数据项的最大值
                        highValue = seriesCollection.getHighValue(i, j);
                    }
                    if (minValue > seriesCollection.getLowValue(i, j)) {//取第i个序列中的第j个数据项的最小值
                        minValue = seriesCollection.getLowValue(i, j);
                    }
                }
            }

            //获取最高值和最低值
            int seriesCount2 = timeSeriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
            for (int i = 0; i < seriesCount2; i++) {
                int itemCount = timeSeriesCollection.getItemCount(i);//每一个序列有多少个数据项
                for (int j = 0; j < itemCount; j++) {
                    if (high2Value < timeSeriesCollection.getYValue(i, j)) {//取第i个序列中的第j个数据项的值
                        high2Value = timeSeriesCollection.getYValue(i, j);
                    }
                    if (min2Value > timeSeriesCollection.getYValue(i, j)) {//取第i个序列中的第j个数据项的值
                        min2Value = timeSeriesCollection.getYValue(i, j);
                    }
                }
            }

            final CandlestickRenderer candlestickRender = new CandlestickRenderer();//设置K线图的画图器，必须申明为final，后面要在匿名内部类里面用到
            candlestickRender.setUseOutlinePaint(true); //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
            candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//设置如何对K线图的宽度进行设定
            candlestickRender.setAutoWidthGap(0.001);//设置各个K线图之间的间隔
            candlestickRender.setUpPaint(Color.RED);//设置股票上涨的K线图颜色
            candlestickRender.setDownPaint(Color.GREEN);//设置股票下跌的K线图颜色
            DateAxis x1Axis = new DateAxis();//设置x轴，也就是时间轴
            x1Axis.setAutoRange(false);//设置不采用自动设置时间范围
            try {
                LocalDate startLocalDate = startTimeDatePicker.getValue();
                LocalDate endLocalDate = endTimeDatePicker.getValue().plusDays(1);
                Date startDate = this.changeDateStyle(startLocalDate);
                Date endDate = this.changeDateStyle(endLocalDate);
                x1Axis.setRange(startDate, endDate);//设置时间范围，注意时间的最大值要比已有的时间最大值要多一天
                long width=startLocalDate.until(endLocalDate, ChronoUnit.DAYS)*10;
                if(width>1178){
                    insidePane.setPrefWidth(width);
                }
                if(width>2000){
                    insidePane.setPrefWidth(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
            x1Axis.setAutoTickUnitSelection(false);//设置不采用自动选择刻度值
            x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
            x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
            x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 7));//设置时间刻度的间隔，一般以周为单位
            x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//设置显示时间的格式
            NumberAxis y1Axis = new NumberAxis();//设定y轴，就是数字轴
            y1Axis.setAutoRange(false);//不使用自动设定范围
            y1Axis.setRange(minValue * 0.9, highValue * 1.1);//设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
            y1Axis.setTickUnit(new NumberTickUnit((highValue * 1.1 - minValue * 0.9) / 10));//设置刻度显示的密度
            XYPlot plot1 = new XYPlot(seriesCollection, x1Axis, y1Axis, candlestickRender);//设置画图区域对象

            XYBarRenderer xyBarRender = new XYBarRenderer() {
                private static final long serialVersionUID = 1L;//为了避免出现警告消息，特设定此值

                public Paint getItemPaint(int i, int j) {//匿名内部类用来处理当日的成交量柱形图的颜色与K线图的颜色保持一致
                    if (seriesCollection.getCloseValue(i, j) > seriesCollection.getOpenValue(i, j)) {//收盘价高于开盘价，股票上涨，选用股票上涨的颜色
                        return candlestickRender.getUpPaint();
                    } else {
                        return candlestickRender.getDownPaint();
                    }
                }
            };
            xyBarRender.setMargin(0.1);//设置柱形图之间的间隔
            NumberAxis y2Axis = new NumberAxis();//设置Y轴，为数值,后面的设置，参考上面的y轴设置
            y2Axis.setAutoRange(false);
            y2Axis.setRange(min2Value * 0.9, high2Value * 1.1);
            y2Axis.setTickUnit(new NumberTickUnit((high2Value * 1.1 - min2Value * 0.9) / 4));
            XYPlot plot2 = new XYPlot(timeSeriesCollection, null, y2Axis, xyBarRender);//建立第二个画图区域对象，主要此时的x轴设为了null值，因为要与第一个画图区域对象共享x轴

            CombinedDomainXYPlot combineddomainxyplot = new CombinedDomainXYPlot(x1Axis);//建立一个恰当的联合图形区域对象，以x轴为共享轴
            combineddomainxyplot.add(plot1, 2);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域2/3
            combineddomainxyplot.add(plot2, 1);//添加图形区域对象，后面的数字是计算这个区域对象应该占据多大的区域1/3
            combineddomainxyplot.setGap(10);//设置两个图形区域对象之间的间隔空间

            JFreeChart chart = new JFreeChart("K线图", JFreeChart.DEFAULT_TITLE_FONT, combineddomainxyplot, false);


            //颜色设置
            chart.setBackgroundPaint(Color.black);
            x1Axis.setTickMarkPaint(Color.yellow);
            y1Axis.setTickMarkPaint(Color.yellow);
            y2Axis.setTickMarkPaint(Color.yellow);
            x1Axis.setTickLabelPaint(Color.white);
            y1Axis.setTickLabelPaint(Color.white);
            y2Axis.setTickLabelPaint(Color.white);
            plot1.setBackgroundPaint(Color.black);
            plot2.setBackgroundPaint(Color.black);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(4000 , 950));

            SwingNode swingNode = new SwingNode();
            swingNode.setContent(chartPanel);
            return swingNode;
        } else {
            return null;
        }
    }

    /**
     * 为蜡烛图注入数据
     *
     * @param ohlcSeries
     * @param stockVO
     * @return
     */
    private OHLCSeries addCandlestickChartData(OHLCSeries ohlcSeries, StockVO stockVO) {
        ohlcSeries.clear();
        double[] open = stockVO.getOpen();
        double[] high = stockVO.getHigh();
        double[] low = stockVO.getLow();
        double[] close = stockVO.getClose();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        List<Date> dates = stockVO.getDates();
        for (int i = 0; i < open.length; i++) {
            String s = format.format(dates.get(i));
            String[] str = s.split("/");
            int year = Integer.parseInt(str[0]);
            int month = Integer.parseInt(str[1]);
            int day = Integer.parseInt(str[2]);
            ohlcSeries.add(new Day(day, month, year), open[i], high[i], low[i], close[i]);
        }
        return ohlcSeries;
    }

    /**
     * 为成交量图注入数据
     *
     * @param timeSeries
     * @param stockVO
     * @return
     */
    private TimeSeries adTimeSeriesCollectionData(TimeSeries timeSeries, StockVO stockVO) {
        int[] volume = stockVO.getVolume();
        List<Date> dates = stockVO.getDates();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = 0; i < volume.length; i++) {
            String s = format.format(dates.get(i));
            String[] str = s.split("/");
            int year = Integer.parseInt(str[0]);
            int month = Integer.parseInt(str[1]);
            int day = Integer.parseInt(str[2]);
            timeSeries.add(new Day(day, month, year), volume[i]);
        }
        return timeSeries;
    }


    /**
     * 绘制均线图
     */
    private void createEMA() {
        lineChart.getData().clear();
        lineChart.setTitle("均线图(EMA)");

        //数据注入
        stockVO = this.getStockVOByCondition();
        if (!(stockVO == null)) {
            double[] average5 = stockVO.getAverage5();
            double[] average10 = stockVO.getAverage10();
            double[] average20 = stockVO.getAverage20();
            double[] average30 = stockVO.getAverage30();
            double[] average60 = stockVO.getAverage60();
            List<Date> dates = stockVO.getDates();
            double lowest = average5[0];
            double highest = 0;
            for (int i = 0; i < average5.length; i++) {
                if (lowest > average5[i]) {
                    lowest = average5[i];
                }
                if (lowest > average10[i]) {
                    lowest = average10[i];
                }
                if (lowest > average20[i]) {
                    lowest = average20[i];
                }
                if (lowest > average30[i]) {
                    lowest = average30[i];
                }
                if (lowest > average60[i]) {
                    lowest = average60[i];
                }
                if (highest < average5[i]) {
                    highest = average5[i];
                }
                if (highest < average10[i]) {
                    highest = average10[i];
                }
                if (highest < average20[i]) {
                    highest = average20[i];
                }
                if (highest < average30[i]) {
                    highest = average30[i];
                }
                if (highest > average60[i]) {
                    highest = average60[i];
                }
            }
            XYChart.Series series_average5 = new XYChart.Series();
            series_average5 = this.addEMAData(series_average5, average5, dates);
            series_average5.setName("5天");
            XYChart.Series series_average10 = new XYChart.Series();
            series_average10 = this.addEMAData(series_average10, average10, dates);
            series_average10.setName("10天");
            XYChart.Series series_average20 = new XYChart.Series();
            series_average20 = this.addEMAData(series_average20, average20, dates);
            series_average20.setName("20天");
            XYChart.Series series_average30 = new XYChart.Series();
            series_average30 = this.addEMAData(series_average30, average30, dates);
            series_average30.setName("30天");
            XYChart.Series series_average60 = new XYChart.Series();
            series_average60 = this.addEMAData(series_average60, average60, dates);
            series_average60.setName("60天");
            lineChart.getData().addAll(series_average5, series_average10, series_average20, series_average30, series_average60);
            number.setAutoRanging(false);
            number.setForceZeroInRange(false);
            number.setLowerBound(5);
            number.setUpperBound(highest * 1.1);
            number.setTickUnit((highest * 1.1 - 5) / 10);
            int width=dates.size()*10;
            if(width>1178){
                lineChart.setPrefWidth(width);
            }


        }
    }

    /**
     * 对均线图进行数据注入
     *
     * @param series
     * @param data
     * @return
     */
    private XYChart.Series addEMAData(XYChart.Series series, double[] data, List<Date> dates) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        for (int i = data.length - 1; i >= 0; i--) {
            String s = format.format(dates.get(i));
            series.getData().add(new XYChart.Data(s, data[i]));
        }
        return series;
    }

    /**
     * 将Localdate转化为Date
     *
     * @param localDate
     * @return
     */
    public Date changeDateStyle(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 根据条件寻找对应股票
     *
     * @return
     */
    private StockVO getStockVOByCondition() {
        String stockName = stockNameTextField.getText();
        String stockID = stockNumberTextField.getText();
        LocalDate startLocalDate = startTimeDatePicker.getValue();
        LocalDate endLocalDate = endTimeDatePicker.getValue();
        Date startDate = this.changeDateStyle(startLocalDate);
        Date endDate = this.changeDateStyle(endLocalDate);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy ");
        String starttime = format.format(startDate);
        String endtime = format.format(endDate);

        String input;
        //检查输入是否完整
        if (stockName.equals("") && stockID.equals("")) {
            AlertUtil.showErrorAlert("对不起，您未输入股票信息");
            return null;
        } else if (!stockName.equals("") && stockID.equals("")) {
            input = "STOCK\t" + "NULL" + "\t" + stockName + "\t" + starttime + "\t" + endtime + "\n";

        } else if (stockName.equals("") && (!stockID.equals(""))) {
            input = "STOCK\t" + stockID + "\t" + "NULL" + "\t" + starttime + "\t" + endtime + "\n";
        } else {
            input = "STOCK\t" + stockID + "\t" + stockName + "\t" + starttime + "\t" + endtime + "\n";
        }

        net.actionPerformed(input);
        String json = net.run();
        JsonUtil jsonUtil = new JsonUtil();
        StockVO stockVO1 = new StockVO();
        return (StockVO) jsonUtil.JSONToObj(json, stockVO1.getClass());
    }

    /**
     * 读取name_code.csv文件
     * @param stockPath
     * @return
     */
    private ArrayList<StockVO> readStockList(String stockPath) {
        ArrayList<StockVO> stockList = new ArrayList<StockVO>();
        File f = new File(stockPath);
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy");

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String row = br.readLine();
            while (row != null) {
                List<String> stockInfo = Arrays.asList(row.split("\t"));
                String name = stockInfo.get(0);
                int code=Integer.parseInt(stockInfo.get(1));
                StockVO stockVO = new StockVO();
                stockVO.setName(name);
                stockVO.setCode(code);
                stockList.add(stockVO);
                row = br.readLine();
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return stockList;
    }

    /**
     * get a list in the filepath
     *
     * @param filePath
     * @return
     */
    public String[] getNameList(String filePath) {
        List<String> content = new ArrayList<String>();
        content = this.readFile(filePath);
        int nums = content.size();
        String[] nameList = new String[nums];
        for (int i = 0; i < nums; i++) {
            String tempName = content.get(i).split("\t")[1];
            nameList[i] = tempName;
        }
        return nameList;
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

    public ArrayList<String> FuzzyCheck(){
        ArrayList<String> result = new ArrayList<>();
        if(reStockName!=".*"){
            Pattern pattern = Pattern.compile(reStockName);
            for(int i=0;i<allStockName.length;i++){
                Matcher matcher = pattern.matcher(allStockName[i]);
                if(matcher.matches()){
                    result.add(allStockName[i]);

                }

            }
            return result;
        }else{
            return  null;
        }
    }

    private void setFuzzyCheck(){
        //模糊搜索
        fuzzyCheck.setVisible(false);
        reStockName = ".*";
        allStockName = this.getNameList("presentation/name_code.csv");
        stockNameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fuzzyCheck.getItems().removeAll();
                reStockName = ".*";
                if(stockNameTextField.getText()!=""){
                    stockNameImport = stockNameTextField.getText();
                    if(stockNameImport!=null) {
                        for (int i = 0; i < stockNameImport.length(); i++) {
                            reStockName = reStockName + stockNameImport.charAt(i) + ".*";
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
        });

        fuzzyCheck.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov,
                                        String old_val, String new_val) {
                        stockNameTextField.setText(new_val);
                        fuzzyCheck.setVisible(false);
                    }
                });
    }

    public void setMain(Main main, Net net) {
        this.main = main;
        this.net = net;
        this.setDatePicker();
        this.setFuzzyCheck();
    }

    /**
     * 附带条件的跳转，自动填充条件并展示数据
     * @param main
     * @param net
     * @param name
     * @param code
     * @param startdate
     * @param enddate
     */
    public void setMain(Main main,Net net,String name, String code, LocalDate startdate,LocalDate enddate){
        this.main=main;
        this.net=net;
        startTimeDatePicker.setValue(startdate);
        endTimeDatePicker.setValue(enddate);
        stockNameTextField.setText(name);
        stockNumberTextField.setText(code);
        this.search();
        this.setFuzzyCheck();
    }


}
