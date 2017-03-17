package quantour.Stub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenyuyan on 17/3/17.
 */
public class StockVO_Stub {
    private String name = "njuStck";
    private int code = 0;
    private Date start;
    private Date over;
    private double[] open = {1,2,3};
    private double[] high = {1,2,3};
    private double[] low = {};
    private double[] close;
    private int[] volume;//交易数
    private double[] adjClose;//复权后
    private List<Date> dates;
    private double[] average5;
    private double[] average10;
    private double[] average20;
    private double[] average30;
    private double[] average60;
    private ArrayList<Double> profit;//每日收益率
    private double variance;//相对方差

}
