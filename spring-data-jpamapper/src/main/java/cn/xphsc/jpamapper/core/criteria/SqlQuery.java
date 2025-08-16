/*
 * Copyright (c) 2021 huipei.x
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

import cn.xphsc.jpamapper.core.paginator.PageInfo;
import org.springframework.data.domain.Sort;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: SqlQuery  Build query
 * @since 1.2.3
 */
public class SqlQuery {

    /**
     * sql  or SQL as a  SQL query string
     */
    private String sql;
    /**
     * parameters  any JavaBean or POJO or Map or Object[]
     */
    private Object parameters;
    /**
     * PageInfo for pagination information.
     */
    private  PageInfo pageInfo;
    /**
     * Sort option for queries. You have to provide at least a list of properties to sort for that must not include
     */
    private Sort sort;
    /**
     *   entityClass any JavaBean or POJO or Map
     */
    private Class<?> entityClass;
    public SqlQuery(){}
    public static SqlQuery.Builder builder(){
        return  new SqlQuery.Builder();
    }

    private SqlQuery(SqlQuery.Builder builder){
        this.sql=builder.sql;
        this.parameters=builder.parameters;
        this.pageInfo=builder.pageInfo;
        this.sort=builder.sort;
        this.entityClass=builder.entityClass;
    }

    public static class Builder{
        public Builder(){
        }
        private String sql;
        private Object parameters;
        private  PageInfo pageInfo;
        private Sort sort;
        private Class<?> entityClass;


        public   <T> SqlQuery.Builder  sql(String sql) {
           this.sql=sql;
            return this;
        }
        public   <T> SqlQuery.Builder  parameters(Object parameters) {
            this.parameters=parameters;
            return this;
        }


        public   <T> SqlQuery.Builder  pageInfo(PageInfo pageInfo) {
            this.pageInfo=pageInfo;
            return this;
        }


        public   <T> SqlQuery.Builder  sort(Sort sort) {
            this.sort=sort;
            return this;
        }
        public   <S> SqlQuery.Builder  entityClass(Class<S> entityClass) {
            this.entityClass=entityClass;
            return this;
        }


        public SqlQuery build() {
            return new SqlQuery(this);
        }
    }


    public String sql() {
        return sql;
    }


    public Object parameters() {
        return parameters;
    }


    public PageInfo pageInfo() {
        return pageInfo;
    }



    public Sort sort() {
        return sort;
    }

    public Class<?> entityClass() {
        return entityClass;
    }
}
