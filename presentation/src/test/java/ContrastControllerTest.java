package java;

import org.junit.Test;
import ui.Controller.ContrastController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;

/**
 * Created by chenyuyan on 17/3/17.
 */
public class ContrastControllerTest {

    ContrastController contrastController = new ContrastController();

    @Test
    public void test_ChangeDateStyle() {
        LocalDate localDate = LocalDate.of(2010, 1, 1);
        Date date = contrastController.changeDateStyle(localDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String str = format.format(date);
        assertEquals("2010/1/1", str);
    }

}
