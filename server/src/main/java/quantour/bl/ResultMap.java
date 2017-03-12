package quantour.bl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/3/12.
 */
class ResultMap {
    private static Map<Integer,String> map=new HashMap<>();

    static void write(int id, String result){
        map.put(id,result);
    }

    static String get(int id){
        return map.get(id);
    }

    static void delete(int id){
        map.remove(id);
    }

}
