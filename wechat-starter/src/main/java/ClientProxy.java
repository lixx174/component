import com.component.wechat.WechatConfiguration;
import com.component.wechat.core.Client;
import com.component.wechat.core.DefaultClient;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.HttpClients;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 微信client{@link Client}的代理
 * 每次调用前使用新的{@link DefaultClient}防止{@link org.apache.http.client.HttpClient}阻塞
 *
 * @author jinx
 */
@RequiredArgsConstructor
public class ClientProxy implements InvocationHandler {

    final WechatProperties properties;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Client.class == method.getDeclaringClass()) {
            WechatConfiguration wechatConfiguration = WechatConfiguration.builder()
                    .businessCheckEnable(properties.isBusinessCheckEnable())
                    .build();

            // 每次使用新的httpClient  可防止阻塞
            return method.invoke(new DefaultClient(HttpClients.createDefault(), wechatConfiguration), args);
        }

        return method.invoke(this, args);
    }
}
