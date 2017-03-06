package quantour.data;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dell on 2017/3/5.
 */
public class DataReader_CSVTest {
    private DataReader_CSV dataReader_CSV;
    @Before
    public void setUp() throws Exception {
        dataReader_CSV =new DataReader_CSV();
    }

    @Test
    public void read() throws Exception {
        Map<StockIdentifier,List> map=dataReader_CSV.read();
        Set set=map.keySet();
        Iterator itr=set.iterator();
        while (itr.hasNext()){
            StockIdentifier stockIdentifier=(StockIdentifier)itr.next();
            System.out.println(stockIdentifier.getName());
        }
    }

}