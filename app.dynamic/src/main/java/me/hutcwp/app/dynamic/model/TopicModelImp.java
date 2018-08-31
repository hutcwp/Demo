package me.hutcwp.app.dynamic.model;

import android.content.ContentValues;

import java.util.List;

import me.hutcwp.app.dynamic.bean.Comment;
import me.hutcwp.app.dynamic.bean.Reply;
import me.hutcwp.app.dynamic.bean.Topic;
import me.hutcwp.app.dynamic.database.DBDao;
import me.hutcwp.app.dynamic.interfaze.ITopicModel;

/**
 * Created by Administrator on 2018/1/15.
 */

public class TopicModelImp implements ITopicModel {

    private static TopicModelImp mInstance;

    private TopicModelImp() {

    }

    public static TopicModelImp getInstance() {
        if (mInstance == null) {
            mInstance = new TopicModelImp();
        }
        return mInstance;
    }

    @Override
    public List<Topic> getTopics() {
        return DBDao.getInstance().findAll(Topic.class);
    }


    @Override
    public List<Reply> getReplyByCommentId(int commentId) {
        return DBDao.getInstance().findAll(Reply.class, "commentid=?", new String[]{"" + commentId});
    }

    @Override
    public List<Comment> getCommentByTopicId(int topicId) {
        return DBDao.getInstance().findAll(Comment.class, "topicid=?", new String[]{"" + topicId});
    }

    @Override
    public boolean publishTopic(Topic topic) {
        return judge(DBDao.getInstance().insert(topic));
    }

    @Override
    public boolean publishComment(Comment comment) {
        return judge(DBDao.getInstance().insert(comment));
    }

    @Override
    public boolean publishReply(Reply reply) {
        return judge(DBDao.getInstance().insert(reply));
    }

    @Override
    public boolean like(int userId, String likeIds, int topicId) {
        likeIds = likeIds + userId + ",";
        return updateLikeStatus(userId, likeIds, topicId);
    }

    @Override
    public boolean cancelLike(int userId, String likeIds, int topicId) {
        likeIds = likeIds.replace(userId + ",", "");
        return updateLikeStatus(userId, likeIds, topicId);
    }

    /**
     * 判断操作是否成功
     *
     * @return 结果
     */
    private boolean judge(long resultCode) {
        if (resultCode > 0) {
            return true;
        }
        return false;
    }

    /**
     * 更新likes的状态
     *
     * @param userId
     * @param likeIds
     * @param topicId
     * @return
     */
    private boolean updateLikeStatus(int userId, String likeIds, int topicId) {
        ContentValues value = new ContentValues();
        value.put("likes", likeIds);
        return judge(DBDao.getInstance().update(Topic.class, "id=?", new String[]{"" + topicId}, value));
    }

}
