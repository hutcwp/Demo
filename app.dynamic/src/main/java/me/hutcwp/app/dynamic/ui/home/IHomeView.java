package me.hutcwp.app.dynamic.ui.home;

import me.hutcwp.app.dynamic.mvp.IBaseView;

/**
 * Created by Administrator on 2018/1/19.
 */

public interface IHomeView extends IBaseView {

    void showTopics();

    void showComment();

    void showReply();

    void showlikes();

    void updateTopics();

    void updateComment();

    void updateReply();

    void updatelikes();

    void showNullLayout();

}
