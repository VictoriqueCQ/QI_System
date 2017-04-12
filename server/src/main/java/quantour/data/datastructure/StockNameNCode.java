package quantour.data.datastructure;

/**
 * Created by dell on 2017/4/12.
 */
public class StockNameNCode {
    private int code;
    private String name;

    public StockNameNCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
