package view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidtest.traincomming.R;

import java.util.ArrayList;
import java.util.List;

import bean.UserBean;
import bean.UserRatingBean;
import lib.Jerrychart.ChartData;
import lib.Jerrychart.JerryChartView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import multitype.itemBinder.ReviewItemBinder;
import multitype.viewItem.ReviewItem;
import view.IBaseView;

/**
 * Created by Administrator on 2017/3/29.
 */
public class TripFragment extends BaseFragment implements IBaseView {
    private JerryChartView jcv;
    private RecyclerView recyclerView;
    private MultiTypeAdapter multiTypeAdapter;
    private SwipeRefreshLayout swiperefresh;


    @Override
    protected int getLayoutId() {
        return R.layout.trip_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //添加环图,添加列表
        jcv = (JerryChartView) view.findViewById(R.id.jcv);
        //创建评论
        recyclerView = (RecyclerView) view.findViewById(R.id.ry_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        multiTypeAdapter = new MultiTypeAdapter();
        multiTypeAdapter.register(ReviewItem.class, new ReviewItemBinder());
        recyclerView.setAdapter(multiTypeAdapter);
    }

    @Override
    protected void initData() {
        //环图数据
        List<ChartData> datas = new ArrayList<>();

        ChartData cd02 = new ChartData(0xff99ccff, 0.01f, 0xffffffff, false, "4分");
        ChartData cd12 = new ChartData(0xffffcc99, 0.99f, 0xff898989, false, "2时40分");

        datas.add(cd02);
        datas.add(cd12);


        jcv.setData(datas);

        //评论数据
        ReviewItem reviewItem = new ReviewItem();
        UserBean user1 = new UserBean();
        user1.setAvatar("http://pic.qiantucdn.com/58pic/10/96/99/18u58PICXCS.jpg");
        user1.setId("00101");
        user1.setName("user1");
        reviewItem.setAuthor(user1);
        reviewItem.setContent("这是第一条评论！");
        reviewItem.setLikes(100);
        reviewItem.setMorecontent("这是地零条评论");
        reviewItem.setUpdate("10分钟前");
        UserRatingBean ratingBean = new UserRatingBean();
        ratingBean.setValue("3");
        reviewItem.setRating(ratingBean);
        Items items = new Items();
        items.add(reviewItem);
        items.add(reviewItem);
        items.add(reviewItem);
        multiTypeAdapter.setItems(items);
        multiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDate(Object result) {

    }

    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        Log.e("showMessage", msg);
    }

    public void showProgress(boolean isRefresh) {
        if (isRefresh) {
            swiperefresh.setRefreshing(isRefresh);
        } else {

        }
    }
    public void hideProgress() {
        swiperefresh.setRefreshing(false);
    }
}
