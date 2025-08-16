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

import cn.xphsc.jpamapper.core.paginator.PageInfo;
import cn.xphsc.jpamapper.utils.Collects;
import cn.xphsc.jpamapper.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public  abstract  class AbstractCriteria<T> implements Specification {
    private List<Criterion> criterions;
    /**
     * 倒序查询条件
     */
    private String orderByDESC;
    /**
     * 升序查询条件
     */
    private String orderByASC;
    /**
     * group查询条件
     */
    private String groupBy;

    private boolean distinct;

    private boolean   having;

    public  QueryProperty queryProperty;

    private Map<String,String> mappings;

    private PageInfo page;
    public AbstractCriteria(){
        criterions = new ArrayList<Criterion>();
        queryProperty=new QueryProperty();
    }


    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query,
                                 CriteriaBuilder builder) {
        if(StringUtils.isNotEmpty(orderByASC))
        {query.orderBy(builder.asc(root.get(orderByASC)));}
        if(StringUtils.isNotEmpty(orderByDESC)){
            query.orderBy(builder.desc(root.get(orderByDESC)));}
        if(StringUtils.isNotEmpty(groupBy)){
            query.groupBy(root.get(groupBy));}
         query.distinct(distinct);
        if (Collects.isNotEmpty(criterions)) {
            List<Predicate> predicates =new ArrayList <Predicate>();
            for(Criterion c : criterions){
                predicates.add(c.toPredicate(root, query,builder));
            }
            if (Collects.isNotEmpty(predicates)) {
                if(having){
                    query.having(predicates.toArray(new Predicate[predicates.size()]));
                }

                else{
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            }
        }
        return builder.conjunction();

    }




    public static class SimpleCriteria extends QueryCondition {
        protected SimpleCriteria() {
            super();
        }
    }


    public SimpleCriteria createCriteria() {
        SimpleCriteria criteria = new SimpleCriteria();
        criteria.setCriterions(criterions);
        return criteria;
    }

    protected SqlCriteria sqlCriteria(SqlCriteria criteria) {
        criterions=criteria.criterions;
        criteria.setCriterions(criterions);
        return criteria;
    }


    protected T groupBy(String groupBy) {
        this.groupBy=groupBy;
        return (T) this;
    }

    protected T orderByDESC(String orderByDESC) {
        this.orderByDESC=orderByDESC;
        return (T) this;
    }
    protected AbstractCriteria orderByASC(String orderByASC) {
        this.orderByASC=orderByASC;
        return this;
    }

    protected T distinct(boolean distinct) {
        this.distinct=distinct;
        return (T) this;
    }

    protected T having(boolean having) {
        this.having=having;
        return (T) this;
    }

    protected T entityClass(Class<?> entityClass){
        this.queryProperty.setEntityClass(entityClass);
        return (T) this;
    }


    protected T mapping(String property,String field){
        if(Collects.isEmpty(mappings)){
            mappings= new HashMap();
        }
        this.mappings.put(field, property);
        this.queryProperty.setMappings(mappings);
        return (T) this;
    }

    protected T mapping(Map<String,String> mappings){
        this.queryProperty.setMappings(mappings);
        return (T) this;
    }


    protected T sort(Sort sort) {
        queryProperty.setSort(sort);
        return (T) this;
    }

    protected T pageInfo(int pageNum, int pageSize) {
         if(page==null){
             page=new PageInfo();
         }
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        this.queryProperty.setPageInfo(page);
        return (T) this;
    }
    /**
     *  paging for query
     */
    protected T offsetPage(int offset, int limit) {
        if(page==null){
            page=new PageInfo();
        }
        page.setOffset(offset);
        page.setLimit(limit);
        this.queryProperty.setPageInfo(page);
        return (T) this;
    }

    protected void clear() {
        this.criterions.clear();
        if(mappings!=null){
            this.mappings.clear();
        }
    }
}
