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


import cn.xphsc.jpamapper.core.jdbc.InsertSQL;
import org.springframework.data.jpa.repository.query.JpaParameters;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 2.0.0
 */
public interface NativeSQLQueryParser {

     Query bind(Query query, Object values);
     Map<String, Object> setParams(Object[] values, JpaParameters parameters);
   String nativeSql(String sql, Method providerMethod, Class <?> type, Object[] parameters, JpaParameters jpaParameters);
    Query createNativeQuery(EntityManager entityManager,String queryString,Class<?> entityClass,boolean queryForEntity);
    Object executeOf(EntityManager entityManager,String nativeSql,Object[] parameters,Class<?> entityClass);
    String buildCountSql(String sql);
    Object executeOf(EntityManager entityManager,String nativeSql,Object parameters);

}
