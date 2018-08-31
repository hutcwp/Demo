package me.hutcwp.app.dynamic.bean;


/**
 * Created by Administrator on 2018/1/15.
 */

public class Comment {

    protected int id; // id
    private int topicId; //话题的id
    protected int fromUId; //发表回复（评论）用户的id
    protected String content; //评论内容
    protected String photos;  //评论图片
    protected String date;  //评论时间

    public Comment(){

    }

    public Comment(int topicId , int fromUId, String content, String photos, String date) {
        this.topicId = topicId;
        this.fromUId = fromUId;
        this.content = content;
        this.photos = photos;
        this.date = date;
    }

    public Comment(int id, int topicId, int fromUId, String content, String photos, String date) {
        this.id = id;
        this.topicId = topicId;
        this.fromUId = fromUId;
        this.content = content;
        this.photos = photos;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getFromUId() {
        return fromUId;
    }

    public void setFromUId(int fromUId) {
        this.fromUId = fromUId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
