package quantour.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import quantour.dataservice.User_data;
import quantour.po.UserPO;

/**
 * Created by dell on 2017/3/23.
 */
public class User_data_ImplTest {
    private User_data userData;
    private UserPO userPO;
    @Before
    public void setUp() throws Exception {
        userData=new User_data_Impl();

    }

    @Test
    public void get() throws Exception {
        Assert.assertNotEquals(userData.get(new String[]{"0", "USER", "SIGNUP", null,"lalala","123"}),null);
    }

    @Test
    public void signUp() throws Exception {
        userPO=new UserPO(null,"emperor","12345");
        Assert.assertNotEquals(userData.signUp(userPO),null);
    }

    @Test
    public void login() throws Exception {
        userPO=new UserPO("00000001",null,"12345");
        Assert.assertNotEquals(userData.login(userPO),null);
    }

    @Test
    public void modify() throws Exception {
        userPO=new UserPO("00000001","emperor","1234");
        Assert.assertNotEquals(userData.modify(userPO),null);
    }

}