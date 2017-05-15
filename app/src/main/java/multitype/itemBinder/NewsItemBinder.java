package multitype.itemBinder;

import android.support.annotation.NonNull;
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
import multitype.viewItem.FindNewsItem;


/**
 * Created by Administrator on 2017/4/8.
 */
public class NewsItemBinder extends ItemViewProvider<FindNewsItem, NewsItemBinder.FindNewsHolder> {
    protected FindNewsHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_news, parent, false);
        return new FindNewsHolder(view, new ViewHolderOnClick() {
            @Override
            public void onItemClick() {
            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull final FindNewsHolder holder, @NonNull FindNewsItem newsItem) {
        if (newsItem==null || TextUtils.isEmpty(newsItem.getTitle())) {
            holder.itemView.setVisibility(View.GONE);
        } else {
            holder.tv_newsTitle.setText(newsItem.getTitle());
            Glide.with(holder.itemView.getContext())
                    .load(newsItem.getNewsImg())
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(holder.iv_news));

            holder.tv_date.setText(newsItem.getDate());
        }

    }

    static class FindNewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_news;
        private TextView tv_newsTitle;
        private TextView tv_date;
        private LinearLayout ll_news;
        ViewHolderOnClick listener;

        public FindNewsHolder(View itemView, ViewHolderOnClick lisener) {
            super(itemView);
            iv_news = (ImageView) itemView.findViewById(R.id.iv_news);
            tv_newsTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
            tv_date = (TextView) itemView.findViewById(R.id.tv_news_date);
            ll_news = (LinearLayout) itemView.findViewById(R.id.ll_news);
            ll_news.setOnClickListener(this);
            this.listener = lisener;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick();

        }
    }

    private interface ViewHolderOnClick {
        void onItemClick();

    }
}
