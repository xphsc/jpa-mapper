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


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class SqlCriteria extends AbstactCriterion<SqlCriteria> {


    public SqlCriteria() {
    }

    public static SqlCriteria.Builder builder(){
        return  new SqlCriteria.Builder();
    }

    private SqlCriteria(SqlCriteria.Builder builder){
       this.setCriterions(builder.getCriterions());
    }

    public static class Builder extends QueryCondition {
       public Builder(){
           super();
       }
       @Override
        public  <T>  SqlCriteria.Builder isNotEmpty(LambdaFunction<T, Object> propertyName) {
           super.isNotEmpty(propertyName);
            return this;
        }

        @Override
        public <T>  SqlCriteria.Builder isEmpty(LambdaFunction<T, Object> propertyName) {
            super.isEmpty(propertyName);
            return this;
        }

        @Override
        public  <T> SqlCriteria.Builder isNull(LambdaFunction<T, Object> propertyName) {
            super.isNull(propertyName);
            return this;
        }


        @Override
        public  <T> SqlCriteria.Builder isNotNull(LambdaFunction<T, Object> propertyName) {
            super.isNotNull(propertyName);
            return this;

        }


        @Override
        public  <T>  SqlCriteria.Builder orIsNotEmpty(LambdaFunction<T, Object> propertyName) {
            super.orIsNotEmpty(propertyName);
            return this;

        }


        @Override
        public    <T> SqlCriteria.Builder orIsEmpty(LambdaFunction propertyName) {
            super.orIsEmpty(propertyName);
            return this;
        }


        @Override
        public   <T> SqlCriteria.Builder orIsNull(LambdaFunction<T, Object> propertyName) {
            super.orIsNull(propertyName);
            return this;
        }


        @Override
        public   <T> SqlCriteria.Builder orIsNotNull(LambdaFunction propertyName) {
            super.orIsNotNull(propertyName);
            return this;

        }

        @Override
        public  <T>  SqlCriteria.Builder equalTo(LambdaFunction<T, Object> propertyName, Object value) {
            super.equalTo(propertyName,value);
            return this;
        }



        @Override
        public   <T> SqlCriteria.Builder notEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
            super.notEqualTo(propertyName,value);
            return this;
        }


        @Override
        public   <T> SqlCriteria.Builder orEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
            super.orEqualTo(propertyName,value);
            return this;
        }
        @Override
        public  <T>  SqlCriteria.Builder orNotEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
            super.orNotEqualTo(propertyName,value);
            return this;
        }


        @Override
        public   <T> SqlCriteria.Builder in(LambdaFunction<T, Object> propertyName, Iterable<?> values) {
            super.in(propertyName,values);
            return this;
        }


        @Override
        public   <T> SqlCriteria.Builder notIn(LambdaFunction<T, Object> propertyName, Iterable<?> values) {
            super.notIn(propertyName,values);
            return this;
        }

        @Override
        public   <T> SqlCriteria.Builder orIn(LambdaFunction<T, Object> propertyName, Iterable<?> values) {
            super.orIn(propertyName,values);
            return this;
        }

        @Override
        public   <T> SqlCriteria.Builder orNotIn(LambdaFunction<T, Object> propertyName, Iterable<?> values) {
            super.orNotIn(propertyName,values);
            return this;
        }


        @Override
        public  <T>  SqlCriteria.Builder like(LambdaFunction<T, Object> propertyName, String value) {
            super.like(propertyName,value);
            return this;
        }

        @Override
        public  <T>  SqlCriteria.Builder notLike(LambdaFunction<T, Object> propertyName, String value) {
            super.notLike(propertyName,value);
            return this;
        }


        @Override
        public  <T>  SqlCriteria.Builder orLike(LambdaFunction<T, Object> propertyName, String value) {
            super.orLike(propertyName,value);
            return this;
        }


        @Override
        public   <T> SqlCriteria.Builder orNotLike(LambdaFunction<T, Object> propertyName, String value) {
            super.orNotLike(propertyName,value);
            return this;
        }



        @Override
        public  <T>  SqlCriteria.Builder like(LambdaFunction<T, Object> propertyName, String value,
                                          Criterion.LIKEMode likeMode) {
            super.like(propertyName,value,likeMode);
            return this;
        }

        @Override
        public   <T> SqlCriteria.Builder between(LambdaFunction<T, Object> propertyName, Object value,
                                             Object betweenValue) {
            super.between(propertyName,value,betweenValue);
            return this;
        }


        @Override
        public   <T> SqlCriteria.Builder orBetween(LambdaFunction<T, Object> propertyName, Object value,
                                               Object betweenValue) {
            super.orBetween(propertyName,value,betweenValue);
            return this;
        }
        @Override
        public   <T> SqlCriteria.Builder notBetween(LambdaFunction<T, Object> propertyName, Object value,
                                                Object betweenValue) {
            super.notBetween(propertyName,value,betweenValue);
            return this;
        }




        @Override
        public   <T> SqlCriteria.Builder orNotBetween(LambdaFunction<T, Object> propertyName, Object value,
                                                  Object betweenValue) {
            super.orNotBetween(propertyName,value,betweenValue);
            return this;
        }

        @Override
        public   <T> SqlCriteria.Builder lessThan(LambdaFunction<T, Object> propertyName, Object value) {
            super.lessThan(propertyName,value);
            return this;
        }


        @Override
        public   <T> SqlCriteria.Builder orLessThan(LambdaFunction<T, Object> propertyName, Object value) {
            super.orLessThan(propertyName,value);
            return this;
        }



        @Override
        public  <T>  SqlCriteria.Builder lessThanOrEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
            super.lessThanOrEqualTo(propertyName,value);
            return this;
        }


        @Override
        public   <T> SqlCriteria.Builder orLessThanOrEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
            super.orLessThanOrEqualTo(propertyName,value);
            return this;
        }

        @Override
        public   <T> SqlCriteria.Builder greaterThan(LambdaFunction<T, Object> propertyName, Object value) {
            super.greaterThan(propertyName,value);
            return this;
        }


        @Override
        public  <T>  SqlCriteria.Builder orGreaterThan(LambdaFunction<T, Object> propertyName, Object value) {
            super.orGreaterThan(propertyName,value);
            return this;
        }





        @Override
        public   <T> SqlCriteria.Builder greaterThanOrEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
            super.greaterThanOrEqualTo(propertyName,value);
            return this;

        }



        @Override
        public   <T> SqlCriteria.Builder  orGreaterThanOrEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
            super.orGreaterThanOrEqualTo(propertyName,value);
            return this;

        }


        public SqlCriteria build() {
            return new SqlCriteria(this);
        }
    }


}
