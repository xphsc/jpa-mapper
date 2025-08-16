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



/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 定义一个查询条件容器
 * @since 1.0.0
 */
public class Criteria extends AbstractCriteria<Criteria>  {

    public Criteria() {
        super();
    }


    @Override
    public SimpleCriteria createCriteria() {
        return super.createCriteria();
    }


    public static Criteria.Builder builder(){
        return  new Criteria.Builder();
    }

    private Criteria(Criteria.Builder builder){
        super.distinct(builder.distinct);
        super.having(builder.having);
        if(builder.sqlCriteria!=null){
            super.sqlCriteria(builder.sqlCriteria);
        }

    }
    public static class Builder {
        private boolean distinct;
        private boolean having;
        private SqlCriteria sqlCriteria;
        public Builder(){
        }


        public Criteria.Builder distinct(boolean distinct) {
            this.distinct = distinct;
            return this;
        }

        public Criteria.Builder having(boolean having) {
            this.having = having;
            return this;
        }

        public Criteria.Builder where(SqlCriteria sqlCriteria) {
            this.sqlCriteria = sqlCriteria;
            return this;
        }

        public Criteria build() {
            return new Criteria(this);
        }
    }
    @Override
    protected void clear() {
        super.clear();
    }


}
