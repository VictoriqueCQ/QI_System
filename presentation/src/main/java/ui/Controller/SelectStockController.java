package ui.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import quantour.vo.StockVO;
import ui.AlertUtil;
import ui.Main;
import ui.Net;
import ui.StockModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xjwhhh on 2017/4/15.
 */
public class SelectStockController {
    private Main main;
    private Net net;
    private ReturnsController returnsController;

    private String path1="";
    private String[] allStockName;

    boolean isyourchoice;

    HashMap<String, String> name_code=new HashMap<String,String>();



    private ArrayList<String> stockNameList=new ArrayList<String>();

    private ArrayList<String> stockCodeList=new ArrayList<String>();

    private ArrayList<String> oldstockCodeList =new ArrayList<String>();

    private ArrayList<String> sectionNameList=new ArrayList<String>();

    @FXML
    private TableView<StockModel> stockTable;

    @FXML
    private TableColumn<StockModel, String> stockName;

    @FXML
    private TableColumn<StockModel, String> stockID;

    @FXML
    private TableColumn<StockModel, String> isChoosen;

    @FXML
    private ComboBox<String> selectComboBox;

    @FXML
    private Button ensureButton1;

    @FXML
    private TextField searchTextField;

    @FXML
    private ListView<String> fuzzyCheck;//模糊搜索

    static String stockNameImport;//输入的股票名
    static String reStockName;

    @FXML
    private Button searchButton;

    @FXML
    private RadioButton selectAllRadioButton;

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

    boolean isselectall=false;

    @FXML
    private void selectAllStocks(){
        if(!selectComboBox.getValue().equals(null)) {
            ObservableList<StockModel> models = FXCollections.observableArrayList();
            String section = selectComboBox.getValue();
            ArrayList<StockVO> stockVOList = readStockList(path1 + "documentation/stock-section/" + section + ".txt");
            setTableView(stockVOList);
            if (!isselectall) {
                for (int i = 0; i < stockVOList.size(); i++) {
                    StockVO stockVO = stockVOList.get(i);
                    StockModel stockModel = stockVOtoStockModle(stockVO);
                    stockNameList.add(stockModel.getName());
                    stockCodeList.add(stockModel.getID());
                    stockModel.setIsChoosen("是");
                    models.add(stockModel);
                }
                stockTable.setItems(models);
                isselectall=true;
            }
            else{
                for (int i = 0; i < stockVOList.size(); i++) {
                    StockVO stockVO = stockVOList.get(i);
                    StockModel stockModel = stockVOtoStockModle(stockVO);
                    Iterator<String> iterable1=stockNameList.iterator();
                    while(iterable1.hasNext()){
                        if(iterable1.next().equals(stockModel.getName())){
                            iterable1.remove();
                        }
                    }
                    Iterator<String> iterable2=stockCodeList.iterator();
                    while(iterable2.hasNext()){
                        if(iterable2.next().equals(stockModel.getID())){
                            iterable2.remove();
                        }
                    }
                    stockModel.setIsChoosen("");
                    models.add(stockModel);
                }
                stockTable.setItems(models);
                isselectall=false;
            }
        }
    }

