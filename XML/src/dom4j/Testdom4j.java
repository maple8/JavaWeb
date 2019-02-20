package dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

/**
 * @author Maple
 * @date 2019.01.14 16:36
 */
public class Testdom4j {
    @Test
    public void testReadWebXML() {
        try {
            //1.获取解析器
            SAXReader saxReader = new SAXReader();
            //2.获取document文档对象
            Document document = saxReader.read("src/dom4j/web.xml");
            //3.获取根元素
            Element rootElement = document.getRootElement();
            //打印根元素名称
            System.out.println(rootElement.getName());
            //打印根元素中的属性值
            System.out.println(rootElement.attributeValue("version"));
            //4.获取根元素下的子元素
            List<Element> childElements = rootElement.elements();
            //5.遍历子元素
            for (Element childElement : childElements) {
                if ("servlet".equals(childElement.getName())) {
                    Element servletName = childElement.element("servlet-name");
                    Element servletClass = childElement.element("servlet-class");
                    System.out.println(servletName.getText());
                    System.out.println(servletClass.getText());
                }
                if ("servlet-mapping".equals(childElement.getName())) {
                    Element servletName = childElement.element("servlet-name");
                    Element urlPattern = childElement.element("url-pattern");
                    System.out.println(servletName.getText());
                    System.out.println(urlPattern.getText());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
