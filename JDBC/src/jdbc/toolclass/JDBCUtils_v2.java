package jdbc.toolclass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Maple
 * @date 2019.01.12 19:30
 * <p>
 * 创建工具类并使用静态方法已经很方便了,但是还是不够,如果需要修改连接的数据库类型,还是需要修改源码,开发者处理一般的原则都是尽量不要修改源码,
 * 实际开发的时候推荐使用创建配置文件, 然后使用ResourceBundle对象获取配置文件中设置的信息
 */
public class JDBCUtils_v2 {
    //由于作用域的问题(在static静态局部代码块中创建的对象,在外部不能访问),所以提前创建
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    /**
     * 静态代码块加载配置文件信息,在类加载的时候就会执行并获取参数
     */
    static {
        //导入配置文件,不能有后缀名,利用ResourceBundle资源束对象获取本地配置文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        //获取配置文件中的参数(注意:配置文件中不能有空格,即使格式上看起来很紧凑也不要添加空格)
        driver = resourceBundle.getString("driver");
        url = resourceBundle.getString("url");
        username = resourceBundle.getString("username");
        password = resourceBundle.getString("password");
    }

    /**
     * 获取连接
     */
    public static Connection getConnection() {
        //作用域的问题,可以先创建一个对象,并初始化为null
        Connection connection = null;
        try {
            //1.注册驱动
            Class.forName(driver);
            //2.获取连接
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
