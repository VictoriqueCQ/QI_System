package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ui.AlertUtil;
import ui.Main;

/**
 * Created by xjwhhh on 2017/3/23.
 */
public class ChangePasswordController {
    private Main main;

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
            AlertUtil.showWarningAlert("对不起，您未输入用户名");
        }
        else if(oldPassword.isEmpty()){
            AlertUtil.showWarningAlert("对不起，您未输入原密码");
        }
        else if(newPassword.isEmpty()){
            AlertUtil.showWarningAlert("对不起，您未输入新密码");
        }
        else{
            if(true){
                AlertUtil.showConfirmingAlert("更改密码成功");
                this.exitChangePassword();
//                main.gotoClientOverview(true);
            }
            else{
                AlertUtil.showErrorAlert("对不起,更新密码失败");
            }
        }
    }



    public ChangePasswordController() {

    }

    public void setMain(Main main) {
        this.main = main;
    }
}
