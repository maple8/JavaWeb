package jdbc.DBUtils_3;

import jdbc.domain.Student;
import jdbc.toolclass.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Maple
 * @date 2019.01.13 21:36
 * <p>
 * 测试DBUtils工具类的增\删\改\查操作
 */
public class TestDBUtils {
    /**
     * 查询所有用户的总个数
     */
    @Test
    public void testCount() {
        try {
            //1.创建核心类QueryRunner的对象
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
            //2.编写sql语句
            String sql = "select count(*) from student";
            //3.执行查询操作,查找单数据,返回Long类型的值.
            Long count = (Long) queryRunner.query(sql, new ScalarHandler());
            System.out.println("数据库中的总记录数: " + count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过sid查询
     */
    @Test
    public void testSelectById() {
        try {
            //1.创建核心类QueryRunner的对象
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
            //2.编写sql语句
            String sql = "select * from student where sid = ?";
            //3.为占位符设置值
            Object[] params = {2};
            //4.执行查询操作
            Student student = queryRunner.query(sql, new BeanHandler<>(Student.class), params);
            //5.处理查询结果,返回的是单条记录
            System.out.println(student.getSid() + ": " + student.getSname());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询指定列的所有记录
     */
    @Test
    public void testSelectAllColumn() {
        try {
            //1.创建核心类QueryRunner的对象
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
            //2.编写sql语句
            String sql = "select * from student";
            //3.执行查询操作
            List<Object> list = queryRunner.query(sql, new ColumnListHandler("sname"));
            //4.对结果集进行处理
            for (Object o : list) {
                System.out.println(o.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有记录,返回Map类型
     */
    @Test
    public void testSelectAllMap() {
        try {
            //1.创建核心类QueryRunner的对象
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
            //2.编写sql语句
            String sql = "select * from student";
            //3.执行查询操作,
            List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler());
            //4.对结果集进行处理,遍历
            for (Map<String, Object> stringObjectMap : list) {
                System.out.println(stringObjectMap.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有记录
     */
    @Test
    public void testSelectAll() {
        try {
            //1.创建核心类QueryRunner的对象
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
            //2.编写sql语句
            String sql = "select * from student";
            //3.执行查询操作,将从数据表中查询的数据对应到javabean类中
            List<Student> students = queryRunner.query(sql, new BeanListHandler<>(Student.class));
            //4.对结果集进行处理,遍历
            for (Student student : students) {
                System.out.println(student.getSid() + ": " + student.getSname());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testUpdate() {
        //1.创建核心类QueryRunner对象
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
        //2.编写sql语句
        String sql = "update student set sname = ? where sid = ?";
        //3.为占位符设置值,通过Object对象数组设定
        Object[] params = {"George", 5};
        try {
            //4.执行sql语句,返回受影响的语句的数量
            int rows = queryRunner.update(sql, params);
            if (rows > 0) {
                System.out.println("修改成功!");
            } else {
                System.out.println("修改失败!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete() {
        //1.创建核心类QueryRunner对象
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
        //2.编写sql语句
        String sql = "delete from student where sid = ?";
        //3.为占位符设置值,通过Object对象数组设定
        Object[] params = {7};
        try {
            //4.执行sql语句,返回受影响的语句的数量
            int rows = queryRunner.update(sql, params);
            if (rows > 0) {
                System.out.println("删除成功!");
            } else {
                System.out.println("删除失败!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertInto() {
        //1.创建核心类QueryRunner对象
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
        //2.编写sql语句
        String sql = "insert into student values (9,?,?)";
        //3.为占位符设置值,通过Object对象数组设定,数组形式
        Object[] params = {"Irving", 1};
        try {
            //4.执行sql语句,返回受影响的语句的数量
            int rows = queryRunner.update(sql, params);
            if (rows > 0) {
                System.out.println("插入成功!");
            } else {
                System.out.println("插入失败!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
