package model;

import util.CompleteListener;

/**
 * Created by xlf on 2017/5/10.
 */
public interface ITripModel {
    void loadReviewList(String trainId,String start, CompleteListener listener);
    void loadArriveInfo(String trainNum,String from,String to, String date,CompleteListener listener);
    void addReview(String trainId,String userId,String content,String moreContent,CompleteListener listener);
}
