import com.component.wechat.core.Client;
import com.component.wechat.core.DefaultClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

/**
 * @author jinx
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WechatProperties.class)
public class WechatAutoConfiguration {

    @Bean("wechatClientProxy")
    @ConditionalOnClass(DefaultClient.class)
    @ConditionalOnMissingBean(Client.class)
    public Client client(WechatProperties properties) {
        return (Client) Proxy.newProxyInstance(
                Client.class.getClassLoader(),
                new Class[]{Client.class},
                new ClientProxy(properties)
        );
    }
}
