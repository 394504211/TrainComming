package adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/16.
 */
public class ScheduleAdapter {
    private List<Map<String,String>> schedule;
    public ScheduleAdapter(org.jsoup.select.Elements links){
        schedule= new ArrayList<>(100);
        for (int i=0;i<links.size();){
            if (i==0){
                Map<String,String> map = new HashMap<>();
                map.put("trainnum",links.get(i++).text());
                map.put("firstStation",links.get(i++).text());
                map.put("lastStation",links.get(i++).text());
                map.put("chufashijian",links.get(i++).text());
                map.put("daodashijian",links.get(i++).text());
                map.put("zongshijian",links.get(i++).text());
                schedule.add(map);
            }
            try {
                if(Integer.parseInt(links.get(i).text())>0&&Integer.parseInt(links.get(i).text())<100){
                    Map<String,String> map = new HashMap<>();
                    map.put("stationnum",links.get(i++).text());
                    map.put("station",links.get(i++).text());
                    map.put("daodashijian",links.get(i++).text());
                    map.put("chufashijian",links.get(i++).text());
                    map.put("tingcheshijian",links.get(i++).text());
                    schedule.add(map);
                }
            }catch (NumberFormatException e){
            }
            i++;
        }
    }
    public Map<String, String> getMap(String station) {
        for(int i=1;i<schedule.size();i++){
            System.out.println(schedule.get(i));
            if(schedule.get(i).get("station").equals(station)){
                return schedule.get(i);
            }
        }
        return null;
    }
    public String getArrivetTime (String station){
        for(int i=0;i<schedule.size();i++){
            if(schedule.get(i).get("station").equals(station)){
                return schedule.get(i).get("daodashijian");
            }
        }
        return null;
    }
    public String getLeftTime (String station){
        for(int i=0;i<schedule.size();i++){
            if(schedule.get(i).get("station").equals(station)){
                return schedule.get(i).get("chufashijian");
            }
        }
        return null;
    }
    public String getStopTime (String station){
        for(int i=0;i<schedule.size();i++){
            if(schedule.get(i).get("station").equals(station)){
                return schedule.get(i).get("tingcheshijian");
            }
        }
        return null;
    }
    public Map<String, String> getPreMap(String station) {
        for(int i=0;i<schedule.size();i++){
            if(schedule.get(i).get("station").equals(station)){
                return schedule.get(i-1);
            }
        }
        return null;
    }
}
