package me.hutcwp.app.dynamic.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.hutcwp.app.dynamic.R;
import me.hutcwp.app.dynamic.base.BaseActivity;
import me.hutcwp.app.dynamic.bean.Topic;
import me.hutcwp.app.dynamic.bean.User;
import me.hutcwp.app.dynamic.databinding.ActivityPublishDynamicBinding;
import me.hutcwp.app.dynamic.model.TopicModelImp;
import me.hutcwp.app.dynamic.util.CommentUtil;
import me.hutcwp.app.dynamic.util.LogUtil;
import me.hutcwp.app.dynamic.util.UserUtil;

public class PublishDynamicActivity extends BaseActivity {

    private static final String TAG = "PublishDynamicActivity";
    private ActivityPublishDynamicBinding mBinding;

    private static final int IMAGE_REQUEST_CODE = 1;

    private List<String> imgList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_dynamic;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mBinding = (ActivityPublishDynamicBinding) getBinding();
        mBinding.ibAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在这里跳转到手机系统相册里面
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });

        mBinding.btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.D(TAG, "图片集合imglist:" + imgList2string());
                LogUtil.D(TAG, "系统当前时间 " + CommentUtil.getCurTime());

                int fromId = UserUtil.getCurUser().getId(); //发布者的id
                String date = CommentUtil.getCurTime() + ""; //发布时间
                String likes = "";  //点赞的人
                String photos = imgList2string(); //图片路径
                String content = mBinding.etContent.getText().toString().trim(); //话题内容
                User user = UserUtil.getCurUser(); //发布者

                if (TextUtils.isEmpty(content)) {
                    toast("内容不能为空!");
                    return;
                }

                LogUtil.D("test", "insert topic -> fromId" + fromId);
                Topic topic = new Topic(fromId, date, likes, photos, content);
                if (TopicModelImp.getInstance().publishTopic(topic)) {
                    toast("话题发布成功!");
                    PublishDynamicActivity.this.finish();
                } else {
                    toast("话题发布失败!");
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //在相册里面选择好相片之后调回到现在的这个activity中
        switch (requestCode) {
            case IMAGE_REQUEST_CODE: //返回到那个Activity的标志
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null); //从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        LogUtil.D(TAG, "刚刚获取到的图片URI为：selectedImage -> " + selectedImage);
                        LogUtil.D(TAG, "刚刚获取到的图片地址为：path -> " + path);
                        imgList.add(path);
                        mBinding.mibPhoto.setList(imgList);
                        LogUtil.D(TAG, "imgList.size() :" + imgList.size());
                        LogUtil.D(TAG, "mibPhoto==null?" + (mBinding.mibPhoto == null));

                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 将imgList转换为string字符串
     *
     * @return string字符串
     */
    public String imgList2string() {
        StringBuilder sb = new StringBuilder();
        for (String s : imgList) {
            sb.append(s).append(",");
        }
        return sb.toString();
    }


}
