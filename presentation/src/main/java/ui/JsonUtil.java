package ui;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class JsonUtil {
    /**
     * java类转json类
     *
     * @param obj
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> JSONObject objectToJson(T obj) throws IOException {
        JSONObject jsonStu = JSONObject.fromObject(obj);
        return jsonStu;
    }

    /**
     * json字符串转java类
     *
     * @param jsonStr
     * @param obj
     * @param <T>
     * @return
     */
    public <T> Object JSONToObj(String jsonStr, Class<T> obj) {
        jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
        JSONObject jsonResult = JSONObject.fromObject(jsonStr);
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("dates", Date.class);
        classMap.put("names",String.class);
        classMap.put("codes",Integer.class);
        T t = (T) JSONObject.toBean(jsonResult, obj, classMap);
        return t;
    }

}
