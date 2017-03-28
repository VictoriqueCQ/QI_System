package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import quantour.vo.UserVO;
import ui.AlertUtil;
import ui.JsonUtil;
import ui.Main;
import ui.Net;

/**
 * Created by xjwhhh on 2017/3/13.
 */
public class LoginController {
    private Main main;
    private Net net;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private RadioButton rememberPasswordRadioButton;

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
            this.exitLogin();
            AlertUtil.showWarningAlert("对不起，您未输入账户名");
        }
        else if((!username.isEmpty())&&password.isEmpty()){
            this.exitLogin();
            AlertUtil.showWarningAlert("对不起，您未输入密码");
        }
        else if(username.isEmpty()&&password.isEmpty()){
            this.exitLogin();
            AlertUtil.showWarningAlert("对不起，您未输入账户名和密码");
        }
        else {
            String input="USER\tLOGIN\t"+usernameTextField.getText()+"\t"+"NULL\t"+passwordTextField.getText();
            net.actionPerformed(input);
            String json = net.run();
            JsonUtil jsonUtil = new JsonUtil();
            UserVO userVO1=new UserVO();
            UserVO userVO= (UserVO) jsonUtil.JSONToObj(json, userVO1.getClass());
            if (userVO!=null) {
                this.exitLogin();
                AlertUtil.showConfirmingAlert("登录成功");
                main.gotoClientOverview(true,userVO);
                if(rememberPasswordRadioButton.isSelected()){
                    //TODO
                }
            } else {
                this.exitLogin();
                AlertUtil.showErrorAlert("对不起，账户名或密码错误");
            }
        }
    }



    public LoginController() {

    }

    public void setMain(Main main,Net net) {

        this.main = main;
        this.net=net;
    }
}
