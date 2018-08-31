package me.hutcwp.app.dynamic.bean;

/**
 * Created by Administrator on 2018/1/12.
 */

public class Topic {

    private int id; //id
    private int fromUId; //发布者的id
    private String date; //发布时间
    private String likes;  //点赞的人
    private String photos; //图片路径
    private String content; //话题内容

    public Topic() {
    }

    public Topic(int fromUId, String date, String likes, String photos, String content) {
        this.fromUId = fromUId;
        this.date = date;
        this.likes = likes;
        this.photos = photos;
        this.content = content;
    }

    public Topic(int id, int fromUId, String date, String likes, String photos, String content) {
        this.id = id;
        this.fromUId = fromUId;
        this.date = date;
        this.likes = likes;
        this.photos = photos;
        this.content = content;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
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

    public int getFromUId() {
        return fromUId;
    }

    public void setFromUId(int fromUId) {
        this.fromUId = fromUId;
    }


}
