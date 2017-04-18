package quantour.data;

import org.junit.Before;
import org.junit.Test;
import quantour.dataservice.DataFactory;
import quantour.dataservice.Strategy_Calculator_data;

/**
 * Created by dell on 2017/4/18.
 */
public class Strategy_Calculator_ImplTest {
    Strategy_Calculator_data strategyCalculatorData;

    @Before
    public void setUp() throws Exception {
        DataFactory dataFactory=DataFactory_CSV_Impl.getInstance();
        strategyCalculatorData=dataFactory.getStrategy();
    }

    @Test
    public void get() throws Exception {
        String[] quest={null,"STRATEGY","M","4/11/14","4/29/14","3","T","3",null,"1","2","4","10","402"};
        
    }

}