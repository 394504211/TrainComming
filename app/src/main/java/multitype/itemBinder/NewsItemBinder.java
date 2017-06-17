package multitype.itemBinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtest.traincomming.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import me.drakeet.multitype.ItemViewProvider;
import multitype.viewItem.NewsResponse;
import view.activity.webViewActivity;


/**
 * Created by Administrator on 2017/4/8.
 */
public class NewsItemBinder extends ItemViewProvider<NewsResponse, NewsItemBinder.FindNewsHolder> {
    @NonNull
    protected FindNewsHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_news, parent, false);
        return new FindNewsHolder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull final FindNewsHolder holder, @NonNull final NewsResponse newsItem) {
        if (TextUtils.isEmpty(newsItem.getTitle())) {
            holder.itemView.setVisibility(View.GONE);
        } else {
            holder.tv_newsTitle.setText(newsItem.getTitle());
            Glide.with(holder.itemView.getContext())
                    .load(newsItem.getImage())
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(holder.iv_news));

            holder.tv_date.setText(newsItem.getAuthor() + " " + newsItem.getUpdatetime());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), webViewActivity.class);
                    intent.putExtra("url",newsItem.getUrl());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    static class FindNewsHolder extends RecyclerView.ViewHolder {
        private ImageView iv_news;
        private TextView tv_newsTitle;
        private TextView tv_date;

        public FindNewsHolder(View itemView) {
            super(itemView);
            iv_news = (ImageView) itemView.findViewById(R.id.iv_news);
            tv_newsTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
            tv_date = (TextView) itemView.findViewById(R.id.tv_news_date);
        }
    }
}
