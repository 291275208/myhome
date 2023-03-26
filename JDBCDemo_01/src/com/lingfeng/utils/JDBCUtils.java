package com.lingfeng.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Description: 包含一个连接池对象，对外提供获取连接和回收连接的方法
 *小建议：工具类的方法推荐写成静态方便外部可以不用实例化对象直接调用方法
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
 * @Version: V1.0
 */
public class JDBCUtils {

    private static DataSource dataSource = null;

    //全局实例化一个连接池对象
    static {
        Properties properties = new Properties();
        InputStream resourceAsStream = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
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
        return dataSource.getConnection();
    }

    /**
    * @Description :回收连接，连接池的连接，调用close()就是回收并不一定是关闭连接
    * @Date 21:22 2023/2/10
    */
    public static void  freeConnection(Connection connection) throws SQLException {
        connection.close();
    }

}
