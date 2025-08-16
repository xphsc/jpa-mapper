package cn.xpsc.jpamapper.boot.autoconfigure;

import cn.xphsc.jpamapper.core.repository.DefaultRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * {@link EnableJpaRepositories}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 0.1.0
 */
@ConditionalOnProperty(
        prefix = JpamapperProperties.JPAMAPPER_PREFIX,
        name = {"enabled"},
        matchIfMissing = true
)
@Configuration
@EnableJpaRepositories(basePackages = "${spring.jpa.mapper.basePackages:*}",repositoryFactoryBeanClass = DefaultRepositoryFactoryBean.class)
public class JpamapperAutoConfiguration {
}
