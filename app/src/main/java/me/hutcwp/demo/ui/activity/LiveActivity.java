package me.hutcwp.demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.hutcwp.apt_lib.annotation.InitAttrConfig;
import me.hutcwp.apt_lib.annotation.InitAttrConfigs;
import me.hutcwp.demo.R;
import me.hutcwp.demo.base.mvp.BindPresenter;
import me.hutcwp.demo.base.mvp.MvpActivity;
import me.hutcwp.demo.ui.component.danmu.DanmuComponent;
import me.hutcwp.demo.ui.component.publicmessage.PublicMessageComponent;
import me.hutcwp.demo.ui.component.video.VideoComponent;

@InitAttrConfigs({
        @InitAttrConfig(component = PublicMessageComponent.class, resourceId = R.id.public_message_view),
        @InitAttrConfig(component = VideoComponent.class, resourceId = R.id.video_view),
        @InitAttrConfig(component = DanmuComponent.class, resourceId = R.id.danmu_view)
})
@BindPresenter(presenter = LivePresenter.class)
public class LiveActivity extends MvpActivity<LivePresenter, ILiveActivity> implements ILiveActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
    }

}


