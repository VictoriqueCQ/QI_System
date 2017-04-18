package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import quantour.vo.UserVO;
import ui.AlertUtil;
import ui.JsonUtil;
import ui.Main;
import ui.Net;



/**
 * Created by xjwhhh on 2017/3/19.
 */
public class RegistController {
    private Main main;

    private Net net;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField1;

    @FXML
    private TextField passwordTextField2;

    @FXML
    private ImageView yesImage;

    @FXML
    private ImageView noImage;

    @FXML
    private Label noLabel;

    @FXML
    private Button registerButton;


    public void setMain(Main main,Net net) {
        this.main = main;
        this.net=net;
        yesImage.setVisible(false);
        noImage.setVisible(false);
        noLabel.setVisible(false);
    }

    /**
     * 退出注册
     */
    @FXML
    private void exitRegist(){
        main.closeExtraStage();
    }

    /**
     * 注册账户
     */
    @FXML
    private void register() {
        if (usernameTextField.getText().isEmpty()) {
            this.exitRegist();
            AlertUtil.showWarningAlert("账号不可以为空！");
        }
        else if (passwordTextField1.getText().isEmpty()) {
            this.exitRegist();
            AlertUtil.showWarningAlert("密码不可以为空！");
        }
        else if (passwordTextField1.getText().equals(passwordTextField2.getText())) {
            String input="USER\tSIGNUP\tNULL\t"+usernameTextField.getText()+"\t"+passwordTextField1.getText();
            net.actionPerformed(input);
            String json = net.run();
            JsonUtil jsonUtil = new JsonUtil();
            UserVO userVO1=new UserVO();
            UserVO userVO= (UserVO) jsonUtil.JSONToObj(json, userVO1.getClass());
            if(userVO!=null){
                this.exitRegist();
                AlertUtil.showConfirmingAlert("注册成功,您的id是"+userVO.getId());
                main.gotoClientOverview(true,userVO);
            }
            else{
                this.exitRegist();
                AlertUtil.showErrorAlert("对不起，注册失败");
            }
        }
        else {
            AlertUtil.showWarningAlert("对不起，您两次输入的密码不一致。");
        }
    }
}
