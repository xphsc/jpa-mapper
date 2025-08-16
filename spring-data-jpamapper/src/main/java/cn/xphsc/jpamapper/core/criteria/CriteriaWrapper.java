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


import cn.xphsc.jpamapper.core.lambda.LambdaFunction;
import cn.xphsc.jpamapper.core.lambda.Reflections;
import cn.xphsc.jpamapper.utils.Collects;
import org.springframework.data.domain.Sort;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link AbstractCriteria}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class CriteriaWrapper extends AbstractCriteria<CriteriaWrapper> {

    public CriteriaWrapper() {
        super();
    }

    public static CriteriaWrapper.Builder builder(){
        return  new CriteriaWrapper.Builder();
    }

    private CriteriaWrapper(CriteriaWrapper.Builder builder){
        super.distinct(builder.distinct);
        super.having(builder.having);
            super.mapping(builder.mappings);
        if(builder.entityClass!=null){
            super.entityClass(builder.entityClass);
        }
        if(builder.pageNum!=null&&builder.pageSize!=null){
            super.pageInfo(builder.pageNum,builder.pageSize);
        }
        if(builder.offset!=null&&builder.offset!=null){
            super.offsetPage(builder.offset,builder.limit);
        }
        super.sort(builder.sort);
        super.groupBy(builder.groupBy);
        if(builder.sqlCriteria!=null){
            super.sqlCriteria(builder.sqlCriteria);
        }


    }
    public static class Builder {
        private boolean distinct;
        private boolean having;
        private Class <?> entityClass;
        private Map<String,String> mappings;
        private Sort sort;
        private String groupBy;
        private Integer offset;
        private Integer limit;
        private Integer pageNum;
        private Integer pageSize;
        private SqlCriteria sqlCriteria;
        public Builder(){

        }


        public CriteriaWrapper.Builder distinct(boolean distinct) {
            this.distinct = distinct;
            return this;
        }

        public CriteriaWrapper.Builder having(boolean having) {
            this.having = having;
            return this;
        }


        public CriteriaWrapper.Builder entityClass(Class <?> entityClass) {
            this.entityClass = entityClass;
            return this;
        }


        public CriteriaWrapper.Builder mapping(String property, String field) {
            if(Collects.isEmpty(mappings)){
                mappings= new HashMap();
            }
            mappings.put(property,field);

            return this;
        }

        public <T,S> CriteriaWrapper.Builder mapping(LambdaFunction<T, Object> property, LambdaFunction<S, Object> field) {
            if(Collects.isEmpty(mappings)){
                mappings= new HashMap();
            }
            mappings.put(Reflections.fieldNameForLambdaFunction(property),Reflections.fieldNameForLambdaFunction(field));
            return this;
        }


        public CriteriaWrapper.Builder sort(Sort sort) {
            this.sort = sort;
            return this;
        }



        public CriteriaWrapper.Builder pageInfo(int pageNum, int pageSize) {
            this.pageNum = pageNum;
            this.pageSize = pageSize;
            return this;
        }
        /**
         *  paging for query
         */
        public CriteriaWrapper.Builder offsetPage(int offset, int limit) {
            this.offset = offset;
            this.limit = limit;
            return this;
        }


        public CriteriaWrapper.Builder groupBy(String groupBy) {
            this.groupBy = groupBy;
            return this;
        }

        public <T>  CriteriaWrapper.Builder groupBy(LambdaFunction<T, Object> groupBy) {
            this.groupBy = Reflections.fieldNameForLambdaFunction(groupBy);
            return this;
        }

        public CriteriaWrapper.Builder where(SqlCriteria sqlCriteria) {
            this.sqlCriteria =sqlCriteria;
            return this;
        }


        public CriteriaWrapper build() {
            return new CriteriaWrapper(this);
        }
    }
    @Override
    protected void clear() {
        super.clear();
    }
}
