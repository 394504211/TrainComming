package presenter;

/**
 * Created by xlf on 2017/5/14.
 */
public interface ITripViewPresenter {
    void loadArriveInfo(String trainId,String from,String to,String Date);
    void loadReviews(String tarinId,String start,boolean isRefresh);
    void addReview(String trainId,String userId,String content,String moreContent);
}
