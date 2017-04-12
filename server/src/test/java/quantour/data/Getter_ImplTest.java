package quantour.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import quantour.dataservice.Getter_data;

/**
 * Created by dell on 2017/3/27.
 */
public class Getter_ImplTest {
    private Getter_data getterData;

    @Before
    public void setUp() throws Exception {
        DataReader_CSV dataReader_csv = new DataReader_CSV();
        getterData=new Getter_Impl(dataReader_csv.readStockList());
    }

    @Test
    public void get() throws Exception {
        String[] strings={"GET"};
        System.out.print(getterData.getAll().getCodes().size());
        Assert.assertEquals(getterData.getAll().getCodes().size(),getterData.getAll().getCodes().size());
    }

}