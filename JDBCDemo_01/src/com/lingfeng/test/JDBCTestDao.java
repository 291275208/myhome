package com.lingfeng.test;

import com.lingfeng.bean.User;
import com.lingfeng.utils.BaseDao;
import com.lingfeng.utils.JDBCUtilsV2;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * @Author: 领风
 * @Create: 2023/2/9 -  18:19
 * @Version: V1.0
 */
public class JDBCTestDao extends BaseDao {

    /**
    * @Description :向t_user表中插入一条记录
    * @Date 22:15 2023/2/9
    */
    @Test
    public void testInsert() throws SQLException {
        String sql = "INSERT INTO t_user (account,password,nickname) VALUE(?,?,?)";
        int i = executeUpdate(sql, "333","333","水水水水水");
        JDBCTestDao jdbcTestDao = new JDBCTestDao();
//        System.out.println("i="+i);
    }
    /**
     * @Description :向数据库新增一条记录并回显自增主键
     * 创建statement时候告知带回自增的主键值Statement.RETURN_GENERATED_KEYS默认等于1
     * 获取装自增主键值的结果集 一行一列 结果集移动光标next()，getInt(1)即可  ResultSet resultSet = preparedStatement.getGeneratedKeys();
     * @Date 18:07 2023/2/10
     */
    @Test
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

        Connection connection = JDBCUtilsV2.getConnection();
        String sql = "select * from t_user where user_id = ? ;";
        List<User> list = executeQuery(User.class, sql,9);
        System.out.println("查询结果为："+list.get(0));

    }

}
