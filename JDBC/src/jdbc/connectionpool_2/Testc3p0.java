package jdbc.connectionpool_2;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import jdbc.toolclass.C3P0Utils;
import jdbc.toolclass.JDBCUtils_v3;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Maple
 * @date 2019.01.13 13:55
 */
public class Testc3p0 {
    @Test
    public void testInsertTo() {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            //从连接池中获取连接
            conn = C3P0Utils.getConnection();
            String sql = "insert into student values (8,?,?)";
            statement = conn.prepareStatement(sql);
            statement.setString(1, "Summer2");
            statement.setInt(2, 2);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("插入成功!");
            } else {
                System.out.println("插入失败!");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            //将连接对象归还给连接池
            JDBCUtils_v3.release(conn, statement, null);
        }
    }


    @Test
    public void testInsertToEnhance() {
        Connection conn = null;
        PreparedStatement statement = null;
        //1.创建自定义连接池对象
        ComboPooledDataSource dataSource = new ComboPooledDataSource(); //加载默认的配置
//        ComboPooledDataSource dataSource1 = new ComboPooledDataSource("maple");//加载有名称的配置
        try {
            //从连接池中获取连接
            conn = dataSource.getConnection();
            String sql = "insert into student values (7,?,?)";
            statement = conn.prepareStatement(sql);
            statement.setString(1, "Summer1");
            statement.setInt(2, 1);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("插入成功!");
            } else {
                System.out.println("插入失败!");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            JDBCUtils_v3.release(conn, statement, null);
        }
    }
}
