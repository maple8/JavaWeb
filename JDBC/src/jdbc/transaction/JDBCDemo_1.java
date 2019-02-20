package jdbc.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Maple
 * @date 2019.01.31 16:12
 */
public class JDBCDemo_1 {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            //1.注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取链接
            connection = DriverManager.getConnection("jdbc:mysql:///tomcat?characterEncoding=utf8&useSSL=true",
                    "root", "nba106118");
            //手动开启事务(关闭自动提交事务)
            connection.setAutoCommit(false);
            //3.获取操作数据库的对象
            Statement statement = connection.createStatement();
            //4.操作数据库
            statement.executeUpdate("insert into account values (null ,'Wuzi',8000)");
            statement.executeUpdate("insert into account values (null ,'maple',7000)");

            //提交事务
            connection.commit();
            //5.关闭资源
            statement.close();
            connection.close();
        } catch (Exception e) {
            //事务回滚
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}