package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import quantour.vo.UserVO;
import ui.AlertUtil;
import ui.JsonUtil;
import ui.Main;
import ui.Net;

/**
 * Created by xjwhhh on 2017/3/23.
 */
public class ChangePasswordController {
    private Main main;
    private Net net;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField1;

    @FXML
    private TextField passwordTextField2;

    @FXML
    private void exitChangePassword(){
        main.closeExtraStage();
    }

    @FXML
    private void changePassword(){
        String username=usernameTextField.getText();
        String oldPassword=passwordTextField1.getText();
        String newPassword=passwordTextField2.getText();
        if(username.isEmpty()){
            this.exitChangePassword();
            AlertUtil.showWarningAlert("对不起，您未输入用户名");
        }
        else if(oldPassword.isEmpty()){
            this.exitChangePassword();
            AlertUtil.showWarningAlert("对不起，您未输入原密码");
        }
        else if(newPassword.isEmpty()){
            this.exitChangePassword();
            AlertUtil.showWarningAlert("对不起，您未输入新密码");
        }
        else{
            String input="USER\tMODIFY\t"+usernameTextField.getText()+"\t"+"NULL\t"+newPassword;
            net.actionPerformed(input);
            String json = net.run();
            JsonUtil jsonUtil = new JsonUtil();
            UserVO userVO1=new UserVO();
            UserVO userVO= (UserVO) jsonUtil.JSONToObj(json, userVO1.getClass());
            if(userVO!=null){
                AlertUtil.showConfirmingAlert("更改密码成功");
                this.exitChangePassword();
                main.gotoClientOverview(true,userVO);
            }
            else{
                this.exitChangePassword();
                AlertUtil.showErrorAlert("对不起,更新密码失败");
            }
        }
    }



    public ChangePasswordController() {

    }

    public void setMain(Main main,Net net) {

        this.main = main;
        this.net=net;
    }
}
