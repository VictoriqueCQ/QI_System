package quantour.data;

/**
 * Created by dell on 2017/3/5.
 */
public class StockIdentifier {
    private String code;
    private String name;
    private String market;

    StockIdentifier( String code,String name, String market) {
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
