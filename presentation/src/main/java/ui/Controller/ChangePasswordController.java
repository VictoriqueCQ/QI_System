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
    private TextField IDTextField;

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
        String id=IDTextField.getText();
        String oldPassword=passwordTextField1.getText();
        String newPassword=passwordTextField2.getText();
        if(id.isEmpty()){
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
            String input="USER\tLOGIN\t"+id+"\t"+"NULL\t"+oldPassword;
            net.actionPerformed(input);
            String json = net.run();
            JsonUtil jsonUtil = new JsonUtil();
            UserVO userVO0=new UserVO();
            UserVO userVO= (UserVO) jsonUtil.JSONToObj(json, userVO0.getClass());
            System.out.print(userVO.getName());
            String input1="USER\tMODIFY\t"+id+"\t"+userVO.getName()+"\t"+newPassword;
            net.actionPerformed(input1);
            String json1 = net.run();
            UserVO userVO1= (UserVO) jsonUtil.JSONToObj(json1, userVO0.getClass());
            if(userVO1!=null){
                AlertUtil.showConfirmingAlert("更改密码成功");
                this.exitChangePassword();
                main.gotoClientOverview(true,userVO1);
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
