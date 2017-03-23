package quantour.data;

import org.junit.Before;
import org.junit.Test;
import quantour.dataservice.DataFactory;

/**
 * Created by dell on 2017/3/23.
 */
public class ResultSenderTest {
    private ResultSender resultSender;

    @Before
    public void setUp() throws Exception {
        String quest="0"+"\t"+"STOCK"+"\t"+"1"+"\t"+"NULL"+"\t"+"04/22/14"+"\t"+"04/29/14";
        DataFactory dataFactory=DataFactory_CSV_Impl.getInstance();
        resultSender=new ResultSender(dataFactory,quest);
    }

    @Test
    public void run() throws Exception {
        resultSender.run();
    }

}