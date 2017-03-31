package quantour.data;

import quantour.dataservice.*;

import java.io.IOException;
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
    private Getter_data getterData;
    private Strategy_Calculator_data strategyCalculatorData;

    private List<Stock> stockList;

    private DataFactory_CSV_Impl() throws ParseException, IOException {
        DataReader_CSV dataReader_CSV = new DataReader_CSV();
        stockList = dataReader_CSV.read();
        overallSearchData = new Overall_Search_data_Impl(stockList);
        singleSearchData = new Single_Search_data_Impl(stockList);
        userData=new User_data_Impl();
        getterData=new Getter_Impl(stockList);
        strategyCalculatorData=new Strategy_Calculator_Impl(stockList);
    }

    static DataFactory_CSV_Impl getInstance() throws ParseException, IOException {
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

    @Override
    public Getter_data getGetter() {
        return getterData;
    }

    @Override
    public Strategy_Calculator_data getStrategy() {
        return strategyCalculatorData;
    }

}
