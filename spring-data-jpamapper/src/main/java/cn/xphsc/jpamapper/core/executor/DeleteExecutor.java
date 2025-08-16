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

import cn.xphsc.jpamapper.core.criteria.DeleteQuery;
import cn.xphsc.jpamapper.core.lambda.LambdaSupplier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;

/**
 * {@link Executor}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Delete Executor
 * @since 1.0.0
 */
public class DeleteExecutor<T>  extends AbstractExecutor<Integer> {

    private DeleteQuery deleteQuery;
    private Class<T>   entityClass;
    public DeleteExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager, DeleteQuery deleteQuery, Class<T> clazz) {
        super(jpaRepository, entityManager);
        this.deleteQuery=deleteQuery;
        this.entityClass=deleteQuery.getEntityClass()!=null?deleteQuery.getEntityClass():clazz;
    }

    @Override
    protected Integer doExecute() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<T> delete= cb.createCriteriaDelete(entityClass);
        Root<T> root = delete.from(entityClass);
        Specification specification=deleteQuery.getCriteria();
        Predicate predicate=specification.toPredicate(root, cb.createQuery(), cb);
        delete.where(predicate);
        Query query = em.createQuery(delete);
        return query.executeUpdate();
    }
}
