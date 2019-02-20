package jdbc.basicuseage_1;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Maple
 * @date 2019.01.09 10:30
 */
public class Demo1_LogInDB {
    //mysql驱动包名
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    //数据库连接地址url
    private static final String URL = "jdbc:mysql://localhost:3306/chapter4?characterEncoding=utf8&useSSL=true";
    //用户名
    private static final String USER_NAME = "root";
    //密码
    private static final String PASSWORD = "nba106118";

    /**
     * 连接数据库
     * 没有防止sql注入方法: 不推荐使用
     * String sql = "select * from student where sname = '" + sname + "';";
     * Statement statement = connection.createStatement();
     * ResultSet resultSet = statement.executeQuery(sql);
     * <p>
     * sql注入会在sql语句中有输入参数的时候会发生,结果就是通过输入一些数据库中没有的数据却还能登录数据库,并且能够查询所有的数据.在实际开发中
     * 这是意见很严重的问题.
     */
    public void LogIn1(String sname) throws ClassNotFoundException, SQLException {
        //1.注册驱动
        Class.forName(DRIVER_NAME);
        //2.获取连接
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        //3.创建执行sql语句的对象,此方法会导致sql注入问题,所以不推荐使用,Statement表示原始语句类
        Statement statement = connection.createStatement();
        //4.写一个sql语句
        String sql = "select * from student where sname = '" + sname + "';";
        //5.执行sql语句,返回查询结果集
        ResultSet resultSet = statement.executeQuery(sql);
        //6.对结果集进行处理,ResultSet的光标一开始位于第一行之前,所以使用resultSet.next()判断的是当前行是否有内容,而不是下一行.
        if (resultSet.next()) {
            String name = resultSet.getString("sname");
            int index = resultSet.getInt("sid");
            System.out.println("Welcome！connect successful.");
            System.out.println(sql);
            System.out.println(name);
            System.out.println(index);
        } else {
            System.out.println("connect failed! the sname id not exist.");
        }

        //7.释放资源,先创建的后关闭
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }

    /**
     * 连接数据库
     * 防止sql注入方法: 实际开发中都使用这种方法
     * String sql = "select * from student where sname=?";
     * PreparedStatement preparedStatement = connection.prepareStatement(sql);
     * preparedStatement.setString(1, sname);
     * ResultSet resultSet = preparedStatement.executeQuery();
     */
    public void LogIn2(String sname) throws ClassNotFoundException, SQLException {
        //1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.建立连接
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        //3.编写sql,参数先使用占位符占位,后面再进行替换
        String sql = "select * from student where sname= ?";
        //4.创建预处理对象,此方法可以避免sql注入的问题,推荐使用
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //5.给sql语句中的占位符设置参数
        preparedStatement.setString(1, sname);
        //6.执行sql对象
        ResultSet resultSet = preparedStatement.executeQuery();
        //7.对查询的结果集进行处理
        if (resultSet.next()) {
            System.out.println("Welcome！load successful.");
            System.out.println(sql);

        } else {
            System.out.println("load failed! username or password is wrong.");
        }
        //8.释放资源
        if (resultSet != null) resultSet.close();
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
    }

    /**
     * 这里需要注意,查询的时候也不区分大小写,按道理是不应该的,虽然MySQL语句是不区分大小写,但是存入数据库中的数据应该是会区分大小写的,但是
     * 这里确实没有区分大小写,否则以下写法应该查不到数据.
     */
    @Test
    public void testLogIn() {
        try {
            //没有防止sql注入方法
//            LogIn1("8Maple8' or '1");
//            LogIn1("8Maple8");

            //防止sql注入方法
//            LogIn2("8Maple8' or '1");
            LogIn2("Maple");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
