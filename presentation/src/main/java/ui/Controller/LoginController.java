package ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import quantour.vo.UserVO;
import ui.AlertUtil;
import ui.JsonUtil;
import ui.Main;
import ui.Net;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xjwhhh on 2017/3/13.
 */
public class LoginController {
    private Main main;
    private Net net;

    String path1="";


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

    /**
     * 登录
     */
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
                    File f=new File(path1+"documentation\\remembereduserinfo.txt");
                    try {
                        BufferedWriter brwriter = new BufferedWriter(new FileWriter(f,false));
                        brwriter.write(username);
                        brwriter.write("\t");
                        brwriter.write(password);
                        brwriter.close();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                }
            } else {
                this.exitLogin();
                AlertUtil.showErrorAlert("对不起，账户名或密码错误");
            }
        }
    }

    public void setMain(Main main,Net net) {
        this.main = main;
        this.net=net;
        //默认记住密码
        rememberPasswordRadioButton.setSelected(true);

        //获取文件路径
        String path=String.valueOf(Main.class.getResource(""));
        String[] pathlist=path.split("/");
        for(int i=1;i<pathlist.length-1;i++){
            path1+=(pathlist[i]+"\\");
        }

        //自动写入上次记住的用户名密码
        File f=new File(path1+"documentation\\remembereduserinfo.txt");
        try {
            BufferedReader brreader = new BufferedReader(new FileReader(f));
            String info=brreader.readLine();
            if(info!=null) {
                List<String> userInfo = Arrays.asList(info.split("\t"));
                usernameTextField.setText(userInfo.get(0));
                passwordTextField.setText(userInfo.get(1));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
