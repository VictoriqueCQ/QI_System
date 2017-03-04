package blservice;

import vo.MarketVO;

import java.util.Date;

/**
 * 用于整体市场搜索
 * Created by dell on 2017/3/4.
 */
public interface Overall_Search {
    MarketVO getMarketInfo(Date date);
}
