package jdbc.transaction;

import jdbc.toolclass.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Maple
 * @date 2019.01.13 14:11
 */
public class DBUtilsDemo_2 {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            //获取连接对象
            connection = C3P0Utils.getConnection();
            //开启事务
            connection.setAutoCommit(false);
            runner.update(connection, "update account set money = 8000 where name = 'Maple'");
            //提交事务
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
