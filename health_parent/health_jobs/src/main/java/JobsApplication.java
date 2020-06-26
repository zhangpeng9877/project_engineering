import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author 张鹏
 * @date 2020/6/26 19:13
 */
public class JobsApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("spring-jobs.xml");
        System.in.read();
    }
}
