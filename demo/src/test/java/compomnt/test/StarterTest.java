package compomnt.test;

import com.component.wechat.core.Client;
import com.demo.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jinx
 */
@SpringBootTest(classes = Application.class)
public class StarterTest {

    @Autowired
    Client client;


    @Test
    public void test() {
        client.toString();
    }
}
