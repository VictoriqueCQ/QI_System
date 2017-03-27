package quantour.dataservice;

/**
 * Created by dell on 2017/3/4.
 */
public interface DataFactory {
    Overall_Search_data getOverallSearch();
    Single_Search_data getSingleSearch();
    User_data getUser();
    Getter_data getGetter();
}
