package jdbc.connectionpool_2;

import jdbc.toolclass.JDBCUtils_v3;
import jdbc.toolclass.MyDataSource;
import jdbc.toolclass.MyDataSourceEnhanced;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Maple
 * @date 2019.01.13 10:12
 * <p>
 * 测试自定义连接池
 */
public class TestMyDataSource {
    @Test
    public void testInsertToEnhance() {
        Connection conn = null;
        PreparedStatement statement = null;
        //1.创建自定义连接池对象
        DataSource dataSource = new MyDataSourceEnhanced();
        try {
            //2.从连接池中获取连接
            conn = dataSource.getConnection();
            //3.编写sql语句
            String sql = "insert into student values (6,?,?)";
            //4.获取执行sql语句的对象
            statement = conn.prepareStatement(sql);
            //5.设置sql语句
            statement.setString(1, "Summer");
            statement.setInt(2, 2);
            //6.执行sql语句
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("插入成功!");
            } else {
                System.out.println("插入失败!");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            //7.增强的方法就是这里的内部的close()方法,是将使用完之后的连接对象归还连接池而不是关闭
            JDBCUtils_v3.release(conn, statement, null);
        }
    }


    @Test
    public void testInsertTo() {
        Connection conn = null;
        PreparedStatement statement = null;
        //1.创建自定义连接池对象
        MyDataSource dataSource = new MyDataSource();
        try {
            //2.从连接池中获取连接
            conn = dataSource.getConnection();
            //3.编写sql语句
            String sql = "insert into student values (5,?,?)";
            //4.获取sql语句执行对象
            statement = conn.prepareStatement(sql);
            //5.设置占位符
            statement.setString(1, "Maple");
            statement.setInt(2, 1);
            //6.执行插入语句,返回受影响的语句的个数
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("插入成功!");
            } else {
                System.out.println("插入失败!");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            //7.将用完的连接对象归还给连接池
            dataSource.closeConnection(conn);
        }
    }
}
