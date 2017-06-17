package view;

/**
 * Created by Administrator on 2017/3/29.
 */
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.androidtest.traincomming.R;

import view.db.TabDb;


public class StartActivity extends FragmentActivity
        implements TabHost.OnTabChangeListener {
    private FragmentTabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        tabHost=(FragmentTabHost)super.findViewById(android.R.id.tabhost);
        tabHost.setup(this,super.getSupportFragmentManager()
                ,R.id.contentLayout);
        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setOnTabChangedListener(this);
        initTab();

    }
    private void initTab(){
        String tabs[]= TabDb.getTabsTxt();
        for(int i=0;i<tabs.length;i++){
            TabHost.TabSpec tabSpec=tabHost.newTabSpec(tabs[i]).setIndicator(getTabView(i));
            tabHost.addTab(tabSpec,TabDb.getFragments()[i],null);
            tabHost.setTag(i);
            System.out.println(tabSpec.getTag()+"spc tag"+tabHost.getTag()+"hosttag"+tabHost.getId()+"hostid");
        }
    }
    private View getTabView(int idx){
        View view= LayoutInflater.from(this).inflate(R.layout.footer_tabs,null);
        ((TextView)view.findViewById(R.id.tvTab)).setText(TabDb.getTabsTxt()[idx]);
        if(idx==0){
            ((ImageView)view.findViewById(R.id.ivImg)).setImageResource(TabDb.getTabsImgLight()[idx]);
            ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.rgb(61,133,198));
        }else{
            ((ImageView)view.findViewById(R.id.ivImg)).setImageResource(TabDb.getTabsImg()[idx]);
            ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.rgb(143,143,143));
        }
        return view;
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
    @Override
    public void onTabChanged(String tabId) {
        // TODO Auto-generated method stub
        updateTab();
    }
    private void updateTab(){
        TabWidget tabw=tabHost.getTabWidget();
        for(int i=0;i<tabw.getChildCount();i++){
            View view=tabw.getChildAt(i);
            ImageView iv=(ImageView)view.findViewById(R.id.ivImg);
            TextView tv=(TextView)view.findViewById(R.id.tvTab);
            if(i==tabHost.getCurrentTab()){
                iv.setImageResource(TabDb.getTabsImgLight()[i]);
                tv.setTextColor(Color.rgb(61,133,198));
            }else{
                iv.setImageResource(TabDb.getTabsImg()[i]);
                tv.setTextColor(Color.rgb(143,143,143));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}