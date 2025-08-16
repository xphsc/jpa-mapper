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
package cn.xphsc.jpamapper.core.repository;




import cn.xphsc.jpamapper.core.criteria.*;
import cn.xphsc.jpamapper.core.executor.*;
import cn.xphsc.jpamapper.core.jdbc.DeleteSQL;
import cn.xphsc.jpamapper.core.jdbc.InsertSQL;
import cn.xphsc.jpamapper.core.jdbc.SQLSelector;
import cn.xphsc.jpamapper.core.jdbc.UpdateSQL;
import cn.xphsc.jpamapper.core.paginator.PageInfo;
import cn.xphsc.jpamapper.utils.Asserts;
import cn.xphsc.jpamapper.utils.Collects;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


/**
 * {@link SimpleJpaRepository}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  JPA specific extension ,you a more sophisticated interface than the plain {@link EntityManager} .
 * Usage EnableJpaRepositories(basePackages=scan the package, repositoryBaseClass = SimpleRepository.class) or (basePackages=scan the package, repositoryFactoryBeanClass = DefaultRepositoryFactoryBean.class)   Annotation to enable JPA repositories.
 * @since 1.0.0
 */
@NoRepositoryBean
@Transactional
public class SimpleRepository <T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> ,JpaSpecificationExecutor<T> {

    protected BaseRepository<T,ID> repository;


    private SimpleJpaRepository <T, ID> simpleJpaRepository;
    @PersistenceContext
    protected final EntityManager em;

    protected  JpaEntityInformation entityInformation;
    protected Class<T> clazz;

    public SimpleRepository(final JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.entityInformation = entityInformation;
        this.clazz = entityInformation.getJavaType();
        simpleJpaRepository=new SimpleJpaRepository(entityInformation,entityManager);
    }



    public BaseRepository<T, ID> getRepository() {
        return repository;
    }

    public JpaRepository <T, ID> getJpaRepository() {
        return simpleJpaRepository;
    }


    @Override
    public List<T> findAll(Criteria criteria) {
        return this.findAll((Specification <T>)criteria);
    }

    @Override
    public T findOne(Criteria criteria) {
        return Collects.isNotEmpty(findAll((Specification<T>) criteria))?findAll((Specification<T>)criteria).get(0):null;
    }

    @Override
    public List<T> findAll(Criteria criteria,Sort sort) {
        List<T>  result=null;
        if(Optional.ofNullable(sort).isPresent()){
            result=  this.findAll((Specification <T>) criteria,sort);
        }else{
            result=  this.findAll((Specification <T>) criteria);
        }
        return result;
    }

    @Override
    public Page<T> findAll(Criteria criteria, Pageable pageable) {
        return this.findAll((Specification<T>) criteria, pageable);
    }



    @Override
    public <S> List<S> findAll(CriteriaWrapper criteria) {
        Asserts.isNull(criteria.queryProperty.getPageInfo(),"CriteriaWrapper pageInfo  must  be empty");
        FindBySqlCriteriaExecutor executor=new FindBySqlCriteriaExecutor(this::getSimpleJpaRepository,em,criteria);
        List<S> result = (List <S>) executor.execute();
        return result;
    }

    @Override
    public <S> Optional<S> findOne(CriteriaWrapper criteria) {
        Asserts.isNull(criteria.queryProperty.getPageInfo(),"CriteriaWrapper pageInfo  must  be empty");
        FindBySqlCriteriaExecutor executor=new FindBySqlCriteriaExecutor(this::getSimpleJpaRepository,em,criteria);
        List<S> result = (List <S>) executor.execute();
        return Collects.isNotEmpty(result)?Optional.ofNullable(result.get(0)):null;
    }


    @Override
    public <S> PageInfo<S> findByPage(CriteriaWrapper criteria) {
        Asserts.notNull(criteria.queryProperty.getPageInfo(),"CriteriaWrapper pageInfo or startPage must not be empty");
        FindBySqlCriteriaExecutor executor=new FindBySqlCriteriaExecutor(this::getSimpleJpaRepository,em,criteria);
        PageInfo<S> result = (PageInfo <S>) executor.execute();
        return result;
    }


    @Override
    public long count(Criteria criteria) {
        return this.count((Specification <T>) criteria);
    }

    @Override
    public long count(CriteriaWrapper criteria) {
        return this.count((Specification <T>) criteria);
    }

