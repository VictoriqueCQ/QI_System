package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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



    public LoginController() {

    }

    public void setMain(Main main) {
        this.main = main;
    }
}
