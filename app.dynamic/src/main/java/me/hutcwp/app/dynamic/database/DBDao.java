package me.hutcwp.app.dynamic.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.hutcwp.app.dynamic.base.BaseApplication;

/**
 * Created by Administrator on 2018/1/19.
 */

public class DBDao {

    private DBHelper mDBHelper;

    private DBDao() {
        this.mDBHelper = new DBHelper(BaseApplication.getContext());
    }

    public synchronized static DBDao getInstance() {
        return new DBDao();
    }


    /**
     * 多条件查询
     * columns:要查询的列名，可以是多个，可以为null，表示查询所有列
     *
     * @param claz          需要返回的数据类型字节码对象，通过字节码对象才能通过反射进行下一步操作，不可为null
     * @param selection     查询条件，比如id=? and name=? 可以为null
     * @param selectionArgs 对查询条件赋值，一个问号对应一个值，按顺序 可以为null
     * @param groupBy       与合计函数一起使用
     * @param having        语法have，与合计函数一起使用，可以为null
     * @param orderBy       语法，按xx排序，可以为null
     * @param limit         返回rows数量
     */

    /**
     * 查询出某个表中所有的数据
     *
     * @param claz
     */
    public <T> List<T> findAll(Class<T> claz) {
        return find(claz, null, null, null, null, null, null);
    }

    /**
     * 有查询条件的查询某个表
     *
     * @param claz
     * @return
     */
    public <T> List<T> findAll(Class<T> claz, String selection, String[] selectionArgs) {
        return find(claz, selection, selectionArgs, null, null, null, null);
    }


