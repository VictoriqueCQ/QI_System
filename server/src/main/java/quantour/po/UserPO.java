package quantour.po;

import quantour.data.DataClass;

/**
 * Created by dell on 2017/3/23.
 */
public class UserPO extends DataClass{
    private String id;
    private String name;
    private String password;

    public UserPO(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public UserPO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
