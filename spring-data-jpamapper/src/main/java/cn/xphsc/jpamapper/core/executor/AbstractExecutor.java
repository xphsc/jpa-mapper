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
package cn.xphsc.jpamapper.core.executor;


import cn.xphsc.jpamapper.core.lambda.LambdaSupplier;
import cn.xphsc.jpamapper.core.lambda.Reflections;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;

/**
 * {@link Executor}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Abstract Executor
 * @since 1.0.0
 */
public abstract class AbstractExecutor<T> implements Executor<T> {
    protected final SimpleJpaRepository jpaRepository;
    protected final EntityManager em;
    protected AbstractExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager) {
        this.jpaRepository = Reflections.classForLambdaSupplier(jpaRepository);
        this.em = entityManager;
    }

    @Override
    public T execute() {
        return doExecute();
    }
    protected abstract T doExecute() ;

    protected SimpleJpaRepository getJpaRepository() {
        return jpaRepository;
    }
}
