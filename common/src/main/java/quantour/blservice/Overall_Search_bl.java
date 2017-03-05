package quantour.blservice;


import quantour.vo.MarketVO;

import java.util.Date;

/**
 * 用于整体市场搜索
 * Created by dell on 2017/3/4.
 */
public interface Overall_Search_bl {
    MarketVO getMarketInfo(Date date);
}
