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
package cn.xphsc.jpamapper.core.parser;


import cn.xphsc.jpamapper.utils.Asserts;
import org.hibernate.SQLQuery;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class DefaultSQLQueryParser implements SQLQueryParser  {
    @Override
    public SQLQuery createSQLQuery(EntityManager em, String sqlString, Object pojo) {
        Query query=em.createNativeQuery(sqlString);
        SQLQuery sqlQuery=query.unwrap(SQLQuery.class);
        if(Optional.ofNullable(sqlQuery).isPresent()){
            if(Optional.ofNullable(pojo).isPresent()){
            if(pojo instanceof Map){
                Map params= (Map) pojo;
                sqlQuery.setProperties(params);
            } else if(pojo instanceof Object[]){
                Object[] object= (Object[]) pojo;
                for (int i = 0; i < object.length; i++) {
                    query.setParameter(i + 1, object[i]);
                }
            }
            else{
                checkParamType(pojo);
                sqlQuery.setProperties(pojo);
            }
        }
        }
        return sqlQuery;
    }

    private void checkParamType(Object pojo){
        Asserts.isTrue(!vaildParamType(pojo),"parameters must be any JavaBean or POJO or Map or Object[], There must be no underlying Java underlying types value");
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
