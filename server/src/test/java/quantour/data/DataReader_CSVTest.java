package quantour.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import quantour.data.datastructure.Index;
import quantour.data.datastructure.Rate;
import quantour.data.datastructure.StockNameNCode;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/3/5.
 */
public class DataReader_CSVTest {

    private DataReader_CSV dataReader_CSV;

    @Before
    public void setUp() throws Exception {
        dataReader_CSV = new DataReader_CSV();
    }

    @Test
    public void readStockListTest() throws Exception {
        List<Stock> list = dataReader_CSV.readStockList();
        System.out.print(list.size());
        /*while (itr.hasNext()){
            Stock stock=(Stock)itr.next();

        }*/
    }

    @Test
    public void readPlate() throws Exception {
        Map<String,List<StockNameNCode>> list=dataReader_CSV.readPlate();
        Assert.assertEquals(2,list.size());
    }

    @Test
    public void readIndexTest() throws Exception{
        Map<String,List<Index>> list=dataReader_CSV.readIndex();
        Assert.assertEquals(3,list.size());
    }

    @Test
    public void readRate() throws Exception {
        List<Rate> list=dataReader_CSV.readRate();
        Assert.assertEquals(28,list.size());
    }


}