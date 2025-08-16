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
package cn.xphsc.jpamapper.core.criteria;


import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class UpdateQuery<T> {

    private Class<T> entityClass;

    private Criteria criteria;

    private Map<String,Object> sets;

    public UpdateQuery(){

    }
    private UpdateQuery(Builder builder){
        this.entityClass=builder.entityClass;
        this.criteria=builder.criteria;
        this.sets=builder.sets;

    }

    public static Builder builder(){
        return  new Builder();
    }

    public static class Builder {
        private Class  entityClass;
        private Criteria criteria;
        private Map<String,Object> sets;
        public Builder(){}

        public Builder from(Class<?> entityClass) {
            this.entityClass = entityClass;
            return this;
        }


        public Builder where(Criteria criteria) {
            this.criteria = criteria;
            return this;
        }

        public  Builder  set(Map<String,Object> bean){
            this.sets=bean;
            return this;
        }


        public UpdateQuery build() {
            return new UpdateQuery(this);
        }
    }




    /**
     * Create and add a query root corresponding to the entity
     * that is the target of the delete.
     * A CriteriaDelete object has a single root, the object that
     * is being deleted.
     *
     * @param entityClass the entity class
     *
     * @return query root corresponding to the given entity
     */

    public void  from(Class<T> entityClass){
      this.entityClass=entityClass;
         };

    public  void where(Criteria criteria){
        this.criteria=criteria;
    }

    public  void  set(Map<String,Object> bean){
        this.sets=bean;
    }


    public Class<T> getEntityClass() {
        return entityClass;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public Map<String, Object> getSets() {
        return sets;
    }
}
