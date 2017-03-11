package quantour.data;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dell on 2017/3/5.
 */
public class Overall_Search_data_ImplTest {
    private Overall_Search_data_Impl overallSearchData;

    @Before
    public void setUp() throws Exception {
        DataReader_CSV dataReader_csv=new DataReader_CSV();
        overallSearchData=new Overall_Search_data_Impl(dataReader_csv.read());
    }

    @Test
    public void getMarketInfo() throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("mm/dd/yy");
        Date date=sdf.parse("4/29/14");
        overallSearchData.getMarketInfo(date);
    }

}