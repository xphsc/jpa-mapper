/*
 * Copyright (c) 2018 huipei.x
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xphsc.jpamapper.core.repository;


import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * {@link JpaRepositoryFactoryBean}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Usage EnableJpaRepositories(basePackages=scan the package, repositoryFactoryBeanClass = DefaultRepositoryFactoryBean.class) Annotation to enable JPA repositories.
 * @since 1.0.0
 */
public class DefaultRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
            extends JpaRepositoryFactoryBean<T, S, ID> {
    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     * @param repositoryInterface must not be {@literal null}.
       @since spring  data  jpa 1.11.0
     */
    public DefaultRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    /**
         * Returns a {@link RepositoryFactorySupport}.
         *
         * @param entityManager
         * @return
         */
        @Override
        protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
            RepositoryFactorySupport factorySupport = new DefaultRepositoryFactory(entityManager);
            return factorySupport;
        }




}
