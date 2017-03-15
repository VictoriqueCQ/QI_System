package quantour.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by dell on 2017/3/5.
 */
public class DataReader_CSV {
    private String path;

    DataReader_CSV() {
        path="D:\\QI_System\\server\\stock_data.csv";
        //path="D:\\data.txt";
    }

    public DataReader_CSV(String path) {
        this.path = path;
    }

    List<Stock> read() {
        List<Stock> stockList = new ArrayList<>();
        File f = new File(path);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
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


}
