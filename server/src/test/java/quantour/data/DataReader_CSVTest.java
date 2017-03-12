package quantour.data;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

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
    public void read() throws Exception {
        List<Stock> list = dataReader_CSV.read();
        Iterator itr = list.iterator();
        System.out.print(list.size());
        /*while (itr.hasNext()){
            Stock stock=(Stock)itr.next();

        }*/
    }

}