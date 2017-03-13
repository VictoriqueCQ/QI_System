package ui;

import net.sf.json.JSONObject;

import java.io.IOException;

public class JsonUtil {

	public  <T> JSONObject objectToJson(T obj) throws IOException {
	JSONObject jsonStu = JSONObject.fromObject(obj);
	return jsonStu;

	}
	public  <T> Object JSONToObj(String jsonStr, Class<T> obj) {
		JSONObject jsonResult =JSONObject.fromObject(jsonStr);
		T t=(T)JSONObject.toBean(jsonResult,obj);
		return t;
	}

}
