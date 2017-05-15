package network.services;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import multitype.viewItem.ReviewItem;

/**
 * Created by xlf on 2017/5/9.
 */
public interface IReviewsService {
    @GET("Review/ReviewList/{trainId}")
    Observable<Response<ReviewItem>> getReviewList(
            @Path("trainId")String trainId
            );
}
