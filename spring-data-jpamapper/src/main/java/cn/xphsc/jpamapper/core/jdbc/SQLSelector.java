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

package cn.xphsc.jpamapper.core.jdbc;

import cn.xphsc.jpamapper.core.executor.CountExecutor;
import cn.xphsc.jpamapper.core.executor.FindBySQLExecutor;
import cn.xphsc.jpamapper.core.paginator.PageInfo;
import cn.xphsc.jpamapper.utils.Asserts;
import cn.xphsc.jpamapper.utils.Collects;
import cn.xphsc.jpamapper.utils.StringUtils;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * {@link SQL}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: SQL Selector
 * @since 1.0.0
 */
public class SQLSelector {
    private SQLSelector getSelf(){
        return this;
    }

    private String sql;
    private Class<?> entityClass;
    private PageInfo<?> pageInfo;
    private Map<String,String> mappings ;
    private SimpleJpaRepository jpaRepository;
    private EntityManager entityManager;
    private  Class<?> clazz;
    private Object parameters;

    public SQLSelector (SimpleJpaRepository jpaRepository, EntityManager entityManager,Class<?> clazz){
        this.jpaRepository=jpaRepository;
        this.entityManager=entityManager;
        this.clazz=clazz;
    }
    /**
     * 实体类型
     * @param entityClass 实体类型
     */
    public SQLSelector entityClass(Class<?> entityClass){
        this.entityClass = entityClass;
        return getSelf();
    }


    public SQLSelector pageInfo(int pageNum,int pageSize){
        Asserts.isTrue(pageNum >= 1, "pageNum必须大于等于1");
        Asserts.isTrue(pageSize > 0, "pageSize必要大于0");
        if(pageInfo==null){
            pageInfo=new PageInfo();
        }
        this.pageInfo.setPageNum(pageNum);
        this.pageInfo.setPageSize(pageSize);
        return getSelf();
    }

    public SQLSelector offsetPage(int  offset,int limit){
        Asserts.isTrue(offset >= 0, "offset必须大于等于0");
        Asserts.isTrue(limit > 0, "pageSize必要大于0");
        if(pageInfo==null){
            pageInfo=new PageInfo();
        }
        this.pageInfo.setOffset(offset);
        this.pageInfo.setLimit(limit);
        return getSelf();
    }

    /**
     * parameters  any JavaBean or POJO or Map or Object[]{}
     */
    public SQLSelector parameters(Object parameters){
        this.parameters=parameters;
        return getSelf();
    }

    /**
     * 查询SQL
     * @param sql SQL语句
     */
    public SQLSelector sql(String sql){
        this.sql = sql;
        return getSelf();
    }




    /**
     * 获取单个实体
     */
    public <T> T get() {
        T entity = null;
        List<T> list= list();
        if(Collects.isNotEmpty(list)){
            entity = list.get(0);
        }
        return entity;
    }
    /**
     * 查询实体列表
     */

    public <T> List<T> list() {
        if(StringUtils.isBlank(this.sql)) {
            this.sql = this.sqlBuilder.toString();
        }
        Asserts.hasText(sql, "SQL语句不能为空");
        FindBySQLExecutor executor=new FindBySQLExecutor(this::getJpaRepository,entityManager,entityClass==null?clazz:entityClass,sql,mappings);
        List<T> result = (List <T>) executor.execute();
        return result;
    }


    public <T> PageInfo<T> page() {
        if(pageInfo==null){
            pageInfo=new PageInfo();
        }
        if(StringUtils.isBlank(this.sql)) {
            this.sql = this.sqlBuilder.toString();
        }
        Asserts.hasText(sql, "SQL语句不能为空");
        FindBySQLExecutor executor=new FindBySQLExecutor(this::getJpaRepository,entityManager,entityClass==null?clazz:entityClass,pageInfo,sql,mappings,parameters);
        PageInfo<T> result = (PageInfo <T>) executor.execute();
        return result;
    }

    /**
     * 查询实体数量
     * @return 条目数
     */
    public int  count()  {
        if(StringUtils.isBlank(this.sql)) {
            this.sql = this.sqlBuilder.toString();
        }
        Asserts.hasText(sql, "SQL语句不能为空");
        CountExecutor executor=new CountExecutor(this::getJpaRepository,entityManager,sql,null);
        int result = (int) executor.execute();
        return result;
    }

    // ================= 构建SQL
    private final SQL sqlBuilder = new SQL();

    public SQLSelector SELECT(String columns) {
        this.sqlBuilder.SELECT(columns);
        return getSelf();
    }

    public SQLSelector SELECT_DISTINCT(String columns) {
        this.sqlBuilder.SELECT_DISTINCT(columns);
        return getSelf();
    }

    public SQLSelector FROM(String table) {
        this.sqlBuilder.FROM(table);
        return getSelf();
    }

    public SQLSelector JOIN(String join) {
        this.sqlBuilder.JOIN(join);
        return getSelf();
    }

    public SQLSelector INNER_JOIN(String join) {
        this.sqlBuilder.INNER_JOIN(join);
        return getSelf();
    }

    public SQLSelector LEFT_OUTER_JOIN(String join) {
        this.sqlBuilder.LEFT_OUTER_JOIN(join);
        return getSelf();
    }

    public SQLSelector RIGHT_OUTER_JOIN(String join) {
        this.sqlBuilder.RIGHT_OUTER_JOIN(join);
        return getSelf();
    }

    public SQLSelector OUTER_JOIN(String join) {
        this.sqlBuilder.OUTER_JOIN(join);
        return getSelf();
    }

    public SQLSelector WHERE(String conditions) {
        this.sqlBuilder.WHERE(conditions);
        return getSelf();
    }

    public SQLSelector OR() {
        this.sqlBuilder.OR();
        return getSelf();
    }

    public SQLSelector AND() {
        this.sqlBuilder.AND();
        return getSelf();
    }

    public SQLSelector GROUP_BY(String columns) {
        this.sqlBuilder.GROUP_BY(columns);
        return getSelf();
    }

    public SQLSelector HAVING(String conditions) {
        this.sqlBuilder.HAVING(conditions);
        return getSelf();
    }

    public SQLSelector ORDER_BY(String columns) {
        this.sqlBuilder.ORDER_BY(columns);
        return getSelf();
    }

    private SimpleJpaRepository getJpaRepository() {
        return jpaRepository;
    }
}
