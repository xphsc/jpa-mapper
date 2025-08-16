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


import cn.xphsc.jpamapper.core.query.NativeSqlLookupStrategy;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * {@link JpaRepositoryFactory}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  JPA specific generic repository factory.
 * @since 1.0.0
 */
public class DefaultRepositoryFactory<T,ID> extends JpaRepositoryFactory {
    private EntityManager entityManager;
    private final PersistenceProvider extractor;

    public DefaultRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager=entityManager;
        this.extractor = PersistenceProvider.fromEntityManager(entityManager);

    }


    /**
     * 设置具体实现类
     */
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

        if (isBaseRepository(metadata.getRepositoryInterface())) {
            return SimpleRepository.class;

        }
        return super.getRepositoryBaseClass(metadata);
    }

    private static boolean isBaseRepository(Class<?> repositoryInterface) {
        return BaseRepository.class.isAssignableFrom(repositoryInterface);
    }

    @Override
    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(QueryLookupStrategy.Key key, QueryMethodEvaluationContextProvider evaluationContextProvider) {
        return Optional.ofNullable(NativeSqlLookupStrategy.create(entityManager,extractor));
    }

}
