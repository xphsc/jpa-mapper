/*
 * Copyright (c) 2018-2019 huipei.x
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
package cn.xphsc.jpamapper.core.executor;




import cn.xphsc.jpamapper.core.criteria.SqlQuery;
import cn.xphsc.jpamapper.core.lambda.LambdaSupplier;
import cn.xphsc.jpamapper.core.mapper.DynamicEntityMapper;
import cn.xphsc.jpamapper.core.mapper.ResultMapper;
import cn.xphsc.jpamapper.core.paginator.PageInfo;
import cn.xphsc.jpamapper.core.paginator.PageInfoImpl;
import cn.xphsc.jpamapper.core.parser.DefaultSQLParser;
import cn.xphsc.jpamapper.core.parser.DefaultSQLQueryParser;
import cn.xphsc.jpamapper.core.parser.SQLParser;
import cn.xphsc.jpamapper.core.parser.SQLQueryParser;
import cn.xphsc.jpamapper.utils.Asserts;
import cn.xphsc.jpamapper.utils.Collects;
import org.hibernate.SQLQuery;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * {@link AbstractExecutor}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: FindBySQL Executor
 * @since 1.0.0
 */
public class FindBySQLExecutor<T> extends AbstractExecutor<Object> {
    private String SQL;
    private  Object parameters;
    private Sort sort;
    private Pageable pageable;
    private boolean resultMap;
    private Map<String,String> mappings ;
    private PageInfo<?> pageInfo;
    private Class<?> entityClass;
    private boolean pages;

