package quantour.po;

import quantour.data.DataClass;

import java.util.List;

/**
 * Created by dell on 2017/3/27.
 */
public class NamePO extends DataClass {
    private List<String> names;
    private List<Integer> codes;

    public NamePO(List<String> names, List<Integer> codes) {
        this.names = names;
        this.codes = codes;
    }

    public List<String> getNames() {
        return names;
    }

    public List<Integer> getCodes() {
        return codes;
    }
}
