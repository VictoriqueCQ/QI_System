package quantour.data;

import quantour.dataservice.DataFactory;
import quantour.dataservice.Overall_Search_data;
import quantour.dataservice.Single_Search_data;

import java.util.List;

/**
 * Created by dell on 2017/3/4.
 */
public class DataFactory_CSV_Impl implements DataFactory{
    private static DataFactory_CSV_Impl dataFactoryCvs=null;

    private Overall_Search_data overallSearchData;
    private Single_Search_data singleSearchData;

    private List<Stock> stockList;

    private DataFactory_CSV_Impl(){
        DataReader_CSV dataReader_CSV =new DataReader_CSV();
        //stockPOList= dataReader_CSV.read();
        stockList=dataReader_CSV.read();
        overallSearchData=new Overall_Search_data_Impl(stockList);
        singleSearchData=new Single_Search_data_Impl(stockList);
    }

    public static DataFactory_CSV_Impl getInstance(){
        if(dataFactoryCvs==null){
            dataFactoryCvs=new DataFactory_CSV_Impl();
        }
        return dataFactoryCvs;
    }


    @Override
    public Overall_Search_data getOverallSearch() {
        return overallSearchData;
    }

    @Override
    public Single_Search_data getSingleSearch() {
        return singleSearchData;
    }
}
