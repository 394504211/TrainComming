package presenter;

import bean.BaseBean;
import model.IReviewListModel;
import model.impl.ReviewListModelImpl;
import util.CompleteListener;
import view.IBaseView;



public class TripViewPreImpl implements ITripViewPresenter, CompleteListener {
    IBaseView iBaseView;
    IReviewListModel iReviewListModel;

    public TripViewPreImpl(IBaseView iBaseView){
        this.iBaseView=iBaseView;
        iReviewListModel=new ReviewListModelImpl();
    }
    @Override
    public void onComplected(Object result) {
        iBaseView.showDate(result);
    }

    @Override
    public void onFailed(BaseBean msg) {

    }

    @Override
    public void loadTimeInfo(String trainId, String trainStation) {

    }

    @Override
    public void loadReviews(String tarinId) {

    }
}
