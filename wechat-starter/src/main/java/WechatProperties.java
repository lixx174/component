import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jinx
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "component.wechat")
public class WechatProperties {

    private boolean businessCheckEnable = true;
}
