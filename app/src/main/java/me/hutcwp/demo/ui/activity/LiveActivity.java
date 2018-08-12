package me.hutcwp.demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.hutcwp.demo.R;
import me.hutcwp.demo.base.mvp.BindPresenter;
import me.hutcwp.demo.base.mvp.MvpActivity;
import me.hutcwp.demo.ui.component.danmu.DanmuComponent;
import me.hutcwp.demo.ui.component.publicmessage.PublicMessageComponent;
import me.hutcwp.demo.ui.component.video.VideoComponent;

@BindPresenter(presenter = LivePresenter.class)
public class LiveActivity extends MvpActivity<LivePresenter,ILiveActivity> implements ILiveActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        loadComponent();
    }

    private void loadComponent() {
        PublicMessageComponent publicMessageComponent = new PublicMessageComponent();
        VideoComponent videoComponent = new VideoComponent();
        DanmuComponent danmuComponent = new DanmuComponent();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.public_message_view, publicMessageComponent, publicMessageComponent.TAG)
                .replace(R.id.video_view, videoComponent, videoComponent.TAG)
                .replace(R.id.danmu_view , danmuComponent)
                .commitAllowingStateLoss();
    }

}
