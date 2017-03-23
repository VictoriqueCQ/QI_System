package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ui.AlertUtil;
import ui.Main;

/**
 * Created by xjwhhh on 2017/3/13.
 */
public class LoginController {
    private Main main;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private void exitLogin(){
        main.closeExtraStage();
    }

    @FXML
    private void gotoRegist(){
        main.gotoRegist();
    }

    @FXML
    private void gotoChangePassword(){
        main.gotoChangePassword();
    }

    @FXML
    private void login(){
        String username=usernameTextField.getText();
        String password=passwordTextField.getText();
        if(username.isEmpty()&&(!password.isEmpty())){
            AlertUtil.showWarningAlert("对不起，您未输入账户名");
        }
        else if((!username.isEmpty())&&password.isEmpty()){
            AlertUtil.showWarningAlert("对不起，您未输入密码");
        }
        else if(username.isEmpty()&&password.isEmpty()){
            AlertUtil.showWarningAlert("对不起，您未输入账户名和密码");
        }
        else {
            //TODO
            if (true) {
                this.exitLogin();
                main.gotoClientOverview(true);
            } else {
                AlertUtil.showErrorAlert("对不起，账户名或密码错误");
            }
        }
    }



    public LoginController() {

    }

    public void setMain(Main main) {
        this.main = main;
    }
}
