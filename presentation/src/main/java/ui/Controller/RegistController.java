package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ui.AlertUtil;
import ui.Main;

import java.rmi.RemoteException;



/**
 * Created by xjwhhh on 2017/3/19.
 */
public class RegistController {
    private Main main;

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
    private Button registButton;


    public void setMain(Main main) {
        this.main = main;
        yesImage.setVisible(false);
        noImage.setVisible(false);
        noLabel.setVisible(false);
        registButton.setDisable(true);
        //输入用户名时的监听
//        usernameTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//            @Override
//            public void handle(KeyEvent event) {
//                try {
//                    if (usernameTextField.getText().isEmpty()) {
//                        yesImage.setVisible(false);
//                        noImage.setVisible(false);
//                        noLabel.setVisible(false);
//                        registButton.setDisable(true);
//                    }
//                    ClientVO vo = helper.getClientBLService().client_getclientvo(usernameTextField.getText());
//                    if (vo.getclientid()==0) {
//                        yesImage.setVisible(true);
//                        noImage.setVisible(false);
//                        noLabel.setVisible(false);
//                        registButton.setDisable(false);
//                    }
//                    else{
//                        yesImage.setVisible(false);
//                        noImage.setVisible(true);
//                        noLabel.setVisible(true);
//                        registButton.setDisable(true);
//                    }
//                    if (usernameTextField.getText().isEmpty()) {
//                        yesImage.setVisible(false);
//                        noImage.setVisible(false);
//                        noLabel.setVisible(false);
//                        registButton.setDisable(true);
//                    }
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @FXML
    private void exitRegist(){
        main.closeExtraStage();
    }

    @FXML
    private void register() throws RemoteException {
        if (usernameTextField.getText().isEmpty()) {
            AlertUtil.showWarningAlert("账号不可以为空！");
        }
        else if (passwordTextField1.getText().isEmpty()) {
            AlertUtil.showWarningAlert("密码不可以为空！");
        }
//        else if (passwordTextField1.getText().equals(passwordTextField2.getText())) {
//            ResultMessage result = helper.getClientBLService().client_register(usernameTextField.getText(),
//                    passwordTextField1.getText());
//            if (result == ResultMessage.Success) {
//                AlertUtil.showInformationAlert("注册成功！");
//                this.exitRegist();
//                main.gotoLogin("client");
//            } else {
//                AlertUtil.showErrorAlert("对不起，注册失败，可能是用户名冲突或者网络问题。");
//            }
//        }
        else {
            AlertUtil.showWarningAlert("对不起，您两次输入的密码不一致。");
        }
    }
}
