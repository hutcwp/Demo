package me.hutcwp.app.dynamic.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public abstract class BaseAdapter<DB extends ViewDataBinding, T> extends RecyclerView.Adapter {

    protected List<T> dataList;

    private Context context;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public BaseAdapter(Context context, List<T> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    // 抽象函数  获取布局资源id
    public abstract int getLayoutId();

    // 抽象函数  通过databinding为布局设置数据
    public abstract void bindView(CommonViewHolder viewHolder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 生成DB对象 (这个方法是不是和View.inflate()很像？)
        DB bindView = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), parent, false);
        return new CommonViewHolder(bindView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 调用抽象函数，将holder强转为CommonViewHodler，供子类Adapter使用其成员对象bindView；
        bindView((CommonViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class CommonViewHolder extends RecyclerView.ViewHolder {

        public DB bindView;

        // 每一个item都必须持有的一个ViewDataBinding子类对象
        public CommonViewHolder(DB bindView) {
            super(bindView.getRoot());
            this.bindView = bindView;
        }
    }
}
