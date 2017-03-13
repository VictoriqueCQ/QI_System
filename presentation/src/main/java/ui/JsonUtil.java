package ui;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

	public  <T> JSONObject objectToJson(T obj) throws IOException {
	JSONObject jsonStu = JSONObject.fromObject(obj);
	return jsonStu;

	}
	public  <T> Object JSONToObj(String jsonStr, Class<T> obj) {
		jsonStr=jsonStr.substring(1,jsonStr.length()-2);
//		System.out.print(jsonStr);
		JSONObject jsonResult =JSONObject.fromObject(jsonStr);
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("dates", Date.class);
		T t=(T)JSONObject.toBean(jsonResult,obj,classMap);
		return t;
	}

}
