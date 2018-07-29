package me.hutcwp.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.hutcwp.demo.R;
import me.hutcwp.demo.ui.component.PublicMessageComponent;
import me.hutcwp.demo.ui.component.VideoComponent;

public class LiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        loadComponent();
    }

    private void loadComponent() {
        PublicMessageComponent publicMessageComponent = new PublicMessageComponent();
        VideoComponent videoComponent = new VideoComponent();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.public_message_view, publicMessageComponent, publicMessageComponent.TAG)
                .replace(R.id.video_view, videoComponent, videoComponent.TAG)
                .commitAllowingStateLoss();
    }

}
