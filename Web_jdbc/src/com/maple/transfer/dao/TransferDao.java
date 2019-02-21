package com.maple.transfer.dao;

import com.maple.transfer.utils.DataSourceUtils_C3P0;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Maple
 * @date 2019.02.01 11:19
 */
public class TransferDao {

    /**
     * 转出操作
     * 为了保证执行sql语句的connection对象和执行事务的connection对象是同一个对象，不能使用带有参数的QueryRunner()构造函数，
     * 需要手动获取connection对象
     *
     * @param outAccount
     * @param money
     * @throws SQLException
     */
    public void transferOut(String outAccount, double money) throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection connection = DataSourceUtils_C3P0.getCurrentConnection();
        String sql = "update account set money = money - ? where name = ?";
        runner.update(connection, sql, money, outAccount);
    }

    /**
     * 转入操作
     *
     * @param inAccount
     * @param money
     * @throws SQLException
     */
    public void transferIn(String inAccount, double money) throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection connection = DataSourceUtils_C3P0.getCurrentConnection();
        String sql = "update account set money = money + ? where name = ?";
        runner.update(connection, sql, money, inAccount);
    }
}
