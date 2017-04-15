package ui.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import quantour.vo.StockVO;
import ui.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xjwhhh on 2017/4/15.
 */
public class SelectStockController {
    private Main main;
    private Net net;

    private String[] allStockName;

    @FXML
    private TableView<StockModel> stockTable;

    @FXML
    private TableColumn<StockModel, String> stockName;

    @FXML
    private TableColumn<StockModel, String> stockID;

//    private ObservableList<StockModel> models;


    @FXML
    private ComboBox<String> selectComboBox;

    @FXML
    private Button ensureButton1;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private RadioButton shangzhengRadioButton;

    @FXML
    private RadioButton hushenRadioButton;

    @FXML
    private RadioButton shenzhenRadioButton;

    @FXML
    private void exit() {
        main.exitSystem1();
    }

    @FXML
    private void zoomout(){
        main.zoomoutButton1();
    }




    @FXML
    private void stockSearch() {
        if (!searchTextField.getText().equals("")) {
            String input = "";
            net.actionPerformed(input);
            String json = net.run();
            JsonUtil jsonUtil = new JsonUtil();
            StockVO stockVO1 = new StockVO();
            StockVO stockVO = (StockVO) jsonUtil.JSONToObj(json, stockVO1.getClass());
            if (stockVO != null) {
                ArrayList<StockVO> stockVOList = new ArrayList<StockVO>();
                stockVOList.add(stockVO);
                setTableView(stockVOList);
            } else {
                AlertUtil.showWarningAlert("对不起，该股票不存在");
            }
        } else {
            AlertUtil.showErrorAlert("对不起，您未输入股票名称");
        }
    }

    @FXML
    private void sectionSearch(){
            String section =selectComboBox.getValue();
            ArrayList<StockVO> stockVOList=readStockList("stock-section\\"+section+".csv");
            setTableView(stockVOList);
    }

    public ArrayList<StockVO> readStockList(String stockPath) {
        ArrayList<StockVO> stockList = new ArrayList<StockVO>();
        File f = new File(stockPath);
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy");

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String row = br.readLine();
            while (row != null) {
                List<String> stockInfo = Arrays.asList(row.split("\t"));
                int code = Integer.parseInt(stockInfo.get(1));
                String name = stockInfo.get(0);
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

    private void setTableView(ArrayList<StockVO> stockVOList) {
        ObservableList<StockModel> models = FXCollections.observableArrayList();
        stockName.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
        stockID.setCellValueFactory(celldata -> celldata.getValue().idProperty());
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
        int code=stockVO.getCode();
        String code1=String.valueOf(code);
        char[] code2=code1.toCharArray();
        int t=6-code2.length;
        for(int i=0;i<t;i++){
            code1="0"+code1;
        }
        model.setID(code1);
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

    private void setComboBox(){
        ArrayList<String> sectionList=new ArrayList<String>();
        sectionList.add("采掘服务业");
        selectComboBox.getItems().addAll(sectionList);
        selectComboBox.setValue(sectionList.get(0));

    }

    public void setMain(Main main, Net net) {
        this.main = main;
        this.net = net;

        //单选框组合
        ToggleGroup group = new ToggleGroup();
        shangzhengRadioButton.setToggleGroup(group);
        shenzhenRadioButton.setToggleGroup(group);
        hushenRadioButton.setToggleGroup(group);


        //表格

//        allStockName = this.getNameList("C:\\Users\\xjwhh\\IdeaProjects\\QI_System\\server\\name_code.csv");

        //设置ComboBox
        setComboBox();

        searchTextField.setText("股票名称");

    }


}
