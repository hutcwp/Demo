package com.hutcwp.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hutcwp.main.R;
import com.hutcwp.main.db.entitys.SignRecordEntity;
import com.hutcwp.main.model.SignRecord;
import com.hutcwp.main.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class SignRecordAdapter extends RecyclerView.Adapter<SignRecordAdapter.SignRecordHolder> {

    private List<SignRecord> datas = new ArrayList<>();

    public SignRecordAdapter(List<SignRecord> datas) {
        this.datas = datas;
    }

    @Override
    public SignRecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_record, parent, false);
        return new SignRecordHolder(rootView);
    }

    @Override
    public void onBindViewHolder(SignRecordHolder holder, int position) {

        SignRecord data = datas.get(position);
        String strTime = String.format("%s - %s", data.getStartTime(), data.getEndTime());

        holder.tv_date.setText(data.getDate());
        holder.tv_time.setText(strTime);

        long subSecond = DateUtil.subTimeHHmmss(data.getStartTime(), data.getEndTime());
        float subHour = subSecond / 3600f;
        String status = "--";
        if (subHour > 8) {
            status = "完美";
        } else if (subHour > 7) {
            status = "良好";
        } else if (subHour > 6) {
            status = "较差";
        } else {
            status = "极差";
        }
        holder.tv_status.setText(status);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addDatas(List<SignRecord> datas) {
        datas.addAll(datas);
        notifyItemChanged(getItemCount());
    }

    public void setNewData(List<SignRecord> newDatas) {
        datas = newDatas;
        notifyDataSetChanged();
    }


    class SignRecordHolder extends RecyclerView.ViewHolder {
        TextView tv_date;
        TextView tv_time;
        TextView tv_status;

        SignRecordHolder(View view) {
            super(view);
            tv_date = view.findViewById(R.id.tv_date);
            tv_time = view.findViewById(R.id.tv_time);
            tv_status = view.findViewById(R.id.tv_statues);
        }
    }

}
