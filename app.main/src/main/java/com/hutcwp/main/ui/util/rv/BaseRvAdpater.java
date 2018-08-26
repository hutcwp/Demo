package com.hutcwp.main.ui.util.rv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hutcwp on 2018/8/26 21:51
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public abstract class BaseRvAdpater<T> extends RecyclerView.Adapter<BaseRvAdpater.mHolder> {

    public List<T> dataList = new ArrayList<>();

    public BaseRvAdpater(List<T> datas) {
        this.dataList = datas;
    }

    @Override
    public BaseRvAdpater.mHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);
        return new BaseRvAdpater.mHolder(rootView);
    }

    // @Override
    // public void onBindViewHolder(mHolder holder, int position) {
    //
    // }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addDatas(List<T> datas) {
        datas.addAll(datas);
        notifyItemChanged(getItemCount());
    }

    public void setNewData(List<T> newDatas) {
        dataList = newDatas;
        notifyDataSetChanged();
    }


    public abstract int getLayout();


    public class mHolder extends RecyclerView.ViewHolder {
        private View rootView;

        mHolder(View view) {
            super(view);
            rootView = view;
        }

        public View getRoot() {
            return rootView;
        }
    }
}
