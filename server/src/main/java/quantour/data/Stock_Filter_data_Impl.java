package quantour.data;

import quantour.data.datastructure.Index;
import quantour.data.datastructure.StockNameNCode;
import quantour.dataservice.DataReader_data;
import quantour.dataservice.Stock_Filter_data;

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

    /*public Stock_Filter_data_Impl(List<Stock> stockList) {
        this.stockList = stockList;
    }*/

    public Stock_Filter_data_Impl(DataReader_data dataReaderData){
        this.stockList=dataReaderData.readStockList();
        this.plateList=dataReaderData.readPlate();
        this.indexList=dataReaderData.readIndex();
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
                List<Stock> info=stockList.stream().filter(stock -> stock.getCode()==code).collect(Collectors.toList());
                if(info!=null&&!info.isEmpty()){
                    result.put(code,info);
                }
            }
        }
        return result;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public Map<String, List<StockNameNCode>> getPlateList() {
        return plateList;
    }

    public Map<String, List<Index>> getIndexList() {
        return indexList;
    }
}
