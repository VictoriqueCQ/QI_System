package quantour.data;

import quantour.po.StockPO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dell on 2017/3/5.
 */
public class DataReader_CSV {
    private String path;

    DataReader_CSV(){
        path="D:\\QI_System\\server\\stock_data.csv";
    }

    public DataReader_CSV(String path){
        this.path=path;
    }

    List<StockPO> read(){
        List<StockPO> stockPOList=new ArrayList<>();
        File f=new File(path);

        try{
            BufferedReader br=new BufferedReader(new FileReader(f));
            br.readLine();//标题行
            String row;
            while((row=br.readLine().trim())!=null){
                String[] stockInfo=row.split("\t");

            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        return stockPOList;
    }

}
