package quantour.data;
import quantour.dataservice.Getter_data;
import quantour.po.NamePO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dell on 2017/3/27.
 */
public class Getter_Impl implements Getter_data{
    private List<Stock> list;

    Getter_Impl(List<Stock> list) {
        this.list=list;
    }

    @Override
    public DataClass get(String[] quest) {
        return getAll();
    }

    @Override
    public NamePO getAll(){
        List<String> names = list.parallelStream().map(Stock::getName).distinct().collect(Collectors.toList());
        List<Integer> codes = list.parallelStream().map(Stock::getCode).distinct().collect(Collectors.toList());
        return new NamePO(names, codes);
    }
}
