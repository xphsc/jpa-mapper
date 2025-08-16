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
package cn.xphsc.jpamapper.core.repository;


import cn.xphsc.jpamapper.core.criteria.*;
import cn.xphsc.jpamapper.core.jdbc.DeleteSQL;
import cn.xphsc.jpamapper.core.jdbc.InsertSQL;
import cn.xphsc.jpamapper.core.jdbc.SQLSelector;
import cn.xphsc.jpamapper.core.jdbc.UpdateSQL;
import cn.xphsc.jpamapper.core.paginator.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * {@link JpaRepository}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  JPA specific extension
 * @since 1.0.0
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {



    /**
     *  Criteria in the sense of Domain Driven Design.
     */
     List<T> findAll(Criteria criteria) ;

    /**
     *
     * @param criteria  Criteria in the sense of Domain Driven Design.
     * @return a reference to the entity with the given identifier.
     */
    T findOne(Criteria criteria);
    /**
     *  Criteria in the sense of Domain Driven Design.
     *   Sort option for queries. You have to provide at least a list of properties to sort for that must not include
     */
     List<T> findAll(Criteria criteria,Sort sort) ;

    /**
     *  Criteria in the sense of Domain Driven Design.
     *   Pageable for pagination information.
     */
     Page<T> findAll(Criteria criteria, Pageable pageable) ;


    /**
     *  CriteriaWrapper in the sense of Domain Driven Design for List.
     */
      <S> List<S>  findAll(CriteriaWrapper criteria);

    /**
     * @param criteriaWrapper  criteriaWrapper in the sense of Domain Driven Design.
     * a single entity matching the given {@link CriteriaWrapper} or {@link Optional#empty()} if none was found.
     */
    <S> Optional<S> findOne(CriteriaWrapper criteriaWrapper);

    /**
     *  CriteriaWrapper in the sense of Domain Driven Design for PageInfo.
     */
     <S>  PageInfo<S> findByPage(CriteriaWrapper criteria) ;

    /**
     *  Criteria in the sense of Domain Driven Design.
     */
     long count(Criteria criteria);

    /**
     *  Criteria in the sense of Domain Driven Design.
     */
     long count(CriteriaWrapper criteria);

    /**
     *  DeleteQuery in the sense of Domain Driven Design.
     */
    public int delete(DeleteQuery query);

    /**
     *  UpdateQuery in the sense of Domain Driven Design.
     */
     int update(UpdateQuery query);


    /**
     * sql  or SQL as a  SQL query string
     * parameters  any JavaBean or POJO or Map or Object[]
     * resultClass any JavaBean or POJO or Map
     */
     <S> List<S> findAll(String sql, Object parameters,Class<S> resultClass);
    /**
     * sql  or SQL as a  SQL query string
     * parameters  any JavaBean or POJO or Map or Object[]
     * resultClass any JavaBean or POJO or Map
     */
     <S> S findOne(String sql, Object parameters, Class<S> resultClass);
    /**
     * sql  or SQL as a  SQL query string
     * parameters  any JavaBean or POJO or Map or Object[]
     * Sort option for queries. You have to provide at least a list of properties to sort for that must not include
     * resultClass any JavaBean or POJO or Map
     */
     <S> List<S> findAll(String sql, Object parameters, Sort sort,Class<S> resultClass);
    /**
     *  SqlQuery  Build query
     *  You have to provide at least a list of properties.
     * entityClass any JavaBean or POJO or Map
     */
    <S> List<S> findAll(SqlQuery sqlQuery) ;
    /**
     * sql  or SQL as a  SQL query string
     * parameters  any JavaBean or POJO or Map or Object[]
     *  PageInfo for pagination information.
      * Sort option for queries. You have to provide at least a list of properties to sort for that must not include
     * resultClass any JavaBean or POJO or Map
     */
     <S> PageInfo<S> findByPage(String sql, Object parameters, PageInfo pageInfo,Class<S> resultClass) ;

    /**
     *  SqlQuery  Build query
     *  PageInfo for pagination information.
     * resultClass any JavaBean or POJO or Map
     */
     <S> PageInfo<S> findByPage(SqlQuery sqlQuery) ;


    /**
     * sql  or SQL as a  SQL query string
     * parameters  any JavaBean or POJO or Map or Object[]
     *  Pageable for pagination information.
     * resultClass any JavaBean or POJO or Map
     */
     <S> Page<S> findByPage(String sql, Object parameters, Pageable pageable,Class<S> resultClass) ;
    /**
     * sql  or SQL as a  SQL query string
     * parameters  any JavaBean or POJO or Map or Object[]
     */
     long count(String sql, Object parameters);
    /**
     * InsertSQL as a  SQL insert string
     */
     int insert(InsertSQL insertSQL);

    /**
     *  DeleteSQL as a  SQL delete string
     */
     int delete(DeleteSQL deleteSQL);

    /**
     * UpdateSQL as a  SQL update string
     */
     int update(UpdateSQL updateSQL);



    /**
     * sql  Query Builder
     */
     SQLSelector SQLSelector();

    /**
     *EntityManager
     */
      EntityManager entityManager();

}
