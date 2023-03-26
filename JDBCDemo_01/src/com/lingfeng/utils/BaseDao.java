package com.lingfeng.utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:  封装dao层数据库crud重复代码
 * 实现： 只需要提供两个方法
 *               一个方法简化DQL代码
 *               一个方法简化非DQL代码
 * @Author: 领风
 * @Create: 2023/2/10 -  22:01
 * @Version: V1.0
 */
public abstract class BaseDao {

    /**
    * @Description :封装非DQL
    * @Date 22:05 2023/2/10
    * @Param   sql   带占位符的sql语句
    * @Param   params  占位符的值
    * @return  int  返回执行sql的影响行数
    */
    public int executeUpdate(String sql ,Object...params) throws SQLException {

        Connection connection = JDBCUtilsV2.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i<=params.length;i++){
            //按照传入带占位符的sql中的占位符顺序设置占位符的值
            preparedStatement.setObject(i,params[i-1]);
        }
        int rows = preparedStatement.executeUpdate();
        preparedStatement.close();
        //是否回收连接需要考虑是不是事务操作
        //如果为true就不是事务操作
        if (connection.getAutoCommit()){
            //不是事务直接正常回收连接
            JDBCUtilsV2.freeConnection();
        }
        //如果为false则为开启了事务操作，不要管连接即可，给业务层管理
        return rows;
    }

    /**
    * @Description : 将查询结果封装到实体对象中
     * 声明泛型<T>的好处是确定实体对象的类型 User.class T = User 以及可以利用反射给属性赋值
    * @Date 20:35 2023/2/11
    * @Param   clazz 要接值的实体类的模板对象
    * @Param    sql 查询语句  通过数据库列名或者别名反射获取属性名所以列名或别名必须和属性名一致
    * @Param    params  占位符的值要和？位置对应
    * @Param    <T> 泛型声明返回结果的类型
    * @return     查询结果的实体对象集合
    */
    public <T> List<T> executeQuery(Class<T> clazz,String sql,Object...params) throws Exception {

        //注册驱动和获取连接两步
        Connection connection = JDBCUtilsV2.getConnection();
        // 3.编写sql
        // 4.创建preparestatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 5.占位符赋值
        if (params!= null&&params.length!=0){
            for (int i =1; i<=params.length;i++){
                preparedStatement.setObject(i,params[i-1]);
            }
        }
        // 6.执行sql语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //7.结果集解析
        List<T> list = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Map map = new HashMap();
        while(resultSet.next()){
            T t = clazz.newInstance();
            for (int i = 1 ; i <= columnCount ; i ++){
                //获取当前行的第i列的值
                Object value = resultSet.getObject(i);
                //获取当前行的第i列的列名，getColumnLable()根据遍历的下角标获取列明（获取别名，如果没有别名就直接获取列明），getColumnName()只会获取列名
                String columnLabel = metaData.getColumnLabel(i);
                //反射获取属性名并赋值
                Field field = clazz.getDeclaredField(columnLabel);
                //设置属性可以设置，打破private修饰限制
                field.setAccessible(true);
                //属性赋值
                field.set(t,value);
            }
            //将当前行的数据封装到list集合
            list.add(t);
        }
        resultSet.close();
        preparedStatement.close();
        if (connection.getAutoCommit()){
            //没有事务，可以关闭资源
            JDBCUtilsV2.freeConnection();
        }
        return list;
    }

}
