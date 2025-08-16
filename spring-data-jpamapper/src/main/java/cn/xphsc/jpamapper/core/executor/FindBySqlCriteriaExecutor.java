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
package cn.xphsc.jpamapper.core.executor;

import cn.xphsc.jpamapper.core.criteria.CriteriaWrapper;
import cn.xphsc.jpamapper.core.criteria.QueryProperty;
import cn.xphsc.jpamapper.core.lambda.LambdaSupplier;
import cn.xphsc.jpamapper.core.mapper.DynamicEntityMapper;
import cn.xphsc.jpamapper.core.paginator.PageInfo;
import cn.xphsc.jpamapper.core.paginator.PageInfoImpl;
import cn.xphsc.jpamapper.utils.Collects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@link AbstractExecutor}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: FindByCriteriaQueryExecutor
 * @since 1.0.0
 */
public class FindBySqlCriteriaExecutor<T> extends AbstractExecutor<Object>  {
    private CriteriaWrapper criteria;
    private PageInfo pageInfo;
    private QueryProperty queryProperty;

    public FindBySqlCriteriaExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager, CriteriaWrapper criteria) {
        super(jpaRepository, entityManager);
        this.criteria=criteria;
    }

    @Override
    protected Object doExecute() {
        List<T> list=null;
        List <Object> result = new ArrayList <>(10);
        Pageable pageable = null;
        int pageNum=0;
        int pageSize=0;
        long total = 0L;
        Page<? extends T> page = null;
        int pageNumber=0;
        if(Optional.ofNullable(criteria).isPresent()) {
            if(Optional.ofNullable(criteria.queryProperty).isPresent()){
                queryProperty=criteria.queryProperty;
                if (Optional.ofNullable(queryProperty.getSort()).isPresent()&&!Optional.ofNullable(queryProperty.getPageInfo()).isPresent()) {
                        list = this.jpaRepository.findAll((Specification <T>) criteria, criteria.queryProperty.getSort());
                }else if(!Optional.ofNullable(queryProperty.getPageInfo()).isPresent()){
                    list = this.jpaRepository.findAll((Specification <T>) criteria);
                }
                if (criteria.queryProperty.getPageInfo() != null) {
                    pageInfo=criteria.queryProperty.getPageInfo();
                    if(pageInfo.getPageNum()>=1&&pageInfo.getPageSize()>0){
                        int offset = (pageInfo.getPageNum() * pageInfo.getPageSize())-pageInfo.getPageSize();
                        pageNum=pageInfo.getPageNum();
                        pageSize=pageInfo.getPageSize();
                        pageNumber=(int)Math.ceil((double)(offset/pageInfo.getPageSize()));
                    }else{
                        pageNumber=(int)Math.ceil((double)(pageInfo.getOffset()/ pageInfo.getLimit()));
                        pageNum =(int)Math.ceil((double)(pageInfo.getOffset()+pageInfo.getLimit())/pageInfo.getLimit());
                        pageSize=pageInfo.getLimit();
                    }
                    if (Optional.ofNullable(criteria.queryProperty.getSort()).isPresent()) {
                        pageable= PageRequest.of(pageNumber,pageSize,criteria.queryProperty.getSort());
                    }else{
                        pageable= PageRequest.of(pageNumber,pageSize);
                    }
                    page = this.jpaRepository.findAll((Specification <T>) criteria, pageable);
                }
                if(page!=null){
                    list= (List <T>) page.getContent();
                    total=page.getTotalElements();
                }
                Map <String, String> mappings = null;
                if (criteria.queryProperty.getEntityClass() != null) {
                    DynamicEntityMapper dynamicEntityMapper = new DynamicEntityMapper();
                    if (Collects.isNotEmpty(criteria.queryProperty.getMappings())) {
                        mappings = criteria.queryProperty.getMappings();
                    }
                    result = (List <Object>) dynamicEntityMapper.rowForMapper(criteria.queryProperty.getEntityClass(), mappings, list);
                } else {
                    result = (List <Object>) list;
                }
                return pageable !=null?new PageInfoImpl<>(result,total,pageNum,pageSize):result;
            }


        }
        return null;
    }

}
