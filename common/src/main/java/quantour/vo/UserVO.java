package quantour.vo;

/**
 * Created by dell on 2017/3/23.
 */
public class UserVO {
    private String id;
    private String name;
    private String password;

    public UserVO(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public UserVO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public UserVO(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }
}
