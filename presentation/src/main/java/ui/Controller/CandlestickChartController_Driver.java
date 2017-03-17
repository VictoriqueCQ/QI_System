package ui.Controller;

import quantour.vo.StockVO;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by xjwhhh on 2017/3/17.
 */
public class CandlestickChartController_Driver {
    public void changeDateStyle_drive(CandlestickChartController candlestickChartController) {
        LocalDate localDate = LocalDate.now();
        Date date = candlestickChartController.changeDateStyle(localDate);
        System.out.print(date);
    }

    public void getStockVOByCondition(CandlestickChartController candlestickChartController) {
        StockVO stockVO = candlestickChartController.getStockVOByCondition();
        System.out.print(stockVO);
    }
}
