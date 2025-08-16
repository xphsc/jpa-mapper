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


import cn.xphsc.jpamapper.core.lambda.LambdaFunction;
import cn.xphsc.jpamapper.core.lambda.Reflections;

/**
 * {@link Criterion}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Conditional constructor
 * @since 1.0.0
 */

public class QueryCondition extends AbstactCriterion<QueryCondition> {

    protected  QueryCondition() {
    }

    @Override
    public  QueryCondition isNotEmpty(String propertyName) {
        super.isNotEmpty(propertyName);
        return this;

    }



    @Override
    public  QueryCondition isEmpty(String propertyName) {
        super.isEmpty(propertyName);
        return this;
    }
    @Override
    public  QueryCondition isNull(String propertyName) {
        super.isNull(propertyName);
        return this;
    }

    @Override
    public  QueryCondition isNotNull(String propertyName) {
        super.isNotNull(propertyName);
        return this;

    }


    @Override
    public  QueryCondition orIsNotEmpty(String propertyName) {
        super.orIsNotEmpty(propertyName);
        return this;

    }


    @Override
    public  QueryCondition orIsEmpty(String propertyName) {
        super.orIsEmpty(propertyName);
        return this;
    }


    @Override
    public  QueryCondition orIsNull(String propertyName) {
        super.orIsNull(propertyName);
        return this;
    }


    @Override
    public  QueryCondition orIsNotNull(String propertyName) {
        super.orIsNotNull(propertyName);
        return this;

    }


    @Override
    public  QueryCondition equalTo(String propertyName, Object value) {
        super.equalTo(propertyName,value);
        return this;
    }


    @Override
    public  QueryCondition notEqualTo(String propertyName, Object value) {
        super.notEqualTo(propertyName,value);
        return this;
    }


    @Override
    public  QueryCondition orEqualTo(String propertyName, Object value) {
        super.orEqualTo(propertyName,value);
        return this;
    }

    @Override
    public  QueryCondition orNotEqualTo(String propertyName, Object value) {
        super.orNotEqualTo(propertyName,value);
        return this;
    }



    @Override
    public  QueryCondition in(String propertyName, Iterable<?> values) {
        super.in(propertyName,values);
        return this;
    }


    @Override
    public  QueryCondition notIn(String propertyName, Iterable<?> values) {
        super.notIn(propertyName,values);
        return this;
    }


    @Override
    public  QueryCondition orIn(String propertyName, Iterable<?> values) {
        super.orIn(propertyName,values);
        return this;
    }


    @Override
    public  QueryCondition orNotIn(String propertyName, Iterable<?> values) {
        super.orNotIn(propertyName,values);
        return this;
    }


    @Override
    public  QueryCondition like(String propertyName, String value) {
        super.like(propertyName,value);
        return this;
    }

    @Override
    public  QueryCondition notLike(String propertyName, String value) {
        super.notLike(propertyName,value);
        return this;
    }


    @Override
    public  QueryCondition orLike(String propertyName, String value) {
        super.orLike(propertyName,value);
        return this;
    }

    @Override
    public  QueryCondition orNotLike(String propertyName, String value) {
        super.orNotLike(propertyName,value);
        return this;
    }



    @Override
    public  QueryCondition like(String propertyName, String value,
            Criterion.LIKEMode likeMode) {
        super.like(propertyName,value,likeMode);
        return this;
    }


    @Override
    public  QueryCondition between(String propertyName, Object value,
                                   Object betweenValue) {
        super.between(propertyName,value,betweenValue);
        return this;
    }


    @Override
    public  QueryCondition orBetween(String propertyName, Object value,
                                   Object betweenValue) {
        super.orBetween(propertyName,value,betweenValue);
        return this;
    }

    @Override
    public  QueryCondition notBetween(String propertyName, Object value,
                                   Object betweenValue) {
        super.notBetween(propertyName,value,betweenValue);
        return this;
    }


    @Override
    public  QueryCondition orNotBetween(String propertyName, Object value,
                                     Object betweenValue) {
        super.orNotBetween(propertyName,value,betweenValue);
        return this;
    }



    @Override
    public  QueryCondition lessThan(String propertyName, Object value) {
        super.lessThan(propertyName,value);
        return this;
    }

    @Override
    public  QueryCondition orLessThan(String propertyName, Object value) {
        super.orLessThan(propertyName,value);
        return this;
    }

    @Override
    public  QueryCondition lessThanOrEqualTo(String propertyName, Object value) {
        super.lessThanOrEqualTo(propertyName,value);
        return this;
    }


    @Override
    public  QueryCondition orLessThanOrEqualTo(String propertyName, Object value) {
        super.orLessThanOrEqualTo(propertyName,value);
        return this;
    }


    @Override
    public  QueryCondition greaterThan(String propertyName, Object value) {
        super.greaterThan(propertyName,value);
        return this;
    }


    @Override
    public  QueryCondition orGreaterThan(String propertyName, Object value) {
        super.orGreaterThan(propertyName,value);
        return this;
    }


    @Override
    public  QueryCondition greaterThanOrEqualTo(String propertyName, Object value) {
        super.greaterThanOrEqualTo(propertyName,value);
        return this;

    }



    @Override
    public  QueryCondition  orGreaterThanOrEqualTo(String propertyName, Object value) {
        super.orGreaterThanOrEqualTo(propertyName,value);
        return this;

    }

 public  <T>  QueryCondition isNotEmpty(LambdaFunction<T, Object> propertyName) {
     super.isNotEmpty(Reflections.fieldNameForLambdaFunction(propertyName));
        return this;
    }


