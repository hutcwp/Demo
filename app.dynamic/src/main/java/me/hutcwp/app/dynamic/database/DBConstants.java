package me.hutcwp.app.dynamic.database;

/**
 * Created by Administrator on 2018/1/15.
 */

public class DBConstants {

    public final static String DATABASE_NAME = "db2018";
    public final static int DATABASE_VERSION = 1;
    public final static String TABLE_USER = "User";
    public final static String TABLE_TOPIC = "topic";
    public final static String TABLE_COMMENT = "comment";

    public static final String CREATE_TABLE_USER = "create table user (  " +
            "id integer primary key autoincrement ," +
            "phone text not null unique," +
            "username text," +
            "password text );";

    public static final String CREATE_TABLE_TOPIC = "create table topic (  " +
            "id integer primary key autoincrement ," +
            "content text not null," +
            "likes text," +
            "photos text," +
            "date text not null ," +
            "fromuid integer not null);";

    public static final String CREATE_USER_COMMENT = "create table comment (  " +
            "id integer primary key autoincrement ," +
            "topicid int ," +
            "commentid int ," +
            "fromuid int ," +
            "touid int ," +
            "content text ," +
            "photos text ," +
            "date text );";

}
