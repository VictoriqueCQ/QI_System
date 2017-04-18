package quantour.data.strategy;

import org.junit.Before;
import org.junit.Test;
import quantour.data.DataClass;
import quantour.data.DataFactory_CSV_Impl;
import quantour.dataservice.FormativeNHolding_data;

/**
 * Created by dell on 2017/4/18.
 */
public class FormativeNHolding_data_ImplTest {
    FormativeNHolding_data formativeNHoldingData;

    @Before
    public void setUp() throws Exception {
        DataFactory_CSV_Impl.getInstance();
        formativeNHoldingData=new FormativeNHolding_data_Impl();
    }

    @Test
    public void get() throws Exception {
        //可能会出现持有期或形成期太长的现象

        String[] quest={null,"FNH","M","4/11/14","4/29/14","F","T","3",null,"1","2","4","10","402"};
        DataClass formativeNHoldingPO=formativeNHoldingData.get(quest);

    }

}