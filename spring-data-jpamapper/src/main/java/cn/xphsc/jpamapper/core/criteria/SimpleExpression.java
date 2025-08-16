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



import cn.xphsc.jpamapper.utils.StringUtils;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.LinkedList;



/**
 * {@link Criterion}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Simple conditional expression
 * @since 1.0.0
 */
public class SimpleExpression implements Criterion{
    /**
     * 属性名
     */
    private String fieldName;
    /**
     *对应值
     */
    private Object value;
    /**
     *计算符
     */
    private Criterion.Operator operator;
    /**
     * like匹配方式
     */
    private Criterion.LIKEMode likeMode;

    private Iterable values;
    /**
     *对应值
     */
    private Object betweenValue;


    protected SimpleExpression(String fieldName, Criterion.Operator operator) {
        this.fieldName = fieldName;
        this.operator = operator;
    }


    protected SimpleExpression(String fieldName, Object value, Criterion.Operator operator) {
        this.fieldName = fieldName;  
        this.value = value;  
        this.operator =operator;
    }

    protected SimpleExpression(String fieldName, Iterable<?> values, Criterion.Operator operator) {
        this.fieldName = fieldName;
        this.values = values;
        this.operator = operator;
    }

    protected SimpleExpression(String fieldName, Object value, Object betweenValue, Criterion.Operator operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.betweenValue = betweenValue;
        this.operator = operator;
    }

    protected SimpleExpression(String fieldName, Object value, Criterion.LIKEMode matchMode, Criterion.Operator operator) {
        this.fieldName = fieldName;  
        this.value = value;  
        this.operator = operator; 
        this.likeMode = matchMode;
    }  
  



    @Override
    public Predicate toPredicate(Root <?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path expression = null;
        if(fieldName.contains(".")){
            String[] names = StringUtils.split(fieldName, ".");
            expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }
        }else{
            expression = root.get(fieldName);
        }
        switch (operator) {
            case EQ:
                return builder.equal(expression, value);
            case NOT_EQUAL:
                return builder.notEqual(expression, value);
            case OR_EQ:
                return builder.or(builder.equal(expression, value));
            case OR_NOT_EQUAL:
                return builder.or(builder.notEqual(expression, value));
            case BETWEEN:
                return betweenBuilder(builder,expression,value,betweenValue);
            case OR_BETWEEN:
                return builder.or(betweenBuilder(builder,expression,value,betweenValue));
            case NOT_BETWEEN:
                return betweenBuilder(builder,expression,value,betweenValue).not();
            case OR_NOT_BETWEEN:
                return builder.or(betweenBuilder(builder,expression,value,betweenValue).not());
            case LIKE:
                switch(likeMode){
                    case LEFT :
                        return builder.like((Expression<String>) expression, value + "%");
                    case RIGHT :
                        return builder.like((Expression<String>) expression, "%" + value);
                    case ANYWHERE :
                        return builder.like((Expression<String>) expression,  value.toString());
                    case DEFAULT :
                        return builder.or(builder.like((Expression<String>) expression, "%" + value + "%"));
                }
            case OR_LIKE:
                switch(likeMode){
                    case LEFT :
                        return builder.or(builder.like((Expression<String>) expression, value + "%"));
                    case RIGHT :
                        return  builder.or(builder.like((Expression<String>) expression, "%" + value));
                    case ANYWHERE :
                        return  builder.or(builder.like((Expression<String>) expression,  value .toString()));
                    case DEFAULT :
                        return builder.or(builder.like((Expression<String>) expression, "%" + value + "%"));
                }
            case NOT_LIKE:
                switch(likeMode){
                    case LEFT :
                        return builder.notLike((Expression<String>) expression, value + "%");
                    case RIGHT :
                        return builder.notLike((Expression<String>) expression, "%" + value);
                    case ANYWHERE :
                        return builder.notLike((Expression<String>) expression, value .toString());
                    case DEFAULT :
                        return builder.notLike((Expression<String>) expression, "%" + value + "%");
                }
            case OR_NOT_LIKE:
                switch(likeMode){
                    case LEFT :
                        return builder.or(builder.notLike((Expression<String>) expression, value + "%"));
                    case RIGHT :
                        return builder.or(builder.notLike((Expression<String>) expression, "%" + value));
                    case ANYWHERE :
                        return builder.or(builder.notLike((Expression<String>) expression, value .toString()));
                    case DEFAULT :
                        return builder.or(builder.notLike((Expression<String>) expression, "%" + value + "%"));
                }
            case IN:
                return builder.in(expression).value(values);
            case NOT_IN:
                return builder.in(expression).value(values).not();
            case OR_IN:
                return builder.or(builder.in(expression).value(values));
            case OR_NOT_IN:
                return builder.or(builder.in(expression).value(values).not());
            case LT:
                return builder.lessThan(expression, (Comparable) value);
            case OR_LT:
                return builder.or(builder.lessThan(expression, (Comparable) value));
            case GT:
                return builder.greaterThan(expression, (Comparable) value);
            case OR_GT:
                return builder.or(builder.greaterThan(expression, (Comparable) value));
            case LTE:
                return builder.lessThanOrEqualTo(expression, (Comparable) value);
            case OR_LTE:
                return builder.or(builder.lessThanOrEqualTo(expression, (Comparable) value));
            case GTE:
                return builder.greaterThanOrEqualTo(expression, (Comparable) value);
            case OR_GTE:
                return builder.or(builder.greaterThanOrEqualTo(expression, (Comparable) value));
            case IS_NOT_NULL:
                return builder.isNotNull(expression);
            case OR_IS_NOT_NULL:
                return builder.or(builder.isNotNull(expression));
            case IS_NULL:
                return builder.isNull(expression);
            case OR_IS_NULL:
                return builder.or(builder.isNull(expression));
            case IS_EMPTY:
                return builder.isEmpty(expression);
            case OR_IS_EMPTY:
                return builder.or(builder.isEmpty(expression));
            case IS_NOT_EMPTY:
                return builder.isNotEmpty(expression);
            case OR_IS_NOT_EMPTY:
                return builder.or(builder.isNotEmpty(expression));
            default:
                return null;
        }
    }

    private static Predicate betweenBuilder(CriteriaBuilder cb, Path fieldPath, Object value1,
                                        Object value2) {
        Object type=fieldPath.type().getJavaType();
        if(type.equals(String.class)) {
            return cb.between(fieldPath.as(String.class), (String)value1, (String)value2);
        }
        if(type.equals(Integer.class)) {
            return cb.between(fieldPath.as(Integer.class), (Integer)value1, (Integer)value2);
        }
        if(type.equals(Long.class)) {
            return cb.between(fieldPath.as(Long.class), (Long)value1, (Long)value2);
        }

        if(type.equals(Double.class)) {
            return cb.between(fieldPath.as(Double.class), (Double)value1, (Double)value2);
        }
        if(type.equals(Date.class)) {
            return cb.between(fieldPath.as(Date.class), (Date)value1, (Date)value2);
        }

        return null;
    }
}
