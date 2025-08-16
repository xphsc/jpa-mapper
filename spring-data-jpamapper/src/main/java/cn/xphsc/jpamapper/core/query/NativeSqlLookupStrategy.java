/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.jpamapper.core.query;

import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;
import javax.persistence.EntityManager;
import java.lang.reflect.Method;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: NativeSqlLookupStrategy specific extension of QueryLookupStrategy.
 * @since 2.0.0
 */
public class NativeSqlLookupStrategy implements QueryLookupStrategy {
    private EntityManager entityManager;
    private  QueryExtractor extractor;
    public NativeSqlLookupStrategy(EntityManager entityManager, QueryExtractor extractor) {
        this.entityManager=entityManager;
        this.extractor=extractor;
    }
    public static QueryLookupStrategy create(EntityManager entityManager,QueryExtractor extractor) {
        return new NativeSqlLookupStrategy(entityManager, extractor);
    }

    @Override
    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata repositoryMetadata, ProjectionFactory projectionFactory, NamedQueries namedQueries) {
            NativeSqlAnnotationResolver annotationResolver= NativeSqlAnnotationResolver.builder().method(method).domainType(repositoryMetadata.getDomainType()).build();
            return new NativeSqlRepositoryQuery(new NativeSqlMethod(method, repositoryMetadata, projectionFactory, extractor),this.entityManager, annotationResolver);
    }
}
