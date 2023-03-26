package com.lingfeng.test;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * preparedStatement或statement通用使用
 *
 * @Author: 领风
 * @Create: 2023/2/9 -  18:19
 * @Version: V1.0
 */
public class JDBCTest {

    //1.注册驱动(导入MySQL厂商提供的实现jdbc规范接口的驱动jar包)
    //class.forName("com.jdbc.mysql.driver")或DriverManager.getDriver(new Driver())后者创建了两次驱动浪费了资源不建议使用
    //2.获取连接
    //DriverManager.getConnection();
    //3.编写sql语句
    //4.创建preparedstatement对象
    //connection.createStatement();或connection.preparedStatement(sql);
    //5.占位符赋值
    //preparedStatement.setObject(?的位置从左到右从1开始赋值);
    //6.执行发送sql语句
    //int rows = executQuery() [DQL]
    //int rows = executUpdate() [非DQL]
    //7.结果集解析
    //DQL才需要解析结果集
    //移动光标next(); if(next());while(next());
    // 获取当前列字段值getInt(),getString()...或get(i)i为下标位置
    //获取列的信息getMetadata();ResultSetMetadata对象包含的就是列的信息
    //getColumCount();获取列数量 getColumLable();获取列的名称
    //8.关闭资源
    //result.close();statement.close();connection.close();从里向外关闭资源

    /**
    * @Description :向t_user表中插入一条记录
    * @Date 22:15 2023/2/9
    */
    @Test
    public void testInsert() throws Exception {
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigudb", "root", "1234");
        //3.编写sql
        String sql = "INSERT INTO t_user (account,password,nickname) VALUE(?,?,?)";
        //4.创建preparestatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //5.占位符赋值
        preparedStatement.setObject(1,"test");
        preparedStatement.setObject(2,"test");
        preparedStatement.setObject(3,"测试测试此");
        //6.执行sql语句
        int row = preparedStatement.executeUpdate();
        //7.关闭资源
        preparedStatement.close();;
        connection.close();

        /**
        * @Description :向数据库新增一条记录并回显自增主键
         * 创建statement时候告知带回自增的主键值Statement.RETURN_GENERATED_KEYS默认等于1
         * 获取装自增主键值的结果集 一行一列 结果集移动光标next()，getInt(1)即可  ResultSet resultSet = preparedStatement.getGeneratedKeys();
        * @Date 18:07 2023/2/10
        */
    } @Test
    public void testInsertAndGetKey() throws Exception {
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigudb", "root", "1234");
        //3.编写sql
        String sql = "INSERT INTO t_user (account,password,nickname) VALUE(?,?,?)";
        //4.创建preparestatement 并告知statement带回自增的主键值Statement.RETURN_GENERATED_KEYS
        PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        //5.占位符赋值
        preparedStatement.setObject(1,"test");
        preparedStatement.setObject(2,"test");
        preparedStatement.setObject(3,"测试测试此");
        //6.执行sql语句
        int row = preparedStatement.executeUpdate();
        //获取自增主键值的结果集  一行一列  id= 值
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        int id = resultSet.getInt(1);
        System.out.println("id=" + id);
        //7.关闭资源
        preparedStatement.close();;
        connection.close();

    }

    @Test
    public void testUpdate() throws Exception {

        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigudb","root","1234");
        // 3.编写sql
        String sql = "UPDATE t_user set nickname = ? where user_id = ?";
        // 4.创建preparestatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 5.占位符赋值
        preparedStatement.setObject(1,"修改测试");
        preparedStatement.setObject(2,5);
        // 6.执行sql语句
        int row = preparedStatement.executeUpdate();
        if(row>0){
            System.out.println("成功");
        }
        //7.结果集解析
        //8.关闭资源
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testDelet() throws Exception {

        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigudb","root","1234");
        // 3.编写sql
        String sql = "DELETE FROM t_user  where user_id = ?";
        // 4.创建preparestatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 5.占位符赋值
        preparedStatement.setObject(1,5);
        // 6.执行sql语句
        preparedStatement.executeUpdate();
        //7.结果集解析
        //8.关闭资源
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testSelect() throws Exception {

        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigudb","root","1234");
        // 3.编写sql
        String sql = "SELECT user_id,account,password,nickname FROM t_user";
        // 4.创建preparestatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 5.占位符赋值
        // 6.执行sql语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //7.结果集解析
        List<Map> list = new ArrayList<Map>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Map map = new HashMap();
        while(resultSet.next()){
            for (int i = 1 ; i <= columnCount ; i ++){
                //获取当前行的第i列的值
                Object value = resultSet.getObject(i);
                //获取当前行的第i列的列名，getColumnLable()根据遍历的下角标获取列明（获取别名，如果没有别名就直接获取列明），getColumnName()只会获取列名
                String columnLabel = metaData.getColumnLabel(i);
                //将当前行第i列数据信息放入map集合
                map.put(columnLabel, value);
            }
            //将当前行的数据封装到list集合
            list.add(map);
        }
        System.out.println("list="+list);
        //8.关闭资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

}
