package jdbc.toolclass;

import jdbc.toolclass.JDBCUtils_v3;
import jdbc.toolclass.MyConnection;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * @author Maple
 * @date 2019.01.13 09:17
 * 自定义连接池(增强方法)
 * <p>
 * 实际开发中"获取连接"或"释放资源"是非常消耗系统资源的两个过程,为解决此类性能问题,通常情况我们采用连接池技术,来共享连接Connection.
 * 常见连接池:DBCP(使用率10%~15%), C3P0(使用率80%), Tomcat自带连接池(使用率5%左右)
 */
public class MyDataSourceEnhanced implements DataSource {
    //1.创建一个容器用于存储Connection对象
    private static LinkedList<Connection> pool = new LinkedList<>();

    //2.创建五个连接对象放到容器中
    static {
        for (int i = 0; i < 5; i++) {
            Connection connection = JDBCUtils_v3.getConnection();
            //3.创建装饰类对象,增强close()方法
            MyConnection myConnection = new MyConnection(connection, pool);
            pool.add(myConnection);
        }
    }


    /**
     * 重写获取连接的方法
     */
    @Override
    public Connection getConnection() throws SQLException {
        //使用之前先判断
        if (pool.isEmpty()) {
            //连接池中如果没有对象就创建对象
            for (int i = 0; i < 5; i++) {
                Connection connection = JDBCUtils_v3.getConnection();
                //放入池子中的Connection增强对象
                MyConnection myConnection = new MyConnection(connection, pool);
                pool.add(myConnection);
            }
        }
        //从池子中获取一个连接对象
        return pool.removeFirst();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

}
