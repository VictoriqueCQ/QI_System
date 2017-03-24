package quantour.data;

import quantour.dataservice.DataFactory;
import quantour.dataservice.Overall_Search_data;
import quantour.dataservice.Single_Search_data;
import quantour.dataservice.User_data;

import java.text.ParseException;
import java.util.List;

/**
 * Created by dell on 2017/3/4.
 */
public class DataFactory_CSV_Impl implements DataFactory {
    private static DataFactory_CSV_Impl dataFactoryCvs = null;

    private Overall_Search_data overallSearchData;
    private Single_Search_data singleSearchData;
    private User_data userData;

    private List<Stock> stockList;

    private DataFactory_CSV_Impl() throws ParseException {
        DataReader_CSV dataReader_CSV = new DataReader_CSV();
        stockList = dataReader_CSV.read();
        overallSearchData = new Overall_Search_data_Impl(stockList);
        singleSearchData = new Single_Search_data_Impl(stockList);
        userData=new User_data_Impl();
    }

    static DataFactory_CSV_Impl getInstance() throws ParseException {
        if (dataFactoryCvs == null) {
            dataFactoryCvs = new DataFactory_CSV_Impl();
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

    @Override
    public User_data getUser() {
        return userData;
    }
}