    @Override
    public int delete(DeleteQuery query) {
        DeleteExecutor executor=new DeleteExecutor(this::getSimpleJpaRepository,em,query,clazz);
        int result = (int) executor.execute();
        return result;
    }

    @Override
    public int update(UpdateQuery query) {
        UpdateExecutor executor=new UpdateExecutor(this::getSimpleJpaRepository,em,query,clazz);
        int result = (int) executor.execute();
        return result;
    }

    @Override
    public <S> List<S> findAll(String sql, Object params, Sort sort, Class<S> resultClass) {
        FindBySQLExecutor executor=new FindBySQLExecutor(this::getSimpleJpaRepository,em,resultClass==null?clazz:resultClass,sql,params,sort);
        List<S> result = (List <S>) executor.execute();
        executor = null;
        return result;
    }

    @Override
    public <S> List<S> findAll(SqlQuery sqlQuery) {
        Asserts.isNull(sqlQuery.pageInfo(),"pageInfo must  be empty");
        FindBySQLExecutor executor=new FindBySQLExecutor(this::getSimpleJpaRepository,em,clazz,sqlQuery);
        List<S> result = (List <S>) executor.execute();
        executor = null;
        return result;
    }


    @Override
    public <S> List<S> findAll(String sql, Object parameters, Class<S> resultClass) {
        FindBySQLExecutor executor=new FindBySQLExecutor(this::getSimpleJpaRepository,em,resultClass==null?clazz:resultClass,sql,parameters);
        List<S> result = (List <S>) executor.execute();
        executor = null;
        return result;
    }

    @Override
    public <S> S findOne(String sql, Object parameters, Class<S> resultClass) {
        return Collects.isNotEmpty(findAll(sql, parameters, resultClass))?findAll(sql, parameters, resultClass).get(0):null;
    }

    @Override
    public <S> PageInfo<S> findByPage(String sql, Object parameters, PageInfo pageInfo, Class<S> resultClass) {
        Asserts.notNull(pageInfo,"pageable must not be empty");
        FindBySQLExecutor executor=new FindBySQLExecutor(this::getSimpleJpaRepository,em,resultClass==null?clazz:resultClass,sql,parameters,pageInfo);
        PageInfo<S> result = (PageInfo <S>) executor.execute();
        executor = null;
        return result;
    }

    @Override
    public <S> PageInfo<S> findByPage(SqlQuery sqlQuery) {
        Asserts.notNull(sqlQuery.pageInfo(),"PageInfo must not be empty");
        FindBySQLExecutor executor=new FindBySQLExecutor(this::getSimpleJpaRepository,em,clazz,sqlQuery);
        PageInfo<S> result = (PageInfo <S>) executor.execute();
        executor = null;
        return result;
    }

    @Override
    public <S> Page<S> findByPage(String sql, Object parameters, Pageable pageable, Class<S> resultClass) {
        Asserts.notNull(pageable,"pageable must not be empty");
        FindBySQLExecutor executor=new FindBySQLExecutor(this::getSimpleJpaRepository,em,resultClass==null?clazz:resultClass,sql,parameters,pageable);
        Page<S> result = (Page <S>) executor.execute();
        executor = null;
        return result;
    }




    @Override
    public long count(String sql, Object parameters) {
        CountExecutor executor=new CountExecutor(this::getSimpleJpaRepository,em,sql,parameters);
        long result = (long) executor.execute();
        return result;
    }


    @Override
    public int insert(InsertSQL insertSQL) {
        SQLExecutor executor=new SQLExecutor(this::getSimpleJpaRepository,em,insertSQL);
        int result = (int) executor.execute();
        return result;
    }

    @Override
    public int delete(DeleteSQL deleteSQL) {
        SQLExecutor executor=new SQLExecutor(this::getSimpleJpaRepository,em,deleteSQL);
        int result = (int) executor.execute();
        return result;
    }

    @Override
    public int update(UpdateSQL updateSQL) {
        SQLExecutor executor=new SQLExecutor(this::getSimpleJpaRepository,em,updateSQL);
        int result = (int) executor.execute();
        return result;
    }

    @Override
    public SQLSelector SQLSelector() {
        return new SQLSelector(simpleJpaRepository,em,clazz);
    }

    @Override
    public EntityManager entityManager() {
        return this.em;
    }

    private SimpleJpaRepository<T, ID> getSimpleJpaRepository() {
        return simpleJpaRepository;
    }
}

