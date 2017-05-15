package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidtest.traincomming.R;


import network.URL.URLadapter;
import network.HttpGet;

public class MainActivity extends AppCompatActivity {
    EditText train_num;
    EditText train_station;
    Button btn_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_query = (Button) findViewById(R.id.query);
        train_num = (EditText) findViewById(R.id.Et_Train);
        train_station = (EditText) findViewById(R.id.Et_Station);
        btn_query.setOnClickListener(new queryBtnListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private class queryBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String St_tarinnum = train_num.getText().toString();
            String St_station = train_station.getText().toString();
//            HttpGet httpGet3 = new HttpGet(URLadapter.getSceduleUrl(St_tarinnum));
//            Thread td3 = new Thread(httpGet3);
//            td3.start();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(httpGet3.getResult());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            org.jsoup.nodes.Document doc = Jsoup.parse(httpGet3.getResult());
//            org.jsoup.select.Elements links = doc.getElementsByTag("td");
//            ScheduleAdapter schedule = new ScheduleAdapter(links);
//            Map map = schedule.getMap(St_station);
//            //要处理找不到车站的null
//            System.out.println((String) map.get("stationnum") + map.get("station") + map.get("daodashijian") + map.get("chufashijian") + map.get("tingcheshijian"));
            HttpGet httpGet = new HttpGet(URLadapter.getFrom2Url(St_tarinnum, St_station));
            Thread td=new Thread(httpGet);
            HttpGet httpGet2 = new HttpGet(URLadapter.getCxArriveTimeUrl(St_tarinnum, St_station));
            Thread td2=new Thread(httpGet2);
            td.start();
            td2.start();
            int count=0;
            while(httpGet2.getResult().equals("")){
                try {
                    System.out.println("count is"+count);
                    Thread.sleep(500);
                    count++;
                    if(count>3){
                        System.out.println("连接超时请重试");
                        count=0;
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!httpGet.getResult().equals("")){
                System.out.println("count is"+count);
                System.out.println(httpGet.getResult());
            }
            if(!httpGet2.getResult().equals("")){
                System.out.println("count is"+count);
                System.out.println(httpGet2.getResult());
            }
        }
    }
}
