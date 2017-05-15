package view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.androidtest.traincomming.R;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import multitype.itemBinder.NewsItemBinder;
import multitype.viewItem.FindNewsItem;

/**
 * Created by Administrator on 2017/3/29.
 */
public class FindFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private MultiTypeAdapter multiTypeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.find_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView=(RecyclerView) view.findViewById(R.id.ry_find_news) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        multiTypeAdapter=new MultiTypeAdapter();
        multiTypeAdapter.register(FindNewsItem.class, new NewsItemBinder());
        recyclerView.setAdapter(multiTypeAdapter);
    }

    @Override
    protected void initData() {
        FindNewsItem item=new FindNewsItem();
        item.setDate("2017-4-9");
        item.setTitle("这是第一条新闻信息");
        item.setNewsImg("http://pic.qiantucdn.com/58pic/10/96/99/18u58PICXCS.jpg");
        item.setUrl("http://www.baidu.com");
        Items items=new Items();
        items.add(item);
        items.add(item);
        items.add(item);
        multiTypeAdapter.setItems(items);
        multiTypeAdapter.notifyDataSetChanged();
    }
}