    public <T> List<T> find(Class<T> claz, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        List<T> list = null;
        // 获取到类名
        String className = claz.getName();
        SQLiteDatabase readableDatabase = mDBHelper.getReadableDatabase();
        // 表名必须为是类名+Table
        String tableName = className.substring(className.lastIndexOf(".") + 1);
        tableName = tableName.toLowerCase();
//        LogUtil.D("hutcwp", "tableName " + tableName);
        if (TextUtils.equals(tableName, "reply")) {
            tableName = "comment";
        }
        // 查询数据
        Cursor query = readableDatabase.query(tableName, null, selection, selectionArgs, groupBy, having, orderBy, limit);
        if (query != null) {
            list = new ArrayList<T>();
            try {
                // 得到字节码对象
                @SuppressWarnings("unchecked")
                Class<T> clz = (Class<T>) Class.forName(className);
                // 根据字节码对象获取到所有set开头的公共方法集合
                List<Method> methodList = parseSetMethodList(clz, "set");

                // 如果得到的set方法集合大于1
                if (methodList != null && methodList.size() > 0) {
                    query.moveToFirst();
                    for (int i = 0; i < query.getCount(); i++) {
                        // 将获取到得数据组装为list集合
//                        LogUtil.D("hutcwp", "methodList.size" + methodList.size());
                        //list.add(initObject(clz, query, methodList));
                        list.add(initObject(clz, query, methodList));
                        query.moveToNext();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (query != null) {
                    query.close();
                }
                if (readableDatabase != null) {
                    readableDatabase.close();
                }
            }
        } else {
            if (readableDatabase != null) {
                readableDatabase.close();
            }
        }
        Log.e("appDao", tableName + "表获取到数据" + list == null ? "0" : list.size() + "条");
        return list;
    }

    /**
     * 根据从数据库获取到得数据封装对象并返回
     *
     * @param clz        字节码对象
     * @param query      数据库游标
     * @param methodList set开头的方法名称集合
     * @return
     */
    private <T> T initObject(Class<T> clz, Cursor query, List<Method> methodList) {
        T obj = null;

        try {
            obj = clz.newInstance();
            // 循环插入clz实例的每一个字段值
            for (Method method : methodList) {
                // 根据方法名获取到参数的名字
                String fieldName = method.getName().substring(4);
                // 截取第三个字符转换为小写，并拼接成字段
                fieldName = (method.getName().charAt(3) + "").toLowerCase(Locale.getDefault()) + fieldName;
                // 得到方法的参数类型
                Class<?>[] parameterTypes = method.getParameterTypes();
                //把属性名转换为小写以对应数据库中的字段名
                fieldName = fieldName.toLowerCase();

                // 如果参数类型数组不为null且大于0时才进行插入数据
                if (parameterTypes != null && !(parameterTypes.length < 1)) {
                    int columnIndex = query.getColumnIndex(fieldName);
//                    LogUtil.D("hutcwp", "参数名 " + fieldName + "---参数类型 " + parameterTypes[0] + " ---参数的值 " + query.getString(columnIndex));
//                    LogUtil.D("hutcwp", "参数名 " + fieldName + "---参数类型 " + parameterTypes[0]);
                    if (parameterTypes[0] == String.class) {
                        method.invoke(obj, query.getString(columnIndex));
                    } else if (parameterTypes[0] == int.class) { //有点坑，必须是int.class 而不是 Integer.class
//                        LogUtil.D("hutcwp", "进入int分支");
                        method.invoke(obj, query.getInt(columnIndex));
                    } else if (parameterTypes[0] == Long.class) {
                        method.invoke(obj, query.getLong(columnIndex));
                    } else if (parameterTypes[0] == Float.class) {
                        method.invoke(obj, query.getFloat(columnIndex));
                    } else if (parameterTypes[0] == Boolean.class) {
                        method.invoke(obj, query.getBlob(columnIndex));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    /**
     * 根据class的字节码对象来反射获取到以set或get开头的公共方法集合
     *
     * @param clz
     * @param methodStartsWith 寻找以什么开头的方法（在这里也就是setXxx()或者getXxx()之类的方法）
     * @return
     */
    private List<Method> parseSetMethodList(Class<?> clz, String methodStartsWith) {
        Method[] methods = clz.getMethods();
        List<Method> list = new ArrayList<Method>();

        for (Method m : methods) {
            if (m.getName().startsWith(methodStartsWith)) {
                // 根据方法名获取到参数的名字
                list.add(m);
            }
        }
        return list;
    }


    /**
     * 插入数据
     *
     * @param obj
     * @return insert 返回插入操作所影响的行数
     */
    public <T> Long insert(T obj) {
        Long insert = 0L;

        if (obj == null) {
            return 0L;
        }

        String className = obj.getClass().getName();
        String tableName = className.substring(className.lastIndexOf(".") + 1);
        tableName = tableName.toLowerCase();

//        LogUtil.D("hutcwp", "tableName:" + tableName);
        if (TextUtils.equals(tableName, "reply")) {
            tableName = "comment";
        }

        SQLiteDatabase writableDatabase = mDBHelper.getWritableDatabase();
        Class<?> c = null;
        try {
            // 根据类名来获取字节码对象
            c = Class.forName(className);
            if (c != null) {
                // 查找出所有以get为开头的方法
                List<Method> methodList = parseSetMethodList(c, "get");
                // 返回插入数据的id
                long number = insert(tableName, obj, methodList, writableDatabase);
                // 说明插入一条数据成功
                if (number != -1) {
                    insert++;
                }
            } else {
                Log.e(this.getClass().getName(), "=============== not found className class =================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
        }
        Log.i("appDao", tableName + "表插入数据" + insert + "条");
        return insert;
    }


    /**
     * 执行具体的插入数据的动作
     *
     * @param tableName  表名
     * @param obj        数据源
     * @param methodList 数据源中的对象类型中以get为开头的方法的集合
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private <T> Long insert(String tableName, T obj, List<Method> methodList, SQLiteDatabase writableDatabase) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Long insert = 0L;

        ContentValues values = new ContentValues();

        for (Method method : methodList) {
            String name = method.getName();
//            LogUtil.D("hutcwp", "arg name " + name);
            // 封装好ContentValues 数据
            if (name.startsWith("get") && !name.startsWith("getClass")) {
                String filedName = method.getName().substring(4);
                filedName = (method.getName().charAt(3) + "").toLowerCase(Locale.getDefault()) + filedName;
//                LogUtil.D("hutcwp", "参数名 " + filedName + "");
                Object methodValue = method.invoke(obj, new Object[]{});
                if (!TextUtils.equals(filedName, "id")) { //过滤点主键id,因为是自增的
                    values.put(filedName, methodValue + "");
                }
            }
        }
        // 插入数据
        insert = writableDatabase.insert(tableName, null, values);
        return insert;
    }


    /**
     * 删除一条数据
     *
     * @param clz       字节码对象
     * @param select
     * @param selectArg
     * @return 操作所影响的行数
     */
    public int delete(Class<?> clz, String select, String[] selectArg) {
        int delete = 0;

        SQLiteDatabase writableDatabase = mDBHelper.getWritableDatabase();
        String tableName = "";
        try {
            tableName = clz.getName().substring(clz.getName().lastIndexOf(".") + 1);

            delete += writableDatabase.delete(tableName, select, selectArg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
        }
        Log.i("AppDao", tableName + "表删除" + delete + "条数据");
        return delete;
    }

    /**
     * 修改数据
     *
     * @param clz       所要操作对象字节码
     * @param select    选择条目的条件
     * @param selectArg 选择条目的值
     * @param values    所要修改的键值对
     * @return 修改所影响的行数
     */
    public int update(Class<?> clz, String select, String[] selectArg, ContentValues values) {
        int update = 0;
        SQLiteDatabase writableDatabase = mDBHelper.getWritableDatabase();
        String tableName = "";
        try {
            tableName = clz.getName().substring(clz.getName().lastIndexOf(".") + 1);
            update += writableDatabase.update(tableName, values, select, selectArg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
        }
        Log.i("AppDao", tableName + "表修改所影响了" + update + "条数据");
        return update;
    }


}
