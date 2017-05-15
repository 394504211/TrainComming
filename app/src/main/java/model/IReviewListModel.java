package model;

import util.CompleteListener;

/**
 * Created by xlf on 2017/5/10.
 */
public interface IReviewListModel {
    void loadReviewList(String trainId, CompleteListener listener);
}
