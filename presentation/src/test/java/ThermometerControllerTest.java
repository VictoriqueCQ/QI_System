package java;

import net.sf.ezmorph.test.ArrayAssertions;
import org.junit.Test;
import ui.Controller.ThermometerController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/3/17.
 */
public class ThermometerControllerTest {
    ThermometerController thermometerController = new ThermometerController();

    @Test
    public void test_ChangeDateStyle(){
        LocalDate localDate = LocalDate.of(2010,1,1);
        Date date = ThermometerController.changeDateStyle(localDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String str=format.format(date);
        ArrayAssertions.assertEquals("2010/1/1",str);


    }
}
