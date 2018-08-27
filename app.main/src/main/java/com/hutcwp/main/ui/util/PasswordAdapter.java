package com.hutcwp.main.ui.util;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hutcwp.main.R;
import com.hutcwp.main.db.repos.AccountRepos;
import com.hutcwp.main.ui.util.rv.BaseRvAdpater;

import java.util.List;

/**
 * Created by hutcwp on 2018/8/26 22:10
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class PasswordAdapter extends BaseRvAdpater<Account> {

    public PasswordAdapter(List<Account> datas) {
        super(datas);
    }

    @Override
    public int getLayout() {
        return R.layout.item_password;
    }

    @Override
    public void onBindViewHolder(BaseRvAdpater.mHolder holder, final int position) {
        TextView tvName = holder.getRoot().findViewById(R.id.tv_name);
        TextView tvPwd = holder.getRoot().findViewById(R.id.tv_password);
        Button btnDelete = holder.getRoot().findViewById(R.id.btn_delete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountRepos.getmInstance().deleteAccount(dataList.get(position));
            }
        });
        tvPwd.setText(dataList.get(position).getPassword());
        tvName.setText(dataList.get(position).getUsername());
    }
}
