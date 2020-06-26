import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author 张鹏
 * @date 2020/6/21 18:52
 */
public class ProviderApplication {

    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("spring-provider.xml");
        System.in.read();
    }
}
