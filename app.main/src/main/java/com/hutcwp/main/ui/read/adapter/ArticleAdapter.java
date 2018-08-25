package com.hutcwp.main.ui.read.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hutcwp.main.R;
import com.hutcwp.main.model.Article;
import com.hutcwp.main.util.WebUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理Article Item的Adapter
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ReadHolder> {

    private static final String TAG = "ArticleAdapter";

    private List<Article> mReadList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public ArticleAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public ArticleAdapter(Context context, List<Article> list) {
        this(context);
        this.mReadList = list;
    }

    @Override
    public ReadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_read, parent, false);
        return new ReadHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReadHolder holder, int position) {
        final Article item = mReadList.get(position);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebUtils.openInternal(mContext, item.getUrl());
            }
        });
        //将标题设置为 序号.内容这种格式
        holder.tv_name.setText(String.format("%s. %s", position + 1, item.getName()));
        holder.tv_info.setText(String.format("%s • %s", item.getUpdateTime(), item.getFrom()));

        Glide.with(mContext).load(item.getIcon()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.iv.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = mReadList == null ? 0 : mReadList.size();
        Log.d(TAG, "getItemCount#count = " + count);
        return count;
    }

    public List<Article> getData() {
        return mReadList;
    }

    public void setNewData(List<Article> data) {
        this.mReadList = data;
        int count = mReadList == null ? 0 : mReadList.size();
        Log.d(TAG, "setNewData count = " + count);
        notifyDataSetChanged();
    }

    public void addData(List<Article> data) {
        int position = mReadList.size() ;
        this.mReadList.addAll(position, data);
        int count = mReadList == null ? 0 : mReadList.size();
        Log.d(TAG, "addData count = " + count);
        this.notifyItemRangeInserted(position, data.size());
    }


    class ReadHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_info;
        private ImageView iv;
        private View rootView;

        ReadHolder(View view) {
            super(view);
            rootView = view;
            tv_name = view.findViewById(R.id.tv_read_name);
            tv_info = view.findViewById(R.id.tv_read_info);
            iv = view.findViewById(R.id.iv_read_icon);
        }
    }
}