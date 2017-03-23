package quantour.dataservice;

import quantour.po.UserPO;

/**
 * Created by dell on 2017/3/23.
 */
public interface User_data extends Logic{
    UserPO signUp(UserPO userInfo);
    UserPO login(UserPO userInfo);
    UserPO modify(UserPO userInfo);
}
