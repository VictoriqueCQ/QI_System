package quantour.data;

import quantour.data.datastructure.Index;
import quantour.data.datastructure.Rate;
import quantour.data.datastructure.StockNameNCode;
import quantour.dataservice.DataReader_data;
import quantour.dataservice.Stock_Filter_data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dell on 2017/4/8.
 */
public class Stock_Filter_data_Impl implements Stock_Filter_data{
    private List<Stock> stockList;
    private Map<String, List<StockNameNCode>> plateList;
    private Map<String, List<Index>> indexList;
    private List<Rate> rateList;

    /*public Stock_Filter_data_Impl(List<Stock> stockList) {
        this.stockList = stockList;
    }*/

    Stock_Filter_data_Impl(DataReader_data dataReaderData){
        this.stockList=dataReaderData.readStockList();
        this.plateList=dataReaderData.readPlate();
        this.indexList=dataReaderData.readIndex();
        this.rateList=dataReaderData.readRate();
    }

    //输入单支股票代码，得到该股票所有信息
    @Override
    public List<Stock> filterSingleStock(int code) {
        return stockList.stream().filter(stock -> stock.getCode()==code).collect(Collectors.toList());
    }

    //输入指令，得到该股票池所有股票信息
    @Override
    public Map<Integer, List<Stock>> filterStaStock(String[] quest) {
        Map<Integer,List<Stock>> result=new HashMap<>();
        for(int i=9;i<quest.length;i++){
            List<StockNameNCode> stockNameNCodes=plateList.get(quest[i]);
            for(StockNameNCode temp:stockNameNCodes){
                int code=temp.getCode();
                List<Stock> info=filterSingleStock(code);
                if(info!=null&&!info.isEmpty()){
                    result.put(code,info);
                }
            }
        }
        return result;
    }

    @Override
    public List<Stock> filterPeriodStock(Date startTime, Date endTime) {
        return stockList.stream().
                filter(stock -> stock.getDate().compareTo(startTime)>=0&&stock.getDate().compareTo(endTime)<0).
                collect(Collectors.toList());
    }


    @Override
    public List<Stock> getStockList() {
        return stockList;
    }

    @Override
    public Map<String, List<StockNameNCode>> getPlateList() {
        return plateList;
    }

    @Override
    public Map<String, List<Index>> getIndexList() {
        return indexList;
    }

    @Override
    public List<Rate> getRateList() {
        return rateList;
    }
}

