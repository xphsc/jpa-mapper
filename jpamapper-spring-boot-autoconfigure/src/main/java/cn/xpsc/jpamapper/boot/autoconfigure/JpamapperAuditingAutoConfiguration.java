package cn.xpsc.jpamapper.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.2.1
 */
@ConditionalOnProperty(
        prefix = JpamapperProperties.JPAMAPPER_PREFIX,
        name = {"auditing"}
)
@Configuration
@EnableJpaAuditing
public class JpamapperAuditingAutoConfiguration {

}
