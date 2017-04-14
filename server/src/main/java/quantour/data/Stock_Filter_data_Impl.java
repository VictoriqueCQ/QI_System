package quantour.data;

import quantour.data.datastructure.Index;
import quantour.data.datastructure.StockNameNCode;
import quantour.dataservice.DataReader_data;
import quantour.dataservice.Stock_Filter_data;

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

    @Override
    public List<Stock> filterSingleStock(int code) {
        return stockList.stream().filter(stock -> stock.getCode()==code).collect(Collectors.toList());
    }

    @Override
    public List<Stock> filterStaStock(String[] quest) {

        return null;
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
