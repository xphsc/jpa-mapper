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
package cn.xphsc.jpamapper.core.repository.query;


import cn.xphsc.jpamapper.core.paginator.PageInfo;
import cn.xphsc.jpamapper.core.parser.DefaultNativeSQLQueryParser;
import cn.xphsc.jpamapper.core.parser.NativeSQLQueryParser;
import cn.xphsc.jpamapper.core.query.NativeSqlAnnotationResolver;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Method;
import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: NativeSql Repository Query specific extension of AbstractJpaQuery.
 * @since 2.0.0
 */
public class NativeSqlRepositoryQuery extends AbstractJpaQuery {

    private  EntityManager entityManager;
    private   String sql;
    private NativeSQLQueryParser nativeSQLQueryParser;
    private  Class<?> entityClass;
    private Method providerMethod;
    private  Class <?> type;
    private  Class <?>  domainType;
    public NativeSqlRepositoryQuery(JpaQueryMethod method, EntityManager em, NativeSqlAnnotationResolver annotationResolver) {
        super(method, em);
        this.entityManager=em;
        this.sql=annotationResolver.sql();
        providerMethod=annotationResolver.providerMethod();
        type=annotationResolver.type();
        if(annotationResolver.entityClass()!=void.class&&annotationResolver.entityClass()!=null){
            this.entityClass=annotationResolver.entityClass();
        }else{
            this.entityClass= getQueryMethod().getReturnedObjectType();
        }
        this.domainType=annotationResolver.domainType();
        nativeSQLQueryParser=new DefaultNativeSQLQueryParser();
    }



    @Override
    protected Query doCreateQuery(JpaParametersParameterAccessor jpaParametersParameterAccessor) {
        JpaParameters parameters = getQueryMethod().getParameters();
        Object[] objects =jpaParametersParameterAccessor.getValues();
        Map<String, Object> objectMap=nativeSQLQueryParser.setParams(objects,getQueryMethod().getParameters());
        String nativeSql= nativeSQLQueryParser.nativeSql(this.sql,providerMethod,type,objects,parameters);
        Query query = nativeSQLQueryParser.createNativeQuery(entityManager,nativeSql,entityClass,getQueryMethod().isQueryForEntity());
        if (parameters.hasPageableParameter()) {
            Pageable pageable = (Pageable) (objects[parameters.getPageableIndex()]);
            if (pageable != null) {
                int  offset=pageable.getPageNumber()* pageable.getPageSize();
                query.setMaxResults(pageable.getPageSize()).setFirstResult(offset);
            }
        }
        query=nativeSQLQueryParser.bind(query,objectMap);
        return query;
    }

    @Override
    protected Query doCreateCountQuery(JpaParametersParameterAccessor jpaParametersParameterAccessor) {
        Object[] objects =jpaParametersParameterAccessor.getValues();
        Query query = entityManager.createNativeQuery(nativeSQLQueryParser.buildCountSql(sql));
        Map<String, Object> objectMap=nativeSQLQueryParser.setParams(objects,getQueryMethod().getParameters());
        return nativeSQLQueryParser.bind(query, objectMap);
    }



    @Override
    public Object execute(Object[] parameters) {
        String nativeSql= nativeSQLQueryParser.nativeSql(this.sql,providerMethod,type,parameters,getQueryMethod().getParameters());
        if(entityClass.isAssignableFrom(PageInfo.class)){
            entityClass=domainType;
        }
        Object  execute= nativeSQLQueryParser.executeOf(entityManager, nativeSql, parameters, entityClass);
        if(execute!=null){
            return execute;
        }
       return super.execute(parameters);
    }


}
