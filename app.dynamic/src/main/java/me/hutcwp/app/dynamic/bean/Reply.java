package me.hutcwp.app.dynamic.bean;

/**
 * Created by Administrator on 2018/1/12.
 */

public class Reply extends Comment {

    private int commentId; //回复评论id
    private int toUId; //被回复的用户

    public Reply(){

    }

    public Reply(int id, int topicId, int commentId, int fromUId, int toUId, String content, String photo, String date) {
        super(id, topicId, fromUId, content, photo, date);
        this.commentId = commentId;
        this.toUId = toUId;
    }

    public Reply(int topicId, int commentId, int fromUId, int toUId, String content, String photo, String date) {
        super(topicId, fromUId, content, photo, date);
        this.commentId = commentId;
        this.toUId = toUId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getToUId() {
        return toUId;
    }

    public void setToUId(int toUId) {
        this.toUId = toUId;
    }
}
