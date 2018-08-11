package me.hutcwp.demo.ui.presenter;

import me.hutcwp.demo.base.mvp.MvpPresenter;
import me.hutcwp.demo.ui.component.IPublicMessageComponent;
import me.hutcwp.demo.ui.component.PublicMessageComponent;

public class PublicMessagePresenter extends MvpPresenter<IPublicMessageComponent> {

    private PublicMessageComponent component;

    public PublicMessagePresenter() {
    }

    @Override
    protected void attachView(IPublicMessageComponent view) {
        super.attachView(view);
        initPresenter();
    }

    private void initPresenter() {
        component = (PublicMessageComponent) getView();
    }

    @Override
    protected void detachView() {
        super.detachView();
    }

    public void performTest() {
        component.showTest();
    }

    public void performLog() {
        component.showLog();
    }

}
