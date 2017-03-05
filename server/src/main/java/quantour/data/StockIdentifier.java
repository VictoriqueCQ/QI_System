package quantour.data;

/**
 * Created by dell on 2017/3/5.
 */
public class StockIdentifier {
    private String name;
    private String code;
    private String market;

    StockIdentifier(String name, String code, String market) {
        this.name = name;
        this.code = code;
        this.market = market;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getMarket() {
        return market;
    }
}
