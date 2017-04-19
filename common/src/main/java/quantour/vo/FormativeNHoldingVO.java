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

    public void setOverProfit(List<Double> overProfit){
        this.overProfit=overProfit;
    }

    public List<Double> getWinChance() {
        return winChance;
    }

    public void setWinChance(List<Double> winChance){
        this.winChance=winChance;
    }
}
