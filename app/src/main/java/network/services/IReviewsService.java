package network.services;

import bean.ReviewsListResponse;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xlf on 2017/5/9.
 */
public interface IReviewsService {
    @GET("anquery/reviews/{trainId}/{start}")
    Observable<Response<ReviewsListResponse>> getReviewList(
            @Path("trainId")String trainId,
            @Path("start")String start
//            @Path("startCount")String startCount
            );
}
