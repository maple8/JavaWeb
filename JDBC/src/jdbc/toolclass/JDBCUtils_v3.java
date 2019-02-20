package jdbc.toolclass;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Maple
 * @date 2019.01.12 19:30
 * 另外一种通过配置文件设置JDBC配置信息的方法
 */
public class JDBCUtils_v3 {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    /**
     * 静态代码块加载配置文件信息
     */
    static {
        try {
            //1.通过当前类获取类加载器,固定的写法
            ClassLoader classLoader = JDBCUtils_v3.class.getClassLoader();
            //2.通过类加载器的方法获取一个配置文件的输入流(此处的配置文件必须添加后缀名)
            InputStream inputStream = classLoader.getResourceAsStream("db.properties");
            //3.创建一个properties对象
            Properties properties = new Properties();
            //4.加载输入流
            properties.load(inputStream);
            //5.从属性对象中获取配置文件中的参数
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     */
    public static Connection getConnection() {
        //作用域的问题,可以先向创建一个对象,并初始化为null
        Connection connection = null;
        try {
            //1.注册驱动
            Class.forName(driver);
            //2.创建连接
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 释放资源方法
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