    /**
     * 自选股票板块
     */
    @FXML
    private void stockSearch() {
        if (!searchTextField.getText().equals("")) {
            StockVO stockVO=new StockVO();
            stockVO.setName(searchTextField.getText());
            for (Map.Entry<String, String> entry : name_code.entrySet()) {
                if(entry.getValue().equals(searchTextField.getText())){
                    stockVO.setCode(Integer.parseInt(entry.getKey()));
                    break;
                }
            }
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

    /**
     * 自选股票选择结束
     */
    @FXML
    private void finishStockSelect(){
        if(stockNameList.size()>0) {
            HashSet<String> hashset_temp = new HashSet<String>(stockNameList);
            stockNameList = new ArrayList<String>(hashset_temp);
            HashSet<String> hashset_temp1 = new HashSet<String>(stockCodeList);
            stockCodeList = new ArrayList<String>(hashset_temp1);
            isyourchoice = true;
            AlertUtil.showInformationAlert("您此次选中" + stockNameList.size() + "只股票");
            main.closeExtraStage();
            returnsController.setSelectStockComboBox(stockNameList, stockCodeList);
        }
        else{
            AlertUtil.showWarningAlert("对不起，您未选择股票");
        }
    }

    /**
     * 板块选择结束
     */
    @FXML
    private void finishSectionSelect(){
        sectionNameList.clear();
        if(shangzhengRadioButton.isSelected()){
            sectionNameList.add("上证指数");
        }
        if(hushenRadioButton.isSelected()){
            sectionNameList.add("沪深300");
        }
        if(shenzhenRadioButton.isSelected()){
            sectionNameList.add("深圳成指");
        }
        if(sectionNameList.size()>=1) {
            String text = "您此次选中";
            for (int i = 0; i < sectionNameList.size() - 1; i++) {
                text += sectionNameList.get(i) + ",";
            }
            text += sectionNameList.get(sectionNameList.size() - 1);
            text += "板块中的股票";
            AlertUtil.showInformationAlert(text);
            isyourchoice = false;
            main.closeExtraStage();
            returnsController.setSectionComboBox(sectionNameList);
        }
        else{
            AlertUtil.showWarningAlert("对不起，您未选择板块");
        }

    }

    /**
     * 选择股票证监会板块
     */
    @FXML
    private void sectionSearch(){
            String section =selectComboBox.getValue();
            ArrayList<StockVO> stockVOList=readStockList(path1+"documentation/stock-section/"+section+".txt");
            setTableView(stockVOList);
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

    /**
     * 显示对应板块的股票并设置监听
     * @param stockVOList
     */
    private void setTableView(ArrayList<StockVO> stockVOList) {
        ObservableList<StockModel> models = FXCollections.observableArrayList();
        stockName.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
        stockName.setCellFactory(new Callback<TableColumn<StockModel, String>, TableCell<StockModel, String>>() {

            @Override
            public TableCell<StockModel, String> call(TableColumn<StockModel, String> param) {
                TextFieldTableCell<StockModel, String> cell = new TextFieldTableCell<>();

                cell.setOnMouseClicked((MouseEvent t) -> {
                    String name=(stockTable.getItems().get(cell.getIndex()).getName());
                    String code=(stockTable.getItems().get(cell.getIndex()).getID());
                    //取消选择
                    if (t.getClickCount() == 2) {
                        if(cell.getIndex()<=models.size()){
                            Iterator<String> iterable1=stockNameList.iterator();
                            while(iterable1.hasNext()){
                                if(iterable1.next().equals(name)){
                                    iterable1.remove();
                                }
                            }
                            Iterator<String> iterable2=stockCodeList.iterator();
                            while(iterable2.hasNext()){
                                if(iterable2.next().equals(name)){
                                    iterable2.remove();
                                }
                            }
                            stockTable.getItems().get(cell.getIndex()).setIsChoosen("");
                            if(cell.getIndex()%2==0){
//                                cell.getTableRow().setStyle("-fx-background-color:rgb(80,80,80);");
                                cell.setTextFill(Color.rgb(200,200,200));
                            }
                            else{
//                                cell.getTableRow().setStyle("-fx-background-color: rgb(50,50,50);");
                                cell.setTextFill(Color.rgb(200,200,200));
                            }
                        }
                    }
                    //选择
                    else if(t.getClickCount() == 1){
                        try {
                            if(cell.getIndex()<=models.size()){
                                stockNameList.add(name);
                                stockCodeList.add(code);
                                stockTable.getItems().get(cell.getIndex()).setIsChoosen("是");
//                                cell.getTableRow().setStyle("-fx-background-color:black;");
                                cell.setTextFill(Color.WHITE);
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return cell;
            }
        });

        stockID.setCellValueFactory(celldata -> celldata.getValue().idProperty());
        stockID.setCellFactory(new Callback<TableColumn<StockModel, String>, TableCell<StockModel, String>>() {

            @Override
            public TableCell<StockModel, String> call(TableColumn<StockModel, String> param) {
                TextFieldTableCell<StockModel, String> cell = new TextFieldTableCell<>();

                cell.setOnMouseClicked((MouseEvent t) -> {
                    String name=(stockTable.getItems().get(cell.getIndex()).getName());
                    String code=(stockTable.getItems().get(cell.getIndex()).getID());
                    if (t.getClickCount() == 2) {
                        if(cell.getIndex()<=models.size()){
                            Iterator<String> iterable1=stockNameList.iterator();
                            while(iterable1.hasNext()){
                                if(iterable1.next().equals(name)){
                                    iterable1.remove();
                                }
                            }
                            Iterator<String> iterable2=stockCodeList.iterator();
                            while(iterable2.hasNext()){
                                if(iterable2.next().equals(name)){
                                    iterable2.remove();
                                }
                            }
                            stockTable.getItems().get(cell.getIndex()).setIsChoosen("");
                            if(cell.getIndex()%2==0){
//                                cell.getTableRow().setStyle("-fx-background-color:rgb(80,80,80);");
                                cell.setTextFill(Color.rgb(200,200,200));
                            }
                            else{
//                                cell.getTableRow().setStyle("-fx-background-color: rgb(50,50,50);");
                                cell.setTextFill(Color.rgb(200,200,200));
                            }
                        }
                    }
                    else if(t.getClickCount() == 1){
                        try {
                            if(cell.getIndex()<=models.size()){
                                stockNameList.add(name);
                                stockCodeList.add(code);
                                stockTable.getItems().get(cell.getIndex()).setIsChoosen("是");
//                                cell.getTableRow().setStyle("-fx-background-color:black;");
                                cell.setTextFill(Color.WHITE);
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return cell;
            }
        });

        isChoosen.setCellValueFactory(celldata -> celldata.getValue().isChoosenProperty());
        isChoosen.setCellFactory(new Callback<TableColumn<StockModel, String>, TableCell<StockModel, String>>() {

            @Override
            public TableCell<StockModel, String> call(TableColumn<StockModel, String> param) {
                TextFieldTableCell<StockModel, String> cell = new TextFieldTableCell<>();

                cell.setOnMouseClicked((MouseEvent t) -> {
                    String name=(stockTable.getItems().get(cell.getIndex()).getName());
                    String code=(stockTable.getItems().get(cell.getIndex()).getID());
                    if (t.getClickCount() == 2) {
                        if(cell.getIndex()<=models.size()){
                            Iterator<String> iterable1=stockNameList.iterator();
                            while(iterable1.hasNext()){
                                if(iterable1.next().equals(name)){
                                    iterable1.remove();
                                }
                            }
                            Iterator<String> iterable2=stockCodeList.iterator();
                            while(iterable2.hasNext()){
                                if(iterable2.next().equals(name)){
                                    iterable2.remove();
                                }
                            }
                            stockTable.getItems().get(cell.getIndex()).setIsChoosen("");
                            if(cell.getIndex()%2==0){
//                                cell.getTableRow().setStyle("-fx-background-color:rgb(80,80,80);");
                                cell.setTextFill(Color.rgb(200,200,200));
                            }
                            else{
//                                cell.getTableRow().setStyle("-fx-background-color: rgb(50,50,50);");
                                cell.setTextFill(Color.rgb(200,200,200));
                            }
                        }
                    }
                    else if(t.getClickCount() == 1){
                        try {
                            if(cell.getIndex()<=models.size()){
                                stockNameList.add(name);
                                stockCodeList.add(code);
                                stockTable.getItems().get(cell.getIndex()).setIsChoosen("是");
//                                cell.getTableRow().setStyle("-fx-background-color:black;");
                                cell.setTextFill(Color.WHITE);
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
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
        int code=stockVO.getCode();
        String code1=String.valueOf(code);
        char[] code2=code1.toCharArray();
        int t=6-code2.length;
        for(int i=0;i<t;i++){
            code1="0"+code1;
        }
        model.setID(code1);
        for (int i=0;i<stockCodeList.size();i++){
            if(code1.equals(stockCodeList.get(i))){
                model.setIsChoosen("是");
                stockNameList.add(model.getName());
                stockCodeList.add(model.getID());
                break;
            }
        }
        return model;
    }

    /**
     * 初始化股票板块ComboBox
     */
    private void setComboBox(){
        ArrayList<String> sectionList=new ArrayList<String>();
        sectionList.add("暂无板块分类");
        sectionList.add("采掘服务业");
        sectionList.add("餐饮业");
        sectionList.add("仓储业");
        sectionList.add("畜牧业");
        sectionList.add("电力、蒸汽、热水的生产和供应业");
        sectionList.add("电器机械及器材制造业");
        sectionList.add("电子元器件制造业");
        sectionList.add("房地产开发与经营业");
        sectionList.add("房地产中介服务业");
        sectionList.add("纺织业");
        sectionList.add("非金属矿物制品业");
        sectionList.add("港口业");
        sectionList.add("公路运输业");
        sectionList.add("广播电视设备制造业");
        sectionList.add("广播电影电视业");
        sectionList.add("化学原料及化学制品制造业");
        sectionList.add("计算机及相关设备制造业");
        sectionList.add("计算机应用服务业");
        sectionList.add("交通运输辅助业");
        sectionList.add("交通运输设备制造业");
        sectionList.add("金属制品业");
        sectionList.add("林业");
        sectionList.add("零售业");
        sectionList.add("旅馆业");
        sectionList.add("旅游业");
        sectionList.add("煤气生产和供应业");
        sectionList.add("煤炭采选业");
        sectionList.add("能源、材料和机械电子设备批发业");
        sectionList.add("农业");
        sectionList.add("普通机械制造业");
        sectionList.add("其他电子设备制造业");
        sectionList.add("其他批发业");
        sectionList.add("其他社会服务业");
        sectionList.add("其他通用零部件制造业");
        sectionList.add("商业经纪与代理业");
        sectionList.add("食品、饮料、烟草和家庭用品批发业");
        sectionList.add("食品加工业");
        sectionList.add("输配电及控制设备制造业");
        sectionList.add("水泥制造业");
        sectionList.add("水上运输业");
        sectionList.add("陶瓷制品业");
        sectionList.add("通信服务业");
        sectionList.add("通信及相关设备制造业");
        sectionList.add("通信设备制造业");
        sectionList.add("土木工程建筑业");
        sectionList.add("卫生、保健、护理服务业");
        sectionList.add("文教体育用品制造业");
        sectionList.add("信息传播服务业");
        sectionList.add("医药制造业");
        sectionList.add("银行业");
        sectionList.add("饮料制造业");
        sectionList.add("印刷业");
        sectionList.add("有色金属矿采选业");
        sectionList.add("有色金属冶炼及压延加工业");
        sectionList.add("渔业");
        sectionList.add("渔业服务业");
        sectionList.add("证券、期货业");
        sectionList.add("制造业");
        sectionList.add("中药材及中成药加工业");
        sectionList.add("专业、科研服务业");
        sectionList.add("专用设备制造业");
        sectionList.add("装修装饰业");
        sectionList.add("租赁服务业");
        selectComboBox.getItems().addAll(sectionList);
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

    /**
     * get a list in the filepath
     * @param filePath
     * @return
     */
    public String[] getNameList1(String filePath){
        List<String> content=new ArrayList<String>();
        content = this.readFile1(filePath);
        int nums = content.size();
        String[] nameList = new String[nums];
        for(int i = 0;i<nums;i++){
            String tempName = content.get(i).split("\t")[1];
            nameList[i] = tempName;
        }
        return nameList;
    }

    private  List<String> readFile1(String path) {
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


    public void setMain(Main main, Net net,ReturnsController returnsController,ArrayList<String> stockCodeList) {
        this.main = main;
        this.net = net;
        this.returnsController=returnsController;
        this.stockCodeList=stockCodeList;
        this.oldstockCodeList=stockCodeList;
        String path=String.valueOf(Main.class.getResource(""));
        String[] pathlist=path.split("/");
        for(int i=1;i<pathlist.length-1;i++){
            path1+=(pathlist[i]+"\\");
        }
        //设置ComboBox
        this.setComboBox();
        searchTextField.setText("请输入股票名称");
        //模糊搜索
        fuzzyCheck.setVisible(false);
        reStockName = ".*";
        allStockName = this.getNameList1("presentation/name_code.csv");
        name_code = this.getNameList("presentation/name_code.csv");
        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fuzzyCheck.getItems().removeAll();
                    reStockName = ".*";
                    if(searchTextField.getText()!=""){
                        stockNameImport = searchTextField.getText();
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
                        searchTextField.setText(new_val);
                        fuzzyCheck.setVisible(false);
                    }
                });
    }
}
