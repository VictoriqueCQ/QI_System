package quantour.data;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by dell on 2017/3/10.
 */
public class Single_Search_data_ImplTest {
    private Single_Search_data_Impl singleSearchData;

    @Before
    public void setUp() throws Exception {
        DataReader_CSV dataReader_csv = new DataReader_CSV();
        singleSearchData = new Single_Search_data_Impl(dataReader_csv.readStockList());
    }

    @Test
    public void getStockListByID() throws Exception {
        String[] quest = {"0", "STOCK", "1", "NULL", "4/22/14", "4/29/14"};
        singleSearchData.getStockList(quest);
        //singleSearchData.getStockListByID(1,date1,date2);
    }

    @Test
    public void getStockListByName() throws Exception {
        //singleSearchData.getStockListByName("深发展Ａ",date1,date2);
        String[] quest = {"0", "STOCK", "NULL", "深发展Ａ", "4/22/14", "4/29/14"};
        singleSearchData.getStockList(quest);
    }

}