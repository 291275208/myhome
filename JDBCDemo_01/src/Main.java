import com.mysql.cj.jdbc.Driver;

import java.sql.*;

/**
 * Description:
 *
 * @Author: 领风
 * @Create: ${DATE} -  ${TIME}
 * @Version: V1.0
 */
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("Hello world!");
//        DriverManager.registerDriver(new Driver());
        Class.forName("com.mysql.jdbc.Driver");
        //获取数据库连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigudb", "root", "1234");
        //创建statement
        Statement statement = connection.createStatement();
        String sql = "select  * from mycustomer ";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String dascri = resultSet.getString("discri");
            System.out.println(id+name+dascri);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
}