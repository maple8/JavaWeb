package jdbc.connectionpool_2;

import jdbc.toolclass.DBCPUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Maple
 * @date 2019.01.13 14:37
 */
public class TestDBCP {
    @Test
    public void testUpdateUser() {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = DBCPUtils.getConnection();
            String sql = "update student set sname = ? where sid = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, "maple4");
            statement.setInt(2, 4);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("更新成功!");
            } else {
                System.out.println("更新失败!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
