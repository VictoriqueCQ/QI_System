package blservice;

import vo.StockSearchConditionVO;
import vo.StockVO;

/**
 * 用于搜索单个股票
 * Created by dell on 2017/3/4.
 */
public interface Single_Search {
    StockVO findStock(StockSearchConditionVO stockSearchConditionVO);
}
