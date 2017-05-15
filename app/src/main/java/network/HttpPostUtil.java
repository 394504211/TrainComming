package network;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by xlf on 2017/5/1.
 */
public class HttpPostUtil {
    public static String doPostRequest(String urlStr, Map<String,String> paraMap){
        String result="";
        PrintWriter out = null;
        BufferedReader reader;
        InputStream is = null;
        try {
            JSONObject json =new JSONObject();
            for(Map.Entry<String,String>entry:paraMap.entrySet()){
                json.put(entry.getKey(),new String(entry.getValue().getBytes(),"utf-8"));
            }
            URL url= new URL(urlStr);
            HttpURLConnection conn;
            conn=(HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("POST");
            conn.connect();
            out=new PrintWriter(conn.getOutputStream());
            out.println(json);
            out.flush();

            //读取服务器返回信息
            int code=conn.getResponseCode();
            if(code==200){
                is = conn.getInputStream(); // 字节流转换成字符串
                reader=new BufferedReader(new InputStreamReader(is,"gb2312"));
                String buffer;
                while((buffer=reader.readLine())!=null){
                    result=result+"\n"+buffer;
                }
                result=new String(result.getBytes(),"utf-8");
                return result;
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out!=null){
                    out.close();
                }
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
