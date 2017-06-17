package model.impl;

import com.google.gson.Gson;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import bean.ArriveInfoResponse;
import bean.BaseResponse;
import bean.ReviewsListResponse;
import bean.UserBean;
import model.ITripModel;
import multitype.viewItem.Item;
import multitype.viewItem.ReviewItem;
import network.ServiceFactory;
import network.services.IAddReviewService;
import network.services.IArriveInfoService;
import network.services.IReviewsService;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import util.CompleteListener;

/**
 * Created by xlf on 2017/5/10.
 */
public class TripModelImpl implements ITripModel {
    @Override
    public void loadReviewList(String trainId,String start,final CompleteListener listener) {
        System.out.println(trainId+start);
        IReviewsService service= ServiceFactory.createService(IReviewsService.class);
        service.getReviewList(trainId,start)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ReviewsListResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            listener.onFailed(null);
                            return;
                        }
                        listener.onFailed(new BaseResponse(404, e.getMessage()));
                    }

                    @Override
                    public void onNext(Response<ReviewsListResponse> reviewListResponse) {
                        if (reviewListResponse.isSuccessful()) {
                            String str = new Gson().toJson(reviewListResponse.body());
                            System.out.println(str);
                            listener.onCompleted(reviewListResponse.body());
                        } else {
                            listener.onFailed(new BaseResponse(reviewListResponse.code(), reviewListResponse.message()));
                        }
                    }
                });
    }
    @Override
    public void loadArriveInfo(String trainNum,String from,String to, String date, final CompleteListener listener) {
        IArriveInfoService arrivetimeService = ServiceFactory.createService(IArriveInfoService.class);
        arrivetimeService.getArrivetimeNet(trainNum,from,to, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ArriveInfoResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            listener.onFailed(null);
                            return;
                        }
                        listener.onFailed(new BaseResponse(404, e.getMessage()));
                    }

                    @Override
                    public void onNext(Response<ArriveInfoResponse> arriveInfoResponse) {
                        if (arriveInfoResponse.isSuccessful()) {
                            String str = new Gson().toJson(arriveInfoResponse.body());
                            listener.onCompleted(arriveInfoResponse.body());
                        } else {
                            listener.onFailed(new BaseResponse(arriveInfoResponse.code(), arriveInfoResponse.message()));
                        }
                    }
                });
    }
    @Override
    public void addReview(String trainId,String userId,String content, String moreContent, final CompleteListener listener) {
        IAddReviewService addReviewService = ServiceFactory.createService(IAddReviewService.class);
        Map<String,String> map= new HashMap<>();
        map.put("trainId",trainId);
        map.put("userId",userId);
        map.put("content",content);
        map.put("moreContent",moreContent);
        addReviewService.addReview(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Item>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            listener.onFailed(null);
                            return;
                        }
                        listener.onFailed(new BaseResponse(404, e.getMessage()));
                    }

                    @Override
                    public void onNext(Response<Item> resultResponse) {
                        if (resultResponse.isSuccessful()) {
                            String str = new Gson().toJson(resultResponse.body());
                            listener.onCompleted(resultResponse.body());
                        } else {
                            listener.onFailed(new BaseResponse(resultResponse.code(), resultResponse.message()));
                        }
                    }
                });
    }
    
}
