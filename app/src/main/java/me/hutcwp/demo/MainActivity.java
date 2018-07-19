package me.hutcwp.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.hutcwp.apt_api.ViewInjector;
import me.hutcwp.apt_lib.Bind;
import me.hutcwp.apt_lib.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv)
    TextView mTextView;
    @Bind(R.id.btn)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewInjector.injectView(this);
        mTextView.setText("hellow apt 编译时注解");

    }

    @OnClick(R.id.btn)
    public void test(){
        Toast.makeText(this,"click",Toast.LENGTH_LONG).show();
    }
}
