package me.hutcwp.demo.ui.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.hutcwp.demo.R;
import me.hutcwp.demo.base.Component;

public class PublicMessageComponent extends Component {

    public static final String TAG = "PublicMessageComponent";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_public_message, container, false);
        return view;
    }
}
