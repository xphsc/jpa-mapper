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
import java.util.ArrayList;
import java.util.List;

/**
 * {@link Criterion}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public  abstract class  AbstactCriterion<T> {
    protected List<Criterion> criterions;
    public AbstactCriterion(){
        criterions = new ArrayList<Criterion>();
    }
    protected List <Criterion> getCriterions() {
        return criterions;
    }

    protected void setCriterions(List <Criterion> criterions) {
        this.criterions = criterions;
    }


    protected  T isNotEmpty(String propertyName) {
        criterions.add(new SimpleExpression(propertyName, Criterion.Operator.IS_NOT_EMPTY));
        return  (T) this;

    }



    protected  T isEmpty(String propertyName) {
        criterions.add( new SimpleExpression(propertyName, Criterion.Operator.IS_EMPTY));
        return  (T) this;
    }

    protected  T isNull(String propertyName) {
        criterions.add(new SimpleExpression(propertyName, Criterion.Operator.IS_NULL));
        return  (T) this;
    }

    protected  T isNotNull(String propertyName) {
        criterions.add(new SimpleExpression(propertyName, Criterion.Operator.IS_NOT_NULL));
        return  (T) this;

    }



    protected  T orIsNotEmpty(String propertyName) {
        criterions.add(new SimpleExpression(propertyName, Criterion.Operator.OR_IS_NOT_EMPTY));
        return  (T) this;

    }



    protected  T orIsEmpty(String propertyName) {
        criterions.add( new SimpleExpression(propertyName, Criterion.Operator.OR_IS_EMPTY));
        return  (T) this;
    }



    protected  T orIsNull(String propertyName) {
        criterions.add(new SimpleExpression(propertyName, Criterion.Operator.OR_IS_NULL));
        return  (T) this;
    }



    protected  T orIsNotNull(String propertyName) {
        criterions.add(new SimpleExpression(propertyName, Criterion.Operator.OR_IS_NOT_NULL));
        return  (T) this;

    }



    protected  T equalTo(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.EQ));
        return  (T) this;
    }



    protected  T notEqualTo(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.NOT_EQUAL));
        return  (T) this;
    }



    protected  T orEqualTo(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.OR_EQ));
        return  (T) this;
    }


    protected  T orNotEqualTo(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.OR_NOT_EQUAL));
        return  (T) this;
    }




    protected  T in(String propertyName, Iterable<?> values) {
        criterions.add(new SimpleExpression(propertyName, values, Criterion.Operator.IN));
        return  (T) this;
    }



    protected  T  notIn(String propertyName, Iterable<?> values) {
        criterions.add(new SimpleExpression(propertyName, values, Criterion.Operator.NOT_IN));
        return  (T) this;
    }


    protected  T  orIn(String propertyName, Iterable<?> values) {
        criterions.add(new SimpleExpression(propertyName, values, Criterion.Operator.OR_IN));
        return  (T) this;
    }



    protected  T  orNotIn(String propertyName, Iterable<?> values) {
        criterions.add(new SimpleExpression(propertyName, values, Criterion.Operator.OR_NOT_IN));
        return  (T) this;
    }



    protected  T like(String propertyName, String value) {
        criterions.add(new SimpleExpression(propertyName, value,Criterion.LIKEMode.DEFAULT, Criterion.Operator.LIKE));
        return  (T) this;
    }

    protected  T notLike(String propertyName, String value) {
        criterions.add(new SimpleExpression(propertyName, value,Criterion.LIKEMode.DEFAULT, Criterion.Operator.NOT_LIKE));
        return  (T) this;
    }



    protected  T orLike(String propertyName, String value) {
        criterions.add(new SimpleExpression(propertyName, value,Criterion.LIKEMode.DEFAULT, Criterion.Operator.OR_LIKE));
        return  (T) this;
    }


    protected  T orNotLike(String propertyName, String value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.LIKEMode.DEFAULT,Criterion.Operator.OR_NOT_LIKE));
        return  (T) this;
    }




    protected  T like(String propertyName, String value,
                                Criterion.LIKEMode likeMode) {
        criterions.add(new SimpleExpression(propertyName, value, likeMode, Criterion.Operator.LIKE));
        return  (T) this;
    }



    protected  T between(String propertyName, Object value,
                                   Object betweenValue) {
        criterions.add(new SimpleExpression(propertyName, value, betweenValue, Criterion.Operator.BETWEEN));
        return  (T) this;
    }



    protected  T orBetween(String propertyName, Object value,
                                     Object betweenValue) {
        criterions.add(new SimpleExpression(propertyName, value, betweenValue, Criterion.Operator.OR_BETWEEN));
        return  (T) this;
    }


    protected  T notBetween(String propertyName, Object value,
                                      Object betweenValue) {
        criterions.add(new SimpleExpression(propertyName, value, betweenValue, Criterion.Operator.BETWEEN));
        return  (T) this;
    }



    protected  T orNotBetween(String propertyName, Object value,
                                        Object betweenValue) {
        criterions.add(new SimpleExpression(propertyName, value, betweenValue, Criterion.Operator.OR_NOT_BETWEEN));
        return  (T) this;
    }




    protected  T lessThan(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.LT));
        return  (T) this;
    }


    protected  T orLessThan(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.OR_LT));
        return  (T) this;
    }


    protected  T lessThanOrEqualTo(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.LTE));
        return  (T) this;
    }



    protected  T orLessThanOrEqualTo(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.OR_LTE));
        return  (T) this;
    }



    protected  T greaterThan(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.GT));
        return  (T) this;
    }



    protected  T orGreaterThan(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.OR_GT));
        return  (T) this;
    }




    protected  T greaterThanOrEqualTo(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.GTE));
        return  (T) this;

    }



    protected  T  orGreaterThanOrEqualTo(String propertyName, Object value) {
        criterions.add(new SimpleExpression(propertyName, value, Criterion.Operator.OR_GTE));
        return  (T) this;

    }

}
