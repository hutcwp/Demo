package me.hutcwp.demo.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import hut.cwp.annotations.BindView;
import hut.cwp.annotations.OnClick;
import hut.cwp.api.Injector;
import me.hutcwp.demo.R;

public class AnnotationActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView mTextView;

    @BindView(R.id.btn)
    Button mButton;

    @BindView(R.id.btn2)
    Button mButton2;

    @BindView(R.id.btn3)
    Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Injector.inject(this);
        mTextView.setText("hellow apt 编译时注解");

    }

    @OnClick(R.id.btn)
    //目前只能是无参数的方法
    public void test() {
        Toast.makeText(this, "click btn", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn2)
    public void tes2t() {
        Toast.makeText(this, "click btn2", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn3)
    public void test3() {
        Toast.makeText(this, "click btn3", Toast.LENGTH_LONG).show();
    }
}
