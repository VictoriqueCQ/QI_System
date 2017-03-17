package java;

import net.sf.json.JSONObject;
import org.junit.Test;
import quantour.vo.StockVO;
import ui.JsonUtil;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by xjwhhh on 2017/3/17.
 */
public class JsonUtilTest {
    JsonUtil jsonUtil = new JsonUtil();

    @Test
    public void test_objectToJson() {
        StockVO stockVO = new StockVO();
        stockVO.setName("深发展A");
        stockVO.setCode(1);
        stockVO.setVariance(100000);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = jsonUtil.objectToJson(stockVO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("{\"adjClose\":[],\"average10\":[],\"average20\":[],\"average30\":[],\"average5\":[],\"average60\":[],\"close\":[],\"code\":1,\"dates\":[],\"high\":[],\"low\":[],\"name\":\"深发展A\",\"open\":[],\"over\":null,\"profit\":[],\"start\":null,\"variance\":100000,\"volume\":[]}", jsonObject.toString());
    }

    @Test
    public void test_JSONToObj() {
        StockVO stockVO = new StockVO();
        stockVO.setName("深发展A");
        stockVO.setCode(1);
        stockVO.setVariance(100000);
        StockVO stockVO1 = new StockVO();
        try {
            JSONObject jsonObject = jsonUtil.objectToJson(stockVO);
            stockVO1 = (StockVO) jsonUtil.JSONToObj(jsonObject.toString(), stockVO.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("深发展A", stockVO1.getName());
    }

}
