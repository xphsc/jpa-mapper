package cn.xpsc.jpamapper.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 0.1.0
 */
@ConfigurationProperties(
        prefix = JpamapperProperties.JPAMAPPER_PREFIX
)
public class JpamapperProperties {
    public static final String JPAMAPPER_PREFIX = "spring.jpa.mapper";
    private boolean enabled;
    private String  basePackages;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }
}
