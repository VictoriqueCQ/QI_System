package quantour.data;

import quantour.dataservice.DataFactory;
import quantour.dataservice.Overall_Search_data;
import quantour.dataservice.Single_Search_data;

/**
 * Created by dell on 2017/3/4.
 */
public class DataFactory_CVS_Impl implements DataFactory{
    static DataFactory_CVS_Impl dataFactoryCvs=null;

    private Overall_Search_data overallSearchData;
    private Single_Search_data singleSearchData;

    private DataFactory_CVS_Impl(){
        overallSearchData=new Overall_Search_CVS_Impl();
        singleSearchData=new Single_Search_CVS_Impl();
    }

    public static DataFactory_CVS_Impl getInstance(){
        if(dataFactoryCvs==null){
            dataFactoryCvs=new DataFactory_CVS_Impl();
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
