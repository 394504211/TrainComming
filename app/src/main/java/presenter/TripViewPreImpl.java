package presenter;

import bean.BaseResponse;
import model.ITripModel;
import model.impl.TripModelImpl;
import util.CompleteListener;
import view.activity.ITripView;


public class TripViewPreImpl implements ITripViewPresenter, CompleteListener {
    ITripView iTripView;
    ITripModel iTripModel;

    public TripViewPreImpl(ITripView iTripView){
        this.iTripView=iTripView;
        iTripModel=new TripModelImpl();
    }
    @Override
    public void onCompleted(Object result) {
        iTripView.showDate(result);
    }


    @Override
    public void onFailed(BaseResponse msg) {
        iTripView.loadFail(msg.getMsg());
    }

    @Override
    public void loadArriveInfo(String trainId, String from, String to, String date) {
        iTripModel.loadArriveInfo(trainId, from, to, date, this);
    }

    @Override
    public void loadReviews(String trainId,String start,boolean isRefresh) {
        iTripModel.loadReviewList(trainId,start ,this);
    }

    @Override
    public void addReview(String trainId, String userId, String content, String moreContent) {
        iTripModel.addReview(trainId,userId,content,moreContent,this);
    }
}
