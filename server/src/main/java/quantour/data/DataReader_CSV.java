package quantour.data;

import quantour.data.datastructure.Index;
import quantour.data.datastructure.StockNameNCode;
import quantour.dataservice.DataReader_data;

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
public class DataReader_CSV implements DataReader_data{
    private String stockPath;
    private String platePath;
    private String indexPath;

    DataReader_CSV() throws IOException {
        stockPath = "stock_data\\stock_data.csv";
        platePath="stock_data\\plate";//directory
        indexPath="stock_data\\index";//directory
    }

    public DataReader_CSV(String path) {
        this.stockPath = path;
    }

    @Override
    public List<Stock> readStockList() {
        List<Stock> stockList = new ArrayList<>();
        File f = new File(stockPath);
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yy");

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine();//标题行
            String row = br.readLine();
            while (row != null) {
                List<String> stockInfo = Arrays.asList(row.split("\t"));
                int serial = Integer.parseInt(stockInfo.get(0));
                Date date = sdf.parse(stockInfo.get(1));
                double open = Double.parseDouble(stockInfo.get(2));
                double high = Double.parseDouble(stockInfo.get(3));
                double low = Double.parseDouble(stockInfo.get(4));
                double close = Double.parseDouble(stockInfo.get(5));
                int volume = Integer.parseInt(stockInfo.get(6));
                double adjClose = Double.parseDouble(stockInfo.get(7));
                //处理stockIdentifier
                int code = Integer.parseInt(stockInfo.get(8));
                String name = stockInfo.get(9);
                String market = stockInfo.get(10);

                Stock stock = new Stock(serial, date, open, high, low, close, volume, adjClose, code, name, market);
                stockList.add(stock);

                row = br.readLine();
            }

            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return stockList;
    }

    @Override
    public Map<String, List<StockNameNCode>> readPlate() {

        return null;
    }

    @Override
    public Map<String, List<Index>> readIndex() {
        File f=new File(indexPath);
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");

        try {
            File[] files=f.listFiles();
            Map<String,List<Index>> indexList=new HashMap<>();
            for (File temp : files){
                BufferedReader br=new BufferedReader(new FileReader(temp));
                String s=temp.getName();
                String name=s.substring(0,s.lastIndexOf("."));
                System.out.println(name);
                List<Index> indices=new ArrayList<>();
                String line;
                while((line=br.readLine())!=null){
                    List<String> strings= Arrays.asList(line.split("\t"));

                    Date date=sdf.parse(strings.get(0));
                    double open=Double.parseDouble(strings.get(1));
                    double highest=Double.parseDouble(strings.get(2));
                    double lowest=Double.parseDouble(strings.get(3));
                    double close=Double.parseDouble(strings.get(4));
                    double idprice=Double.parseDouble(strings.get(5));
                    double idrate=Double.parseDouble(strings.get(6));
                    double volume=Double.parseDouble(strings.get(7));
                    double turnover=Double.parseDouble(strings.get(8));

                    Index index=new Index(date,open,highest,lowest,close,idprice,idrate,volume,turnover);
                    indices.add(index);
                }
                indexList.put(name,indices);
            }

            return indexList;
        }catch (IOException ioe){
            ioe.printStackTrace();
            return null;
        }catch (ParseException pe){
            pe.printStackTrace();
            return null;
        }
    }
}
