package com.hutcwp.livebiz.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hutcwp.livebiz.R;
import com.hutcwp.livebiz.ui.component.danmu.DanmuComponent;
import com.hutcwp.livebiz.ui.component.publicmessage.PublicMessageComponent;
import com.hutcwp.livebiz.ui.component.video.VideoComponent;

import net.wequick.small.Small;

import hut.cwp.annotations.InitAttrConfig;
import hut.cwp.annotations.InitAttrConfigs;
import hut.cwp.api.Injector;
import hut.cwp.mvp.BindPresenter;
import hut.cwp.mvp.MvpActivity;


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
        Injector.injectContainer(this);

    }
}


