/*
 * Copyright (c) 2024 huipei.x
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
package cn.xphsc.jpamapper.core.parser;

import cn.xphsc.jpamapper.core.executor.CountExecutor;
import cn.xphsc.jpamapper.core.executor.SQLExecutor;
import cn.xphsc.jpamapper.core.mapper.BeanTransformerAdapter;
import cn.xphsc.jpamapper.core.mapper.ResultMapper;
import cn.xphsc.jpamapper.core.mapper.SmartTransformer;
import cn.xphsc.jpamapper.core.paginator.PageInfo;
import cn.xphsc.jpamapper.core.paginator.PageInfoImpl;
import cn.xphsc.jpamapper.utils.Asserts;
import cn.xphsc.jpamapper.utils.Collects;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.query.JpaParameters;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.query.Parameter;
import org.springframework.data.repository.query.ParameterAccessor;
import org.springframework.data.repository.query.ParametersParameterAccessor;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.CollectionUtils;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.0
 */
public class DefaultNativeSQLQueryParser implements NativeSQLQueryParser  {

    @Override
     public Query bind(Query query, Object values) {
         SQLQuery sqlQuery=query.unwrap(SQLQuery.class);
         if(Optional.ofNullable(sqlQuery).isPresent()){
             if(Optional.ofNullable(values).isPresent()){
                 if(values instanceof Map){
                     Map params= (Map) values;
                     sqlQuery.setProperties(params);
                 } else if(values instanceof Object[]){
                     Object[] object= (Object[]) values;
                     for (int i = 0; i < object.length; i++) {
                         query.setParameter(i + 1, object[i]);
                     }
                 }
                 else{
                     checkParamType(values);
                     sqlQuery.setProperties(values);
                 }
             }
         }
        return query;
    }

