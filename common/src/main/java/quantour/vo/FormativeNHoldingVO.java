package quantour.vo;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */
public class FormativeNHoldingVO {
    private List<Double> overProfit;
    private List<Double> winChance;

    public FormativeNHoldingVO(List<Double> overProfit, List<Double> winChance) {
        this.overProfit = overProfit;
        this.winChance = winChance;
    }
    public FormativeNHoldingVO(){

    }

    public List<Double> getOverProfit() {
        return overProfit;
    }

    public List<Double> getWinChance() {
        return winChance;
    }
}
