package quantour.data;

import quantour.po.StockPO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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

    Map<StockIdentifier,List> read(){
        List<StockPO> stockPOList=new ArrayList<>();
        File f=new File(path);

        Map<StockIdentifier,List> stockMap=new HashMap<>();
        SimpleDateFormat sdf=new SimpleDateFormat("mm/dd/yy");
        try{
            BufferedReader br=new BufferedReader(new FileReader(f));
            br.readLine();//标题行
            String row=br.readLine();
            while(row!=null){
                List<String> stockInfo=Arrays.asList(row.split("\t"));
                int serial=Integer.parseInt(stockInfo.get(0));
                List<StockDealInfo> stockDealInfos=new ArrayList<>();

                //处理stockIdentifier
                String code=stockInfo.get(8);
                String name=stockInfo.get(9);
                String market=stockInfo.get(10);
                StockIdentifier stockIdentifier=new StockIdentifier(code,name,market);

                row=br.readLine();
                if(row!=null){
                    stockInfo=Arrays.asList(row.split("\t"));
                    serial=Integer.parseInt(stockInfo.get(0));
                }
                while(row!=null&&serial!=0) {
                    //处理stockDealInfo
                    Date date = sdf.parse(stockInfo.get(1));
                    double open = Double.parseDouble(stockInfo.get(2));
                    double high = Double.parseDouble(stockInfo.get(3));
                    double low = Double.parseDouble(stockInfo.get(4));
                    double close = Double.parseDouble(stockInfo.get(5));
                    int volume = Integer.parseInt(stockInfo.get(6));
                    double adjClose = Double.parseDouble(stockInfo.get(7));
                    StockDealInfo stockDealInfo = new StockDealInfo(serial, date, open, high, low, close, volume, adjClose);
                    stockDealInfos.add(stockDealInfo);

                    row=br.readLine();
                    if(row!=null){
                        stockInfo=Arrays.asList(row.split("\t"));
                        serial=Integer.parseInt(stockInfo.get(0));
                    }
                }

                stockMap.put(stockIdentifier,stockDealInfos);
            }

            br.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (ParseException pe) {
            pe.printStackTrace();
        }

        return stockMap;
    }


}