    public <C> SQLQuery transform(SQLQuery query, Class<C> clazz) {
        if (Map.class.isAssignableFrom(clazz)) {
            return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).unwrap(SQLQuery.class);
        } else if (Number.class.isAssignableFrom(clazz) || clazz.isPrimitive() || String.class.isAssignableFrom(clazz) ||
                Date.class.isAssignableFrom(clazz)) {
            return query.setResultTransformer(new SmartTransformer(clazz)).unwrap(SQLQuery.class);
        }
        BeanTransformerAdapter beanTransformerAdapter = new BeanTransformerAdapter(clazz);
        beanTransformerAdapter.setPrimitivesDefaultedForNullValue(true);
        query= query.setResultTransformer(beanTransformerAdapter).unwrap(SQLQuery.class);
        return query;
    }

    @Override
    public Map<String, Object> setParams(Object[] values,JpaParameters parameters) {
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameters.getNumberOfParameters(); i++) {
            Object value = values[i];
            Parameter parameter = parameters.getParameter(i);
            if (value != null && canBindParameter(parameter)) {
                Class<?> clz = value.getClass();
                if (clz.isPrimitive() || String.class.isAssignableFrom(clz) || Number.class.isAssignableFrom(clz)
                        || clz.isArray() || Collection.class.isAssignableFrom(clz) || clz.isEnum() ||
                        Date.class.isAssignableFrom(clz) || java.sql.Date.class.isAssignableFrom(clz)) {
                    params.put(parameter.getName().get(), value);
                } else {
                    params = toParams(value);
                }
            }
        }
        return params;
    }
    private boolean canBindParameter(Parameter parameter) {
        return parameter.isBindable();

    }public static Map<String, Object> toParams(Object beanOrMap) {
        Map<String, Object> params;
        if (beanOrMap instanceof Map) {
            params = (Map<String, Object>) beanOrMap;
        } else {
            params = ResultMapper.beanOf(beanOrMap);
        }
        return params;
    }

    @Override
    public  String nativeSql(String sql,Method providerMethod,Class <?> type,Object[] parameters,JpaParameters jpaParameters){
        ParameterAccessor accessor = new ParametersParameterAccessor(jpaParameters, parameters);
        Map<String, Object> objectMap=setParams(parameters,jpaParameters);
        if(type!=null){
            if (!Collects.isEmpty(Arrays.asList(providerMethod.getParameterTypes()))) {
                Class<?> parameterType = providerMethod.getParameterTypes()[0];
                if (parameterType.isAssignableFrom(Map.class)) {
                    Map<String, Object> params =objectMap;
                    try {
                       sql = (String)providerMethod.invoke(type.newInstance(), params);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        sql = (String)providerMethod.invoke(type.newInstance(), parameters);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else {
                try {
                    sql = (String)providerMethod.invoke(type.newInstance());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        String sortedQueryString = QueryUtils
                .applySorting(sql, accessor.getSort());
        return sortedQueryString;
    }
    @Override
    public Query createNativeQuery(EntityManager entityManager,String queryString,Class<?> entityClass,boolean queryForEntity) {
        Query oriProxyQuery=null;
        if (queryForEntity) {
            oriProxyQuery = entityManager.createNativeQuery(queryString, entityClass);
        } else {
            oriProxyQuery = entityManager.createNativeQuery(queryString);

            SQLQuery query=oriProxyQuery.unwrap(SQLQuery.class);
            ClassTypeInformation<?> ctif = ClassTypeInformation.from(entityClass);
            TypeInformation<?> actualType = ctif.getActualType();
            if(actualType!=null){
                Class<?> genericType = actualType.getType();
                if (genericType != null && genericType != Void.class) {
                   transform(query, entityClass);
                }
            }

        }
        return oriProxyQuery;
    }
    @Override
    public  Object executeOf(EntityManager entityManager,String nativeSql,Object[] parameters,Class<?> entityClass){
         List<?> queryList;
         Query query=entityManager.createNativeQuery(nativeSql);
         long total=0L;
         SQLQuery sqlQuery= query.unwrap(SQLQuery.class);
         boolean pages=false;
         PageInfo pageInfoTarget = null;
        for(Object parameter:parameters){
            if(parameter instanceof PageInfo ){
                pages=true;
                PageInfo pageInfo= (PageInfo) parameter;
                int pageNum = pageInfo.getPageNum() >= 1 ? pageInfo.getPageNum() : (int) Math.ceil((double) (( pageInfo.getOffset() + pageInfo.getLimit() ) / pageInfo.getLimit() ));
                int  pageSize = pageInfo.getPageSize() >0 ? pageInfo.getPageSize() : pageInfo.getLimit();
                pageInfoTarget=PageInfo.of(pageNum,pageSize);
                query.setMaxResults(pageSize).setFirstResult((pageNum - 1) *pageSize);
                continue;
            }else{
                  checkPageInfoParamType(parameter);
                   Map<String, Object> params= toParams(parameter);
                   for(Map.Entry  param: params.entrySet()){
                       if(param!=null&&param.getValue()!=null){
                           if(param.getValue() instanceof PageInfo){
                               continue;
                           } else if("unsorted".equals(param.getKey())||"sorted".equals(param.getKey())){
                               continue;
                           }else{
                               if(param.getValue()!=null){
                                   sqlQuery.setParameter(param.getKey().toString(),param.getValue());

                               }

                           }
                       }

                   }
               CountExecutor<Object> executor=new CountExecutor(null,entityManager,nativeSql, parameter);
                total = (long) executor.execute();
            }
        }
         if(pages){
             if(entityClass.isAssignableFrom(Map.class)){
                 queryList = (List<?>) ResultMapper.setResultMap(sqlQuery);
             }else{
                 queryList = (List<?>) ResultMapper.setResultEntity(sqlQuery,entityClass);
             }
             return new PageInfoImpl<>(queryList,total,pageInfoTarget.getPageNum(),pageInfoTarget.getPageSize());
         }
         return null;
     }

    public String buildCountSql(String sql){
        String buildSql="";
        if(!sql.trim().toUpperCase().startsWith("SELECT COUNT")) {
            String countRexp = "(?i)^select (?:(?!select|from)[\\s\\S])*(\\(select (?:(?!from)[\\s\\S])* from [^\\)]*\\)(?:(?!select|from)[^\\(])*)*from";
            String replacement = "SELECT COUNT(1) AS COUNT FROM";
            buildSql = sql.replaceFirst(countRexp, replacement);
        } else {
            buildSql = sql;
        }
        SQLParser sqlParser=new DefaultSQLParser();
        if(sqlParser.hasOrders(buildSql)) {
            buildSql = sqlParser.removeOrders(buildSql);
        }
        return buildSql;
    }

    @Override
    public Object executeOf(EntityManager entityManager,String nativeSql,Object parameters) {
        SQLExecutor executor=new SQLExecutor(null,entityManager,nativeSql,parameters);
        int result = (int) executor.execute();
        return result;
    }

    public static boolean isValidValue(Object object) {
        if (object == null) {
            return false;
        }
        return !(object instanceof Collection && CollectionUtils.isEmpty((Collection<?>) object));
    }
    private void checkParamType(Object pojo){
        Asserts.isTrue(!vaildParamType(pojo),"parameters must be any JavaBean or POJO or Map or Object[], There must be no underlying Java underlying types value");
    }

    private void checkPageInfoParamType(Object pojo){
        Asserts.isTrue(!vaildParamType(pojo),"parameters must be any JavaBean or POJO or Map , There must be no underlying Java underlying types value");
    }
    private boolean  vaildParamType(Object pojo){
        boolean ckeckResult = false;
        if(pojo instanceof String){
            ckeckResult=true;
        }
        if(pojo instanceof Number){
            ckeckResult=true;
        }
        if(pojo instanceof Integer){
            ckeckResult=true;
        }
        if(pojo instanceof Long){
            ckeckResult=true;
        }
        if(pojo instanceof Character){
            ckeckResult=true;
        }
        if(pojo instanceof Short){
            ckeckResult=true;
        }
        if(pojo instanceof Byte){
            ckeckResult=true;
        }
        if(pojo instanceof Boolean){
            ckeckResult=true;
        }
        return ckeckResult;
    }

}
