package com.component.demo;

import com.component.wechat.core.Client;
import com.component.wechat.model.AccessRequest;
import com.component.wechat.model.AccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author jinx
 */
@Component
@RequiredArgsConstructor
public class WechatDemo implements InitializingBean {

    final Client client;

    @Override
    public void afterPropertiesSet() throws Exception {
        for (int i = 0; i < 2; i++) {
            AccessResponse response = client.access(
                    new AccessRequest("wx2e5dd033b02ef757", "41fc45c9be70dff8b07b1c618896b526")
            );
            System.out.println(response.success());

            TimeUnit.SECONDS.sleep(10);
        }
    }
}