    public FindBySQLExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager, Class<?> entityClass, String sql, Object parameters, Sort sort) {
        super(jpaRepository,entityManager);
        this.entityClass=entityClass;
        this.SQL=sql;
        this.parameters=parameters;
        this.sort=sort;
    }



    public FindBySQLExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager, Class<?> entityClass, String sql, Object parameters) {
        super(jpaRepository,entityManager);
        this.entityClass=entityClass;
        this.SQL=sql;
        this.parameters=parameters;
    }


    public FindBySQLExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager,Class<?> entityClass, String sql,Map<String,String> mappings) {
        super(jpaRepository,entityManager);
        this.entityClass=entityClass;
        this.SQL=sql;
        this.mappings=mappings;
    }

    public FindBySQLExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager, Class<?> entityClass, PageInfo<?> pageInfo, String sql,Map<String,String> mappings,Object parameters ) {
        super(jpaRepository,entityManager);
        this.entityClass=entityClass;
        this.SQL=sql;
        this.mappings=mappings;
        this.pageInfo=pageInfo;
        this.parameters=parameters;
        this.pages=true;
    }



    public FindBySQLExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager,Class<?> entityClass, String sql, Object parameters, Pageable pageable) {
        super(jpaRepository,entityManager);
        this.entityClass=entityClass;
        this.SQL=sql;
        this.parameters=parameters;
        this.pageable=pageable;
        this.sort=pageable.getSort();
        this.pages=true;
    }



    public FindBySQLExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager,Class<?> entityClass, String sql, Object parameters, PageInfo pageInfo) {
        super(jpaRepository,entityManager);
        this.entityClass=entityClass;
        this.SQL=sql;
        this.parameters=parameters;
        this.pageInfo=pageInfo;
        this.pages=true;
    }

    public FindBySQLExecutor(LambdaSupplier jpaRepository, EntityManager entityManager, Class entityClass, SqlQuery sqlQuery) {
        super(jpaRepository,entityManager);
        this.entityClass=sqlQuery.entityClass()==null?entityClass:sqlQuery.entityClass();
        this.SQL=sqlQuery.sql();
        this.parameters=sqlQuery.parameters();
        this.sort=sqlQuery.sort();
        this.pageInfo=sqlQuery.pageInfo();
        this.pages=Optional.ofNullable(sqlQuery.pageInfo()).isPresent()?true:false;
    }


    @Override
    protected T doExecute() {
        StringBuilder newSql;
        SQLQuery query = null;
        List<T> queryList= new ArrayList<T>();
        List<Object> result= new ArrayList<Object>();
        long total=0L;
        checkResultClassType();
        resultMap();
        SQLQueryParser sqlQueryParser=new DefaultSQLQueryParser();
      if (!Optional.ofNullable(sort).isPresent()) {
               query= sqlQueryParser.createSQLQuery(em, SQL, parameters);
               if(this.pages){
                   CountExecutor<T> executor=new CountExecutor(this::getJpaRepository,em,SQL, parameters);
                   total = (long) executor.execute();
               }
            } else {
               SQL=getRemoveSQL();
               newSql = new StringBuilder(SQL + getSortSql(sort));
               query= sqlQueryParser.createSQLQuery(em, newSql.toString(), parameters);

            }
        if(!Optional.ofNullable(pageable).isPresent()&&!Optional.ofNullable(pageInfo).isPresent()) {
                if(resultMap){
                    queryList = (List<T>) ResultMapper.setResultMap(query);
                }else{
                    queryList = (List<T>) ResultMapper.setResultEntity(query,entityClass);
                    if(Collects.isNotEmpty(mappings)){
                        DynamicEntityMapper dynamicEntityMapper = new DynamicEntityMapper();
                        result = (List <Object>) dynamicEntityMapper.rowForMapper(entityClass, mappings, queryList);
                    }
                }
                return Collects.isEmpty(result)?(T) queryList: (T) result;
            } else{
                int pageNum=0;
                int pageSize=0;
                if(Optional.ofNullable(entityClass).isPresent()){
                    if(Optional.ofNullable(this.sort).isPresent()){
                        SQL=getRemoveSQL();
                        newSql=new StringBuilder (SQL+ getSortSql(this.sort) );
                        query= sqlQueryParser.createSQLQuery(em, newSql.toString(), parameters);
                        if(this.pages){
                            CountExecutor<T> executor=new CountExecutor(this::getJpaRepository,em,SQL, parameters);
                            total = (long) executor.execute();
                        }
                    }
                    if(Optional.ofNullable(pageable).isPresent()&&!Optional.ofNullable(pageInfo).isPresent()){
                        if(pageable.getPageNumber()>=0&&pageable.getPageSize() !=-1){
                           int  offset=pageable.getPageNumber()* pageable.getPageSize();
                            query.setMaxResults(pageable.getPageSize()).setFirstResult(offset);
                        }
                    }
                    if(Optional.ofNullable(pageInfo).isPresent()&&!Optional.ofNullable(pageable).isPresent()) {
                        pageNum = pageInfo.getPageNum() >= 1 ? pageInfo.getPageNum() : (int) Math.ceil((double) ( ( pageInfo.getOffset() + pageInfo.getLimit() ) / pageInfo.getLimit() ));
                        pageSize = pageInfo.getPageSize() >0 ? pageInfo.getPageSize() : pageInfo.getLimit();
                        query.setMaxResults(pageSize).setFirstResult((pageNum - 1) *pageSize);

                    }
                }
                if(resultMap){
                    queryList = (List<T>) ResultMapper.setResultMap(query);
                }else{
                    queryList = (List<T>) ResultMapper.setResultEntity(query,entityClass);
                }

                return !Optional.ofNullable(pageInfo).isPresent()?(T) new PageImpl(queryList,pageable,total):(T)new PageInfoImpl(Collects.isEmpty(result)?queryList:result,total,pageNum,pageSize);

            }
        }









    private String getSortSql(Sort sort){
        if(!Optional.ofNullable(sort).isPresent()){
            return "";
        }
        Iterator<Sort.Order> iterator= sort.iterator();
        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()){
            Sort.Order order=iterator.next();
            builder.append(" ").append(order.getProperty()).append(" ").append(order.getDirection());
            if( iterator.hasNext() ){
                builder.append(",");
            }
        }
        if( builder.length()>0 ){
            return builder.insert(0," ORDER BY ").toString();
        }
        return builder.toString();
    }


        private String getRemoveSQL(){
            SQLParser sqlParser=new DefaultSQLParser();
            if(sqlParser.hasOrders(SQL)){
                SQL=sqlParser.removeOrders(SQL);
            }
            return SQL;
        }


       private boolean resultClassType(){
           boolean  checkResult = false;
           if(entityClass!=null) {
               if ("java.lang.Integer".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("boolean".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("java.lang.Boolean".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("byte".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("java.lang.Byte".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("char".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("java.lang.Character".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("int".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("java.lang.Integer".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("long".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("java.lang.Long".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("short".equals(entityClass.getName())) {
                   checkResult = true;
               }
               if ("java.lang.Short".equals(entityClass.getName())) {
                   checkResult = true;
               }
           }
           return checkResult;
       }

        private void checkResultClassType(){
              Asserts.isTrue(!resultClassType(),"ResultClass must be any JavaBean or POJO or Map, There must be no underlying Java underlying types");
        }

        private void resultMap(){
             if(entityClass!=null){
                 if("java.util.Map".equals(entityClass.getName())){
                     this.resultMap=true;
                 }
             }

        }

}
