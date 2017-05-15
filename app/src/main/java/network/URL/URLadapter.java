package network.URL;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/1/24.
 */
public class URLadapter {
    public static String getCxArriveTimeUrl(String trainNum,String station){
        String url;
        String St_station=station;
        String St_trianNum=trainNum;
        String St_czEn = null;
        try {
            St_czEn=URLEncoder.encode(St_station,"utf-8").replace('%','-');
            St_station= URLEncoder.encode(St_station,"gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        St_trianNum=St_trianNum.toLowerCase();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd");
        String date=simpleDateFormat.format(new java.util.Date());
        String timemills=Long.toString(System.currentTimeMillis());
        url="http://dynamic.12306.cn/mapping/kfxt/zwdcx/LCZWD/cx.jsp?" +
                "cz="+St_station+"&cc="+St_trianNum+"&cxlx=0&rq="+date+"&czEn="+St_czEn+"&tp="+timemills;
        return url;
    }
    public static String getFrom2Url(String trainNum,String station){
        String url;
        String St_station=station;
        String St_trianNum=trainNum;
        String St_czEn = null;
        try {
            St_czEn=URLEncoder.encode(St_station,"utf-8").replace('%','-');
            St_station= URLEncoder.encode(St_station,"gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        St_trianNum=St_trianNum.toLowerCase();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd");
        String date=simpleDateFormat.format(new java.util.Date());
        String timemills=Long.toString(System.currentTimeMillis());
        url="http://dynamic.12306.cn/mapping/kfxt/zwdcx/LCZWD/from2.jsp?" +
                "cz="+St_station+"&cc="+St_trianNum+"&cxlx=0&rq="+date+"&czEn="+St_czEn+"&tp="+timemills;
        return url;
    }
    public static String getSceduleUrl(String trainNum){
        String url;
        String St_trianNum=trainNum.toUpperCase();
        url="http://trains.ctrip.com/trainbooking/TrainSchedule/"+St_trianNum+"/";
        return url;
    }
    public static String getHost(){
        return "http://localhost:8080/TrainArrive/";
    }
}
