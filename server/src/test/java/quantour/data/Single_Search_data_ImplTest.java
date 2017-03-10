package quantour.data;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/3/10.
 */
public class Single_Search_data_ImplTest {
    private Single_Search_data_Impl singleSearchData;
    @Before
    public void setUp() throws Exception {
        List<Stock> stockList=new ArrayList<>();
        DataReader_CSV dataReader_csv=new DataReader_CSV();
        singleSearchData=new Single_Search_data_Impl(dataReader_csv.read());
    }

    @Test
    public void getStockListByID() throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("mm/dd/yy");
        Date date1=sdf.parse("4/22/14");
        Date date2=sdf.parse("4/29/14");
        singleSearchData.getStockListByID(1,date1,date2);
    }

    @Test
    public void getStockListByName() throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("mm/dd/yy");
        Date date1=sdf.parse("4/22/14");
        Date date2=sdf.parse("4/29/14");
        singleSearchData.getStockListByName("深发展Ａ",date1,date2);
    }

}