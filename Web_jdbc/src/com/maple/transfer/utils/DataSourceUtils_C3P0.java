package com.maple.transfer.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Maple
 * @date 2019.01.13 14:11
 * c3p0 工具类: 从c3p0-config.xml配置文件中获取JDBC的配置信息,并将数据库的操作进行方法的封装
 */
public class DataSourceUtils_C3P0 {
    //创建连接池
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource("maple");
    //创建一个ThreadLocal(底层是Map集合)
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 获取连接池对象
     *
     * @return
     */
    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 获取连接对象
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            //异常的时候也要有返回值,返回一个异常对象
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当期线程中绑定的connection,保证执行sql的connection对象与开启事务的connnection对象是同一个
     *
     * @return
     */
    public static Connection getCurrentConnection() {
        //从threadLocal中寻找当前线程是否有对应的Connection,
        //有就直接获取
        Connection connection = threadLocal.get();
        if (connection == null) {
            //没有就创建一个新的连接对象
            connection = getConnection();
            //将connection资源绑定到ThreadLocal（Map）上
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
     * 开启事务
     */
    public static void startTransaction() throws SQLException {
        Connection connection = getCurrentConnection();
        connection.setAutoCommit(false);
    }

    /**
     * 回滚事务
     */
    public static void rollback() throws SQLException {
        Connection connection = getCurrentConnection();
        connection.rollback();
    }

    /**
     * 提交事务
     */
    public static void commit() throws SQLException {
        Connection connection = getCurrentConnection();
        connection.commit();
        //表明该事务结束了，将connection从ThreadLocal中移出
        threadLocal.remove();
        connection.close();
    }
}
