package jdbc.toolclass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Maple
 * @date 2019.01.12 19:30
 * <p>
 * 使用工具类规范代码: 连接和释放资源的操作是每个JDBC操作都要使用的,而这两部分代码又是固定的,所有创建为工具类的静态方法,增强代码的复用性.
 * <p>
 * JDBC工具类, 工具类是直接使用的, 所以工具类中出现的方法都是使用异常捕获, 而不是选择抛出异常, 因为抛出的异常, 在使用该方法的父类
 * 方法中还是需要对其进行处理.
 * 不过这种创建单独的类的用法还不是最方便的,实际开发的时候推荐使用创建配置文件,虽然工具类已经很方便了,但是如果有更换数据库的需求的时候就不是
 * 很方便了,还是需要修改源码.此时使用配置文件的优势有凸显出来了,不用修改源码,只修改配置文件即可.
 */
public class JDBCUtils_v1 {
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/chapter4?characterEncoding=utf8&useSSL=true&useUnicode=true";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "nba106118";

    /**
     * 封装获取连接的方法
     */
    public static Connection getConnection() {
        //作用域的问题(在try...catch...代码块内部的变量,外部无法访问),可以先创建一个对象,并初始化为null
        Connection connection = null;
        try {
            //1.注册驱动
            Class.forName(DRIVER_NAME);
            //2.建立连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 释放资源方法
     * 先获取的后释放,后获取的先释放
     *
     * @param connection:        连接对象
     * @param preparedStatement: sql执行语句对象
     * @param resultSet:         查询结果集对象
     */
    public static void release(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }
}
