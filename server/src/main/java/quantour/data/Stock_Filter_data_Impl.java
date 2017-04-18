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
    public double filterPeriodRate(Date startTime, Date endTime) {
        List<Rate> requiredList=rateList.stream().
                filter(rate -> rate.getDate().compareTo(endTime)<=0).
                collect(Collectors.toList());
        Rate endRate=requiredList.get(requiredList.size()-1);
        requiredList=requiredList.stream().
                filter(rate -> rate.getDate().compareTo(startTime)<=0).
                collect(Collectors.toList());
        Rate startRate=requiredList.get(requiredList.size()-1);

        requiredList=requiredList.subList(requiredList.indexOf(startRate),requiredList.indexOf(endRate)+1);

        if(requiredList.size()==1){
            return requiredList.get(0).getRate();
        }
        double result=0.0;
        long interval=(endTime.getTime()-startTime.getTime())/(1000*60*60*24);
        if(requiredList.size()==2){
            long subInterval=(requiredList.get(1).getDate().getTime()-startTime.getTime())/(1000*60*60*24);
            result=result+requiredList.get(0).getRate()*((double)subInterval/(double)interval);

            subInterval=(endTime.getTime()-requiredList.get(1).getDate().getTime())/(1000*60*60*24);
            result=result+requiredList.get(1).getRate()*((double)subInterval/(double)interval);

            return result;
        }

        Date start=startTime;
        for(int i=1;i<requiredList.size();i++){
            Date temp=requiredList.get(i).getDate();
            long subInterval=(temp.getTime()-start.getTime())/(1000*60*60*24);
            result=result+requiredList.get(i-1).getRate()*((double)subInterval/(double)interval);
            start=temp;
        }
        long subInterval=(endTime.getTime()-start.getTime())/(1000*60*60*24);
        result=result+requiredList.get(requiredList.size()-1).getRate()*((double)subInterval/(double)interval);
        return result;
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

