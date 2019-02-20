package jdbc.toolclass;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Maple
 * @date 2019.01.13 14:11
 * c3p0 工具类: 从c3p0-config.xml配置文件中获取JDBC的配置信息.
 */
public class C3P0Utils {
    //1.使用命名配置,获取命名配置中的信息
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource("maple");

    //获得连接池
    public static DataSource getDataSource() {
        return dataSource;
    }

    //3.获取连接
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            //异常的时候也要有返回值,返回一个异常对象
            throw new RuntimeException(e);
        }
    }
}
