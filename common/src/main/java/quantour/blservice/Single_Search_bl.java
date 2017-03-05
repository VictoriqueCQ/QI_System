package quantour.blservice;

import quantour.vo.StockSearchConditionVO;
import quantour.vo.StockVO;

/**
 * 用于搜索单个股票
 * Created by dell on 2017/3/4.
 */
public interface Single_Search_bl {
    StockVO findStock(StockSearchConditionVO stockSearchConditionVO);
}
