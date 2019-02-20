package jdbc.toolclass;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Maple
 * @date 2019.01.13 14:30
 * DBCP 工具类: 从dbcp.properties配置文件中获取JDBC的配置信息
 */
public class DBCPUtils {
    //创建数据源对象
    private static DataSource dataSource;

    //利用静态代码块获取配置参数
    static {
        try {
            //1.加载配置文件,获取文件流
            InputStream inputStream = DBCPUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");
            //2.创建属性对象并加载输入流
            Properties properties = new Properties();
            properties.load(inputStream);
            //3.使用工具类创建数据源(连接池)
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接池对象
    public static DataSource getDataSource() {
        return dataSource;
    }

    //获取连接
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
