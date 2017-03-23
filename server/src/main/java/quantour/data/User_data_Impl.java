package quantour.data;

import quantour.dataservice.User_data;
import quantour.po.UserPO;

import java.io.*;

/**
 * Created by dell on 2017/3/23.
 */

/*
命令类型：
编号 USER SIGNUP/LOGIN/MODIFY ID NAME PASSWORD
 */
public class User_data_Impl implements User_data {
    private String path = "userinfo";
    private File file;

    User_data_Impl() {
        file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public DataClass get(String[] quest) {
        UserPO userPO = new UserPO(quest[3], quest[4], quest[5]);
        switch (quest[2]) {
            case "SIGNUP":
                return signUp(userPO);
            case "LOGIN":
                return login(userPO);
            default:
                return modify(userPO);
        }
    }

    @Override
    public UserPO signUp(UserPO userInfo) {
        int length = file.listFiles().length;
        String id = String.format("%08d", length + 1);
        File newFile = new File(path + "/" + id + ".txt");
        try {
            newFile.createNewFile();
            FileWriter fileWriter = new FileWriter(newFile);
            fileWriter.write(userInfo.getName() + "\t" + userInfo.getPassword());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
        //尚未新建文件
        return new UserPO(id, userInfo.getName(), userInfo.getPassword());
    }

    @Override
    public UserPO login(UserPO userInfo) {
        File[] files = file.listFiles();
        BufferedReader br;
        try {
            String id=userInfo.getId() + ".txt";
            for (File f : files) {
                if (f.getName().equals(id)) {
                    br=new BufferedReader(new FileReader(f));
                    String[] s=br.readLine().split("\t");
                    if(s[1].equals(userInfo.getPassword())){
                        br.close();
                        return userInfo;
                    }
                    else{
                        br.close();
                        return null;
                    }
                }
            }
            return null;
        }catch (IOException ioe){
            ioe.printStackTrace();
            return null;
        }
    }

    @Override
    public UserPO modify(UserPO userInfo) {
        File[] files = file.listFiles();
        try {
            for (File f : files) {
                if (f.getName().equals(userInfo.getId() + ".txt")) {
                    FileWriter fileWriter=new FileWriter(f);
                    fileWriter.write(userInfo.getName() + "\t" + userInfo.getPassword());
                    fileWriter.flush();
                    fileWriter.close();
                    return userInfo;
                }
            }
            return null;
        }catch (IOException ioe){
            ioe.printStackTrace();
            return null;
        }
    }

}