    public <T>  QueryCondition isEmpty(LambdaFunction<T, Object> propertyName) {
        super.isEmpty(Reflections.fieldNameForLambdaFunction(propertyName));
        return this;
    }


    public  <T> QueryCondition isNull(LambdaFunction<T, Object> propertyName) {
        super.isNull(Reflections.fieldNameForLambdaFunction(propertyName));
        return this;
    }



    public  <T>  QueryCondition isNotNull(LambdaFunction<T, Object> propertyName) {
        super.isNotNull(Reflections.fieldNameForLambdaFunction(propertyName));
        return this;

    }



    public  <T>  QueryCondition orIsNotEmpty(LambdaFunction<T, Object> propertyName) {
        super.orIsNotEmpty(Reflections.fieldNameForLambdaFunction(propertyName));
        return this;

    }



    public    <T> QueryCondition orIsEmpty(LambdaFunction propertyName) {
        super.orIsEmpty(Reflections.fieldNameForLambdaFunction(propertyName));
        return this;
    }



    public   <T> QueryCondition orIsNull(LambdaFunction<T, Object> propertyName) {
        super.orIsNull(Reflections.fieldNameForLambdaFunction(propertyName));
        return this;
    }



    public   <T> QueryCondition orIsNotNull(LambdaFunction propertyName) {
        super.orIsNotNull(Reflections.fieldNameForLambdaFunction(propertyName));
        return this;

    }


    public  <T>  QueryCondition equalTo(LambdaFunction<T, Object> propertyName, Object value) {
        this.equalTo(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }




    public   <T> QueryCondition notEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
        super.notEqualTo(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }



    public   <T> QueryCondition orEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
        super.orEqualTo(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }


    public  <T>  QueryCondition orNotEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
        super.orNotEqualTo(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }



    public   <T> QueryCondition in(LambdaFunction<T, Object> propertyName, Iterable<?> values) {
        super.in(Reflections.fieldNameForLambdaFunction(propertyName),values);
        return this;
    }



    public   <T> QueryCondition notIn(LambdaFunction<T, Object> propertyName, Iterable<?> values) {
        super.notIn(Reflections.fieldNameForLambdaFunction(propertyName),values);
        return this;
    }


    public   <T> QueryCondition orIn(LambdaFunction<T, Object> propertyName, Iterable<?> values) {
        super.orIn(Reflections.fieldNameForLambdaFunction(propertyName),values);
        return this;
    }


    public   <T> QueryCondition orNotIn(LambdaFunction<T, Object> propertyName, Iterable<?> values) {
        super.orNotIn(Reflections.fieldNameForLambdaFunction(propertyName),values);
        return this;
    }



    public  <T>  QueryCondition like(LambdaFunction<T, Object> propertyName, String value) {
        super.like(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }


    public  <T>  QueryCondition notLike(LambdaFunction<T, Object> propertyName, String value) {
        super.notLike(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }



    public  <T>  QueryCondition orLike(LambdaFunction<T, Object> propertyName, String value) {
        super.orLike(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }



    public   <T> QueryCondition orNotLike(LambdaFunction<T, Object> propertyName, String value) {
        super.orNotLike(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }




    public  <T>  QueryCondition like(LambdaFunction<T, Object> propertyName, String value,
                                          Criterion.LIKEMode likeMode) {
        super.like(Reflections.fieldNameForLambdaFunction(propertyName),value,likeMode);
        return this;
    }


    public   <T> QueryCondition between(LambdaFunction<T, Object> propertyName, Object value,
                                             Object betweenValue) {
        super.between(Reflections.fieldNameForLambdaFunction(propertyName),value,betweenValue);
        return this;
    }



    public   <T> QueryCondition orBetween(LambdaFunction<T, Object> propertyName, Object value,
                                               Object betweenValue) {
        super.orBetween(Reflections.fieldNameForLambdaFunction(propertyName),value,betweenValue);
        return this;
    }

    public   <T> QueryCondition notBetween(LambdaFunction<T, Object> propertyName, Object value,
                                                Object betweenValue) {
        super.notBetween(Reflections.fieldNameForLambdaFunction(propertyName),value,betweenValue);
        return this;
    }





    public   <T> QueryCondition orNotBetween(LambdaFunction<T, Object> propertyName, Object value,
                                                  Object betweenValue) {
        super.orNotBetween(Reflections.fieldNameForLambdaFunction(propertyName),value,betweenValue);
        return this;
    }


    public   <T> QueryCondition lessThan(LambdaFunction<T, Object> propertyName, Object value) {
        super.lessThan(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }



    public   <T> QueryCondition orLessThan(LambdaFunction<T, Object> propertyName, Object value) {
        super.orLessThan(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }




    public  <T>  QueryCondition lessThanOrEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
        super.lessThanOrEqualTo(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }



    public   <T> QueryCondition orLessThanOrEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
        this.orLessThanOrEqualTo(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }


    public   <T> QueryCondition greaterThan(LambdaFunction<T, Object> propertyName, Object value) {
        super.greaterThan(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }



    public  <T>  QueryCondition orGreaterThan(LambdaFunction<T, Object> propertyName, Object value) {
        super.orGreaterThan(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;
    }





    public   <T> QueryCondition greaterThanOrEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
        super.greaterThanOrEqualTo(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;

    }



    public   <T> QueryCondition  orGreaterThanOrEqualTo(LambdaFunction<T, Object> propertyName, Object value) {
        super.orGreaterThanOrEqualTo(Reflections.fieldNameForLambdaFunction(propertyName),value);
        return this;

    }

}
