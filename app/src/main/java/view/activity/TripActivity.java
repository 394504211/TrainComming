package view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtest.traincomming.R;
import com.bigkoo.convenientbanner.ConvenientBanner;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.ArriveInfoResponse;
import bean.ReviewsListResponse;
import bean.TrainBean;
import bean.UserBean;
import bean.UserRatingBean;
import cn.iwgang.countdownview.CountdownView;
import dialog.addTripDialogBuilder;
import lib.ConvenientBanner.LocalImageHolderView;
import lib.Jerrychart.ChartData;
import lib.Jerrychart.JerryChartView;
import lib.litepreferences.LitePrefs;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import multitype.itemBinder.LoadMoreItemBinder;
import multitype.itemBinder.ReviewItemBinder;
import multitype.viewItem.Item;
import multitype.viewItem.LoadMoreItem;
import multitype.viewItem.ReviewItem;
import presenter.ArriveInfoPreImpl;
import presenter.IArriveInfoPresenter;
import presenter.ITripViewPresenter;
import presenter.TripViewPreImpl;
import view.IBaseView;

import com.ToxicBakery.viewpager.transforms.*;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

/**
 * Created by Administrator on 2017/3/29.
 */
public class TripActivity extends FragmentActivity implements ITripView,
        AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, OnItemClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private int isLoadMore;
    private JerryChartView jcv;
    private RecyclerView recyclerView;
    private MultiTypeAdapter multiTypeAdapter;
    private TextView tv_from;
    private TextView tv_to;
    private TextView tv_trainId;
    private TextView tv_arriveTime;
    private TextView tv_curStation;
    private TextView tv_nextStation;
    private TextView tv_addReview;
    private ConvenientBanner<Integer> convenientBanner;//顶部广告栏控件
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private CountdownView mCvCountdownView;
    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";
    private Button dateButton;
    private Button timeButton;
    private ITripViewPresenter tripPresenter;
    private SwipeRefreshLayout swiperefresh;
    private List<Item> items = new ArrayList<>();
    private LoadMoreItem loadMoreItem;
    private int itemCount;
    private int lastVisibleItem;
    private int page = 1;
    private String from;
    private String to;
    private String trainId;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_fragment);
        //获取textView
        tv_from = (TextView) findViewById(R.id.tv_trip_from);
        tv_to = (TextView) findViewById(R.id.tv_trip_to);
        tv_trainId = (TextView) findViewById(R.id.tv_trip_trainId);
        tv_arriveTime = (TextView) findViewById(R.id.tv_trip_arriveTime);
        tv_curStation = (TextView) findViewById(R.id.tv_trip_curStation);
        tv_nextStation = (TextView) findViewById(R.id.tv_trip_nextStation);
        tv_addReview = (TextView) findViewById(R.id.tv_trip_addReview);
        //添加环图,添加列表
        jcv = (JerryChartView) findViewById(R.id.jcv);
        //评论dialog
        tv_addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShow_addReview(v);
            }
        });
        //创建评论
        recyclerView = (RecyclerView) findViewById(R.id.ry_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //评论滑动加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        loadMoreItem = new LoadMoreItem();
        multiTypeAdapter = new MultiTypeAdapter();
        multiTypeAdapter.setItems(items);
        multiTypeAdapter.register(ReviewItem.class, new ReviewItemBinder());
        multiTypeAdapter.register(LoadMoreItem.class, new LoadMoreItemBinder());
        recyclerView.setAdapter(multiTypeAdapter);
        //倒计时控件
        mCvCountdownView = (CountdownView) findViewById(R.id.cv_countdownViewTest1);
        //添加广告图
        convenientBanner = (ConvenientBanner<Integer>) findViewById(R.id.convenientBanner);
        init();
        //日期、时间选择器
        final Calendar calendar = Calendar.getInstance();
        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);

        final DatePickerDialog datePickerDialog =
                DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        final TimePickerDialog timePickerDialog =
                TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);

        dateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.setVibrate(false);
                timePickerDialog.setCloseOnSingleTapMinute(false);
                timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });

        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }

            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
        }
        //获取intent信息，获取Info
        Intent intent = getIntent();
        final TrainBean aimInfo = (TrainBean) intent.getSerializableExtra("info");
        from = aimInfo.getFrom();
        to = aimInfo.getTo();
        date = aimInfo.getDate();
        trainId = aimInfo.getTrainId();
        tripPresenter = new TripViewPreImpl(this);
        onRefresh();

    }

    @Override
    public void showDate(Object result) {
        if (result instanceof ArriveInfoResponse) {
            ArriveInfoResponse response = (ArriveInfoResponse) result;
            //环图数据
            if(response.getCode()==-1){
                Toast.makeText(this, "没有更多了", Toast.LENGTH_SHORT).show();
                return;
            }
            List<ChartData> datas = new ArrayList<>();
            ChartData cd02 = new ChartData(0xff99ccff, ((float) response.getRunTime()) / ((float) response.getAllTime())
                    , 0xffffffff, false, String.valueOf(response.getRunTime())+"分");
            ChartData cd12 = new ChartData(0xffffcc99, 1-(((float) response.getRunTime()) / ((float) response.getAllTime()))
                    , 0xff898989, false, String.valueOf(response.getAllTime())+"分");
            datas.add(cd02);
            datas.add(cd12);
            jcv.setData(datas);
            tv_from.setText(response.getFrom());
            tv_to.setText(response.getTo());
            tv_trainId.setText(response.getTrainNum());
            tv_nextStation.setText(response.getNextStation());
            tv_curStation.setText(response.getCurrStation());
            tv_arriveTime.setText(response.getTime());
            System.out.println((response.getAllTime() * 60 * 1000) - (response.getRunTime() * 60 * 1000));
            mCvCountdownView.start((response.getAllTime() * 60 * 1000) - (response.getRunTime() * 60 * 1000));
        }
        if (result instanceof ReviewsListResponse) {
            ReviewsListResponse response = (ReviewsListResponse) result;

            if (response.getStart() == 0) {
                items.clear();
                items.addAll(response.getReviews());
                items.add(loadMoreItem);
                multiTypeAdapter.notifyItemInserted(items.size() - 1);
                if (page >= 1) {
                    page = 1;
                }
            }
            if(response.getCode()==0) {
                isLoadMore = -1;
                multiTypeAdapter.notifyItemRemoved(lastVisibleItem);
                items.remove(loadMoreItem);
            }else {
                multiTypeAdapter.notifyItemRemoved(lastVisibleItem);
                items.remove(loadMoreItem);

                int start = items.size();
                items.addAll(response.getReviews());
                page++;

                items.add(loadMoreItem);
                multiTypeAdapter.notifyItemRangeInserted(start + 1, items.size() + 1);
            }
        }
    }

    @Override
    public void loadFail(String msg) {

    }

    @Override
    public void showMessage(String msg) {

    }

    public void onRefresh() {
        tripPresenter.loadArriveInfo(trainId, from, to, date);
        tripPresenter.loadReviews(trainId, "0", true);
    }

    public void onLoadMore() {
        if(isLoadMore==-1){
            return;
        }
        System.out.println(trainId+String.valueOf(page * 10));
        tripPresenter.loadReviews(trainId, String.valueOf(page * 10), false);
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        dateButton.setText(year + "-" + (month + 1) + "-" + day);
        Toast.makeText(this, "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        timeButton.setText(hourOfDay + ":" + String.format("%02d", minute));
        Toast.makeText(this, "new time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG).show();
    }


    private void init() {
        if (localImages.isEmpty())
            loadTestDatas();
        //本地图片例子
        try {
            convenientBanner.setPages(
                    new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, localImages)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                    //设置翻页的效果，不需要翻页效果可用不设
                    .setPageTransformer((ABaseTransformer) Class.forName("com.ToxicBakery.viewpager.transforms.DefaultTransformer").newInstance())
                    .setOnPageChangeListener(this)//监听翻页事件
                    .setOnItemClickListener(this);
        } catch (java.lang.InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    加入测试Views
    * */
    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 3; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    //点击切换效果
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        Toast.makeText(this, "监听到翻到第" + position + "了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    public void dialogShow_addReview(View v) {
        LayoutInflater inflater = ((Activity) v.getContext()).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_addreview, null);
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle(null)                                  //.withTitle(null)  no title
                .withDividerColor("#000000")                              //def
                .withMessage(null)                     //.withMessage(null)  no Msg
                .withMessageColor("#000000")                              //def  | withMessageColor(int resid)
                .withDialogColor("#ffffff")
                .withButtonDrawable(R.drawable.button_style)//def  | withDialogColor(int resid)                               //def
                .isCancelableOnTouchOutside(false)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(Effectstype.Shake)                                         //def Effectstype.Slidetop
                .withButton1Text("确定")                                      //def gone
                .withButton2Text("取消")                                  //def gone
                .setCustomView(view, v.getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et_review = (EditText) view.findViewById(R.id.dialog_addReview);
                        String review = et_review.getText().toString();
                        if (review.isEmpty()) {
                            Toast.makeText(v.getContext(), getResources().getString(R.string.querynull), Toast.LENGTH_SHORT).show();
                        } else {
                            tripPresenter.addReview(trainId, LitePrefs.getString("userId"),review, "");
                            dialogBuilder.dismiss();
                            onRefresh();
                        }
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }
}
