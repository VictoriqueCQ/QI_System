package quantour.vo;

import java.util.List;

/**
 * Created by xjwhhh on 2017/4/3.
 */
public class NameVO {
    private List<String> names;
    private List<Integer> codes;

    public NameVO(List<String> names, List<Integer> codes) {
        this.names = names;
        this.codes = codes;
    }

    public NameVO(){

    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names){
        this.names=names;
    }

    public List<Integer> getCodes() {
        return codes;
    }

    public void setCodes(List<Integer> codes){
        this.codes=codes;
    }
}
