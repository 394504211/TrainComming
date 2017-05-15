package multitype.itemBinder;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidtest.traincomming.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;


import me.drakeet.multitype.ItemViewProvider;
import multitype.viewItem.ReviewItem;

/**
 * Created by Administrator on 2017/4/8.
 */
public class ReviewItemBinder extends ItemViewProvider<ReviewItem, ReviewItemBinder.TrainReviewHolder> {
    protected TrainReviewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_train_review, parent, false);
        return new TrainReviewHolder(view, new ViewHolderOnClick() {
            @Override
            public void onItemClick() {
                System.out.println("LL_comment is Click");

            }

            @Override
            public void onLikeClick() {
                System.out.println("iv_like is Click");
            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull final TrainReviewHolder holder, @NonNull ReviewItem reviewItem) {
        if (reviewItem==null || TextUtils.isEmpty(reviewItem.getContent())) {
            holder.itemView.setVisibility(View.GONE);
        } else {
            holder.tv_username.setText(reviewItem.getAuthor().getName());
            Glide.with(holder.itemView.getContext())
                    .load(reviewItem.getAuthor().getAvatar())
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(holder.iv_avatar) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(holder.itemView.getContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            holder.iv_avatar.setImageDrawable(circularBitmapDrawable);
                        }
                    });

            if (reviewItem.getRating() != null) {
                holder.rating.setRating(Float.valueOf(reviewItem.getRating().getValue()));
            }
            holder.tv_content.setText(reviewItem.getContent());
            holder.tv_likes.setText(reviewItem.getLikes() + "");
            holder.tv_update.setText(reviewItem.getUpdate().split(" ")[0]);
            holder.tv_morecontent.setText(reviewItem.getMorecontent());
        }

    }

    static class TrainReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_avatar;
        private TextView tv_username;
        private TextView tv_likes;
        private TextView tv_content;
        private TextView tv_morecontent;
        private TextView tv_update;
        private AppCompatRatingBar rating;
        private ImageView iv_like;
        private LinearLayout ll_comment;
        ViewHolderOnClick listener;

        public TrainReviewHolder(View itemView, ViewHolderOnClick lisener) {
            super(itemView);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tv_username = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_likes = (TextView) itemView.findViewById(R.id.tv_favorite_num);
            tv_content = (TextView) itemView.findViewById(R.id.tv_comment_content);
            tv_morecontent = (TextView) itemView.findViewById(R.id.tv_more_comment);
            tv_update = (TextView) itemView.findViewById(R.id.tv_update_time);
            rating = (AppCompatRatingBar) itemView.findViewById(R.id.ratingBar_hots);
            iv_like = (ImageView) itemView.findViewById(R.id.iv_favorite);
            ll_comment = (LinearLayout) itemView.findViewById(R.id.ll_comment);
            ll_comment.setOnClickListener(this);
            iv_like.setOnClickListener(this);
            this.listener = lisener;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_favorite:
                    listener.onLikeClick();
                    break;
                case R.id.ll_comment:
                    listener.onItemClick();
                    break;
            }

        }
    }

    private interface ViewHolderOnClick {
        public void onItemClick();

        public void onLikeClick();

    }
}
