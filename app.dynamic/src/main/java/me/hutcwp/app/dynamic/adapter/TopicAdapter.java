package me.hutcwp.app.dynamic.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import me.hutcwp.app.dynamic.R;
import me.hutcwp.app.dynamic.base.BaseAdapter;
import me.hutcwp.app.dynamic.base.BaseApplication;
import me.hutcwp.app.dynamic.bean.Comment;
import me.hutcwp.app.dynamic.bean.Topic;
import me.hutcwp.app.dynamic.bean.User;
import me.hutcwp.app.dynamic.databinding.ItemTopicBinding;
import me.hutcwp.app.dynamic.listener.LittleImageButtonClickListener;
import me.hutcwp.app.dynamic.model.TopicModelImp;
import me.hutcwp.app.dynamic.model.UserModelImp;
import me.hutcwp.app.dynamic.util.CommentUtil;
import me.hutcwp.app.dynamic.util.LogUtil;
import me.hutcwp.app.dynamic.util.UserUtil;
import me.hutcwp.app.dynamic.view.CommentPopupWindow;
import me.hutcwp.app.dynamic.view.LikesView;


/**
 * Created by Administrator on 2018/1/16.
 */

public class TopicAdapter extends BaseAdapter<ItemTopicBinding, Topic> {

    private static final String TAG = "TopicAdapter";

    private Context mContext;
    private List<Topic> mTopicList;

    public TopicAdapter(Context context, List<Topic> dataList) {
        super(context, dataList);
        this.mTopicList = dataList;
        this.mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_topic;
    }

    @Override
    public void bindView(BaseAdapter.CommonViewHolder viewHolder, int position) {
        final Topic topic = this.mTopicList.get(position);
        initView(viewHolder, topic, position);
    }

    /**
     * 初始化布局View
     *
     * @param viewHolder
     * @param topic
     */
    private void initView(CommonViewHolder viewHolder, Topic topic, int position) {
        viewHolder.bindView.tvContent.setText(topic.getContent());
        viewHolder.bindView.tvUsername.setText(UserModelImp.getInstance().getUserById(topic.getFromUId()).getUsername());
        //设置多选图片列表的集合
        viewHolder.bindView.mulitIv.setList(CommentUtil.string2strList(topic.getPhotos()));
        initLikeIv(viewHolder, topic, position);
        initLikes(viewHolder, topic, position);
        initCommentList(viewHolder, topic, position);
    }

    /**
     * 初始化点赞人的列表
     *
     * @param viewHolder
     * @param topic
     */
    private void initLikes(CommonViewHolder viewHolder, Topic topic, int position) {
        //设置点赞人列表
        viewHolder.bindView.tvLikes.setList(CommentUtil.string2userList(topic.getLikes()));
        viewHolder.bindView.tvLikes.notifyDataSetChanged();
        viewHolder.bindView.tvLikes.setListener(new LikesView.onItemClickListener() {
            @Override
            public void onItemClick(int position, User user) {
                Toast.makeText(mContext, "点击了名字为" + user.getUsername() + "的用户!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化评论列表
     *
     * @param viewHolder
     */
    private void initCommentList(CommonViewHolder viewHolder, Topic topic, int position) {
        //处理评论列表
        List<Comment> commentList = TopicModelImp.getInstance().getCommentByTopicId(topic.getId());
        CommentAdapter commentAdapter = new CommentAdapter(this.mContext, commentList);
        viewHolder.bindView.rvComment.setAdapter(commentAdapter);

        initCommentIv(commentAdapter, viewHolder, topic, position);
    }

    /**
     * 初始化点赞图标状态
     *
     * @param viewHolder
     * @param topic
     */
    private void initLikeIv(CommonViewHolder viewHolder, final Topic topic, int position) {
        //判断点赞图标是否点亮
        if (topic.getLikes() == null) {
            return;
        }
        if (topic.getLikes().contains("" + UserUtil.getCurUser().getId())) {
            viewHolder.bindView.ibLike.setSelected(true);
        }
        viewHolder.bindView.ibLike.setLittleImageButtonClickListener(new LittleImageButtonClickListener() {
            @Override
            public void onClick(int cur) {
                if (cur == 1) {
                    Toast.makeText(mContext, "点赞", Toast.LENGTH_SHORT).show();
                    TopicModelImp.getInstance().like(UserUtil.getCurUser().getId(), topic.getLikes(), topic.getId());
                } else {
                    Toast.makeText(mContext, "取消点赞", Toast.LENGTH_SHORT).show();
                    TopicModelImp.getInstance().cancelLike(UserUtil.getCurUser().getId(), topic.getLikes(), topic.getId());
                }
            }
        });
    }

    /**
     * 初始化评论小图标状态
     *
     * @param viewHolder
     * @param topic
     */
    private void initCommentIv(final CommentAdapter commentAdapter, final CommonViewHolder viewHolder, final Topic topic, final int position) {
        viewHolder.bindView.ibComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BaseApplication.getContext(), "点击了", Toast.LENGTH_SHORT).show();
                CommentPopupWindow popupWindow = new CommentPopupWindow(
                        mContext, new CommentPopupWindow.CommentSendClick() {
                    @Override
                    public void onSendClick(View v, String result) {
                        if (TextUtils.isEmpty(result)) {
                            return;
                        }
                        LogUtil.D(TAG, "输入的信息为：" + result);
                        Comment comment = new Comment(topic.getId(),
                                UserUtil.getCurUser().getId(),
                                result,
                                "",
                                CommentUtil.getCurTime() + ""
                        );
                        if (TopicModelImp.getInstance().publishComment(comment)) {
                            commentAdapter.addData(comment);
                        }

                        LogUtil.D(TAG, "==============发布话题信息===============");
                        LogUtil.D(TAG, "topId:" + topic.getId() + "content:" + result);
                    }
                });
                popupWindow.showReveal();
            }
        });
    }

}
