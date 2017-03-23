package quantour.dataservice;

import quantour.po.MarketPO;

import java.util.Date;

/**
 * Created by dell on 2017/3/4.
 */
public interface Overall_Search_data extends Logic{
    MarketPO getMarketInfo(Date date);
}
