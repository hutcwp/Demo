package com.hutcwp.livebiz.ui.component.publicmessage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hutcwp.livebiz.ui.component.Component;

import hut.cwp.mvp.BindPresenter;
import com.hutcwp.livebiz.R;

@BindPresenter(presenter = PublicMessagePresenter.class)
public class PublicMessageComponent extends Component<PublicMessagePresenter, IPublicMessageComponent> implements IPublicMessageComponent {

    private Button btnLog;
    private Button btnTest;
    public static final String TAG = "PublicMessageComponent";

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        assert inflater != null;
        View view = inflater.inflate(R.layout.component_public_message, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View root) {
        btnTest = root.findViewById(R.id.btnTest);
        btnLog = root.findViewById(R.id.btnLog);
    }

    private void initListener() {
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().performLog();
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().performLog();
            }
        });
    }

    @Override
    public void showTest() {
        Toast.makeText(getContext(), "showTest", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLog() {
        Toast.makeText(getContext(), "showLog", Toast.LENGTH_LONG).show();
    }
}
