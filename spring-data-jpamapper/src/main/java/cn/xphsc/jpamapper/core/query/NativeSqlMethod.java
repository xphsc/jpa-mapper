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
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import java.lang.reflect.Method;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: NativeSql specific extension of JpaQueryMethod.
 * @since 2.0.0
 */
public class NativeSqlMethod  extends JpaQueryMethod {
    private Method method;

    /**
     * Creates a {@link JpaQueryMethod}.
     * @param method    must not be {@literal null}
     * @param metadata  must not be {@literal null}
     * @param factory
     * @param extractor must not be {@literal null}
     */
    public NativeSqlMethod(Method method, RepositoryMetadata metadata, ProjectionFactory factory, QueryExtractor extractor) {
        super(method, metadata, factory, extractor);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
