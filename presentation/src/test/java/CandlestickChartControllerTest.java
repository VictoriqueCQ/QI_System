package java;

import org.junit.Test;
import ui.Controller.CandlestickChartController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by xjwhhh on 2017/3/17.
 */
public class CandlestickChartControllerTest {
    CandlestickChartController candlestickChartController = new CandlestickChartController();

    @Test
    public void test_changeDateStyle() {
        LocalDate localDate = LocalDate.of(2010, 1, 1);
        Date date = candlestickChartController.changeDateStyle(localDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String str = format.format(date);
        assertEquals("2010/1/1", str);
    }

}
