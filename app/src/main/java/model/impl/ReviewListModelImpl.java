package model.impl;

import com.google.gson.Gson;

import java.net.UnknownHostException;

import bean.BaseBean;
import model.IReviewListModel;
import network.ServiceFactory;
import network.services.IReviewsService;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import multitype.viewItem.ReviewItem;
import util.CompleteListener;

/**
 * Created by xlf on 2017/5/10.
 */
public class ReviewListModelImpl implements IReviewListModel {
    @Override
    public void loadReviewList(String trainId,final CompleteListener listener) {
        IReviewsService service= ServiceFactory.createService(IReviewsService.class);
        service.getReviewList(trainId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ReviewItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            listener.onFailed(null);
                            return;
                        }
                        listener.onFailed(new BaseBean(404, e.getMessage()));
                    }

                    @Override
                    public void onNext(Response<ReviewItem> reviewItemResponse) {
                        if (reviewItemResponse.isSuccessful()) {
                            String str = new Gson().toJson(reviewItemResponse.body());
                            listener.onComplected(reviewItemResponse.body());
                        } else {
                            listener.onFailed(new BaseBean(reviewItemResponse.code(), reviewItemResponse.message()));
                        }
                    }
                });
    }
    
}
