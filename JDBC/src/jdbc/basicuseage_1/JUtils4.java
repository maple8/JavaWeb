package jdbc.basicuseage_1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Maple
 * @date 2019.01.14 19:28
 * <p>
 * JUtils是一个Java语言的单元测试框架,属于第三方工具,简单的说,可以看成是main()方法,可以在里面调用其他方法.
 */
public class JUtils4 {
    @Test
    public void test() {
        System.out.println("JUtils4测试方法");
    }

    @Before
    public void before() {
        System.out.println("JUtils4测试方法执行之前的方法");
    }

    @After
    public void after() {
        System.out.println("JUtils测试方法执行之后的方法");
    }
}
