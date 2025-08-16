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


import cn.xphsc.jpamapper.core.criteria.UpdateQuery;
import cn.xphsc.jpamapper.core.lambda.LambdaSupplier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.Map;

/**
 * {@link AbstractExecutor}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Update Executor
 * @since 1.0.0
 */
public class UpdateExecutor<T>  extends AbstractExecutor<Integer> {

    private UpdateQuery updateQuery;
    private Class<T>   entityClass;
    private Map<String,Object> sets;
    public UpdateExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager, UpdateQuery updateQuery, Class<T> clazz) {
        super(jpaRepository, entityManager);
        this.updateQuery=updateQuery;
        this.entityClass=updateQuery.getEntityClass()!=null?updateQuery.getEntityClass():clazz;
        this.sets=updateQuery.getSets();
    }

    @Override
    protected Integer doExecute() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<T> update= cb.createCriteriaUpdate(entityClass);
        Root<T> root = update.from(entityClass);
        for(Map.Entry<String,Object> entity:sets.entrySet()){
            update.set(entity.getKey(),entity.getValue());
        }
        if(updateQuery.getCriteria()!=null){
            Specification specification=updateQuery.getCriteria();
            Predicate predicate=specification.toPredicate(root, cb.createQuery(), cb);
            update.where(predicate);
        }
        Query query = em.createQuery(update);
        return query.executeUpdate();
    }
}
