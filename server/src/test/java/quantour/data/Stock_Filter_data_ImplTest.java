package quantour.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import quantour.dataservice.DataFactory;
import quantour.dataservice.Stock_Filter_data;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/4/15.
 */
public class Stock_Filter_data_ImplTest {
    Stock_Filter_data stockFilterData;

    @Before
    public void setUp() throws Exception {
        DataFactory dataFactory=DataFactory_CSV_Impl.getInstance();
        stockFilterData=dataFactory.getStockFilterData();
    }

    @Test
    public void filterStaStock() throws Exception {
        String[] quest={null,null,null,null,null,null,null,null,null,"沪深300"};
        Map<Integer,List<Stock>> listMap=stockFilterData.filterStaStock(quest);
        Assert.assertEquals(4,listMap.size());
    }

}