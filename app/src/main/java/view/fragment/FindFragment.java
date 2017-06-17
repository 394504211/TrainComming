package view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import com.androidtest.traincomming.R;
import java.util.ArrayList;
import java.util.List;
import bean.NewsListResponse;
import lib.litepreferences.LitePrefs;
import me.drakeet.multitype.MultiTypeAdapter;
import multitype.itemBinder.LoadMoreItemBinder;
import multitype.itemBinder.NewsItemBinder;
import multitype.viewItem.FindNewsItem;
import multitype.viewItem.LoadMoreItem;
import multitype.viewItem.NewsResponse;
import presenter.INewsPresenter;
import presenter.NewsPreImpl;

/**
 * Created by Administrator on 2017/3/29.
 */
public class FindFragment extends BaseFragment implements INewsView,SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private MultiTypeAdapter multiTypeAdapter;
    private SwipeRefreshLayout swiperefresh;
    private INewsPresenter iNewsPresenter;
    private int itemCount;
    private int lastVisibleItem;
    private int page = 1;
    private List<multitype.viewItem.Item> items = new ArrayList<>();
    LoadMoreItem loadMoreItem;


    @Override
    protected int getLayoutId() {
        return R.layout.find_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        this.swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recyclerView=(RecyclerView) view.findViewById(R.id.ry_find_news) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
        swiperefresh.setOnRefreshListener(this);
        iNewsPresenter=new NewsPreImpl(this);
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == itemCount - 1) {
                    onLoadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                itemCount = manager.getItemCount();
                lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
            }
        });
        multiTypeAdapter=new MultiTypeAdapter(items);
        multiTypeAdapter.register(NewsResponse.class, new NewsItemBinder());
        multiTypeAdapter.register(LoadMoreItem.class,new LoadMoreItemBinder());
        recyclerView.setAdapter(multiTypeAdapter);
        loadMoreItem = new LoadMoreItem();
        onRefresh();
    }

    @Override
    public void showDate(Object result) {
        NewsListResponse response = (NewsListResponse) result;
//        System.out.println(response.getCode()+"****"+response.getNews().size()+"***"+response.getStart()+"***"+response.getCode());
        if(response.getCode()==-1){
            multiTypeAdapter.notifyItemRemoved(lastVisibleItem);
            items.remove(loadMoreItem);
            Toast.makeText(getContext(), "已经拉到底了", Toast.LENGTH_LONG).show();
            return;
        }
        if(response.getStart()==0){
            items.clear();
            items.addAll(response.getNews());
            items.add(loadMoreItem);
            multiTypeAdapter.notifyItemInserted(items.size() - 1);
            if (page >= 1) {
                page = 1;
            }
        }else {
            multiTypeAdapter.notifyItemRemoved(lastVisibleItem);
            items.remove(loadMoreItem);
            int start = items.size();
            items.addAll(response.getNews());
            System.out.println("item size"+items.size());
            page++;
            items.add(loadMoreItem);
            multiTypeAdapter.notifyItemRangeInserted(start + 1, items.size() + 1);
        }
    }

    @Override
    public void loadFail(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        Log.e("showMessage", msg);
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void onRefresh() {
        System.out.println("onRefresh is called");
        String station= LitePrefs.getString(getResources().getString(R.string.Prefs_to) + 0);
        if(station.equals("0")){
            station="home";
        }
        iNewsPresenter.loadNewsList(station,"0",true);
    }

    @Override
    public void showProgress(boolean isRefresh) {
        if (isRefresh) {
            swiperefresh.setRefreshing(isRefresh);
        } else {

        }
    }

    @Override
    public void hideProgress() {
        swiperefresh.setRefreshing(false);
    }
    public void onLoadMore(){
        System.out.println("loadmore is call");
        String station= LitePrefs.getString(getResources().getString(R.string.Prefs_to) + 0);
        if(station.equals("0")){
            station="home";
        }
        iNewsPresenter.loadNewsList(station,String.valueOf(page*10),false);
    }

}
