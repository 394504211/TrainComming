package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/1/25.
 */
public class HttpGet implements Runnable {

    String urlStr;
    boolean completed=false;
    String result="";
    public HttpGet(String url){
        this.urlStr=url;
    }

    public String getResult() {
        return result;
    }

    public boolean getCompelted() {
        return completed;
    }

    @Override
    public void run() {
        completed=false;
        HttpURLConnection conn;
        try {
            URL url =new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setRequestMethod("GET");
            int code=conn.getResponseCode();
            if(code==200){
                InputStream is = conn.getInputStream(); // 字节流转换成字符串
                BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
                String buffer;
                while((buffer=reader.readLine())!=null){
                    result=result+"\n"+buffer;
                }
                result=new String(result.getBytes(),"utf-8");
                completed=true;
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
