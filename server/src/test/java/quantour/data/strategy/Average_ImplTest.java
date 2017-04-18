package quantour.data.strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import quantour.data.DataFactory_CSV_Impl;
import quantour.data.StockSet;
import quantour.dataservice.DataFactory;
import quantour.dataservice.Strategy_data;

import java.util.List;

/**
 * Created by chenyuyan on 18/4/17.
 */
public class Average_ImplTest {

    Strategy_data strategyData;
    DataFactory dataFactory;
    @Before
    public void setUp() throws Exception {
        strategyData=new Momentum_Impl();
        dataFactory= DataFactory_CSV_Impl.getInstance();
    }

    @Test
    public void getSets1() throws Exception {
        String[] quest={null,"STRATEGY","M","4/11/14","4/29/14","3","T","3",null,"1","2","4","10","402"};
        List<StockSet> stockSets=strategyData.getSets(quest);
        Assert.assertEquals(4,stockSets.size());
    }


}
