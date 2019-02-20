package jdbc.toolclass;

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
 * 自定义连接池,必须要实现数据源接口DataSource
 * <p>
 * 实际开发中"获取连接"或"释放资源"是非常消耗系统资源的两个过程,为解决此类性能问题,通常情况我们采用连接池技术,来共享连接Connection.
 * 连接池就是先创建若干个连接对象放入LinkedList集合中(因为连接对象总是执行删除和增加操作,使用链表更快),需要用的时候就从该集合中获取,
 * 用完之后再将拿走的连接对象添加回来.这样使用就不会频繁的创建连接和释放资源了,可以减少系统资源的消耗.
 * 常见连接池: DBCP(使用率10%~15%), C3P0(使用率80%), Tomcat自带连接池(使用率5%左右)
 */
public class MyDataSource implements DataSource {
    //1.创建一个容器用于存储Connection对象,其实就是连接对象的集合(连接池)
    private static LinkedList<Connection> pool = new LinkedList<>();

    //2.创建五个连接对象放到容器中(可以任意指定初始的连接对象的个数)
    static {
        for (int i = 0; i < 5; i++) {
            //通过配置文件的工具类方法创建连接对象
            Connection connection = JDBCUtils_v3.getConnection();
            //将连接对象添加到容器中
            pool.add(connection);
        }
    }


    /**
     * 重写获取连接的方法
     */
    @Override
    public Connection getConnection() throws SQLException {
        //使用之前先判断集合中是否已经用完
        if (pool.isEmpty()) {
            //容器中的连接对象用完之后就再创建5个
            for (int i = 0; i < 5; i++) {
                Connection connection = JDBCUtils_v3.getConnection();
                pool.add(connection);
            }
        }
        //获取容器中的第一个连接对象
        return pool.removeFirst();
    }

    /**
     * 重写归还连接对象的方法
     */
    public void closeConnection(Connection connection) {
        //将用完之后的连接对象返回到容器中
        pool.add(connection);
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
