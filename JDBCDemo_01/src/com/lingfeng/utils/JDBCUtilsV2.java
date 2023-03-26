package com.lingfeng.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Description: 包含一个连接池对象，对外提供获取连接和回收连接的方法
 * 相对于版本一就是优化了用于事务管理，增加了ThreadLocal<Connection>
 *小建议：工具类的方法推荐写成静态方便外部调用
 * 实现：
 * 属性：
 *         连接池对象，只实例化一次
 *                     单例模式（构造方法中实例化）
 *                     静态代码块实现static{
 *                         全局调用一次
 *                     }
 * 方法：
 *             对外提供获取连接的方法
 *             对外提供回收连接的方法
 *
 * @Author: 领风
 * @Create: 2023/2/10 -  20:50
 * @Version: V2.0
 */
public class JDBCUtilsV2 {

    private static DataSource dataSource = null;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    //全局实例化一个连接池对象
    static {
        Properties properties = new Properties();
        //读取properties文件配置
        InputStream resourceAsStream = JDBCUtilsV2.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            //将文件中的配置加载到Properties中
            properties.load(resourceAsStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
    * @Description :对外提供连接的方法
    * @Date 21:15 2023/2/10
    */
    public static Connection getConnection() throws SQLException {
        //查看线程本地变量中是否存在connection，没有就获取新的，有就直接从线程本地变量中获取
        Connection connection = threadLocal.get();
        //第一次没有
        if(connection == null){
            //连接池获取连接
            connection =  dataSource.getConnection();
            //将连接存入线程本地变量thread Local中
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
    * @Description :回收连接，连接池的连接，调用close()就是回收并不一定是关闭连接
    * @Date 21:22 2023/2/10
    */
    public static void  freeConnection() throws SQLException {
        //获取线程本地变量中的连接
        Connection connection = threadLocal.get();
        if (connection!=null){
            //清空线程本地变量的连接对象
            threadLocal.remove();
            //回归数据库事务状态
            connection.setAutoCommit(true);
            connection.close();
        }
    }

}
