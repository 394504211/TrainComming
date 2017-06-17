package view.db;


import com.androidtest.traincomming.R;

import view.fragment.FindFragment;
import view.fragment.QueryFragment;
import view.fragment.SetFragment;
import view.fragment.TripListFragment;

/**
 * Created by Administrator on 2016/12/15.
 */
public class TabDb {
    public static String[] getTabsTxt(){
        String[] tabs={"查询","旅途","发现","设置"};
        return tabs;
    }
    public static int[] getTabsImg(){
        int[] ids={R.mipmap.query, R.mipmap.trip,R.mipmap.find,R.mipmap.setup};
        return ids;
    }
    public static int[] getTabsImgLight(){
        int[] ids={R.mipmap.query_active, R.mipmap.trip_active,R.mipmap.find_active,R.mipmap.setup_active};
        return ids;
    }
    public static Class[] getFragments(){
        Class[] clz={QueryFragment.class, TripListFragment.class, FindFragment.class, SetFragment.class};
        return clz;
    }
}
