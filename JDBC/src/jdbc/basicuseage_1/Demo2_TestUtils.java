package jdbc.basicuseage_1;

import jdbc.toolclass.JDBCUtils_v1;
import jdbc.toolclass.JDBCUtils_v2;
import jdbc.toolclass.JDBCUtils_v3;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 测试工具类
 *
 * @author Maple
 * @date 2019.01.12 19:46
 */
public class Demo2_TestUtils {

    /**
     * 更新记录方法
     * 使用配置文件获取JDBC配置信息的规范化写法
     */
    @Test
    public void Update() {
        //更新记录就不需要结果集对象,只有连接对象和sql语句执行对象
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            //1.获取连接
            conn = JDBCUtils_v3.getConnection();
            //2.编写sql语句
            String sql = "update student set gid =? where sid=?";
            //3.获取slq语句执行对象
            statement = conn.prepareStatement(sql);
            //4.设置参数
            statement.setInt(1, 1);
            statement.setInt(2, 3);
            //5.执行语句操作,返回被操作的语句的数量
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("更新记录成功!");
            } else {
                System.out.println("更新记录失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils_v3.release(conn, statement, null);
        }
    }

    /**
     * 删除记录方法
     * 使用配置文件获取JDBC配置信息的规范化写法
     */
    @Test
    public void DeleteById() {
        //删除记录就不需要结果集对象,只有连接对象和sql语句执行对象
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            //1.获取连接
            conn = JDBCUtils_v3.getConnection();
            //2.编写sql语句
            String sql = "delete  from student where sid =?";
            //3.获取slq语句执行对象
            statement = conn.prepareStatement(sql);
            //4.设置参数
            statement.setInt(1, 5);
            //5.执行语句操作,返回被操作的语句的数量
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("删除记录成功!");
            } else {
                System.out.println("删除记录失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils_v3.release(conn, statement, null);
        }
    }

    /**
     * 插入记录方法
     * 使用配置文件的规范化写法
     */
    @Test
    public void testInsertInto() {
        //插入记录就不需要结果集对象,只有连接对象和sql语句执行对象
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            //1.获取连接
            conn = JDBCUtils_v2.getConnection();
            //2.编写sql语句
            String sql = "insert into student values (5,?,?)";
            //3.获取slq语句执行对象
            statement = conn.prepareStatement(sql);
            //4.设置参数
            statement.setString(1, "Maple");
            statement.setInt(2, 2);
            //5.执行语句操作,返回执行操作后受影响的sql语句的数量
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("插入记录成功!");
            } else {
                System.out.println("插入记录失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            JDBCUtils_v2.release(conn, statement, null);
        }
    }

    /**
     * 查询信息方法
     * 使用工具类的规范化写法
     */
    @Test
    public void testFindUserById() {
        //因为try...catch...会导致作用域的约束问题,所以在其之前就创建内部需要使用的对象
        //分别创建连接对象,sql语句执行对象,结果集对象(如果不是查询操作就不用)
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            //1.获取连接,使用工具类中的静态方法
            conn = JDBCUtils_v1.getConnection();
            //2.编写sql语句
            String sql = "select * from student where sid= ?";
            //3.获取执行sql语句对象
            statement = conn.prepareStatement(sql);
            //4.设置参数
            statement.setInt(1, 2);
            //5.执行sql查询语句
            rs = statement.executeQuery();
            //6.处理结果集
            while (rs.next()) {
                System.out.println(rs.getString("sname") + " --- " + rs.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //7.释放资源,不论是否发生异常,都要释放资源,所以放在finally中.
            JDBCUtils_v1.release(conn, statement, rs);
        }
    }
}
