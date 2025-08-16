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


import cn.xphsc.jpamapper.core.jdbc.DeleteSQL;
import cn.xphsc.jpamapper.core.jdbc.InsertSQL;
import cn.xphsc.jpamapper.core.jdbc.UpdateSQL;
import cn.xphsc.jpamapper.core.lambda.LambdaSupplier;
import cn.xphsc.jpamapper.core.parser.DefaultSQLQueryParser;
import cn.xphsc.jpamapper.core.parser.SQLQueryParser;
import org.hibernate.SQLQuery;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;

/**
 * {@link AbstractExecutor}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: SQL Executor
 * @since 1.0.0
 */
public class SQLExecutor<T> extends AbstractExecutor<Integer> {
    private String SQL;
    private  Object params;

    public SQLExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager, DeleteSQL deleteSQL) {
        super(jpaRepository,entityManager);
        this.SQL=deleteSQL.toString();
        this.params=deleteSQL.getParameters();
    }
    public SQLExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager, InsertSQL insertSQL) {
        super(jpaRepository,entityManager);
        this.SQL=insertSQL.toString();
    }

    public SQLExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager,  UpdateSQL updateSQL) {
        super(jpaRepository,entityManager);
        this.SQL=updateSQL.toString();
        this.params=updateSQL.getParameters();
    }
    public SQLExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager, String nativeSql,Object parameters) {
        super(jpaRepository,entityManager);
        this.SQL=nativeSql.toString();
        this.params=parameters;
    }
    @Override
    protected Integer doExecute() {
        SQLQueryParser sqlQueryParser=new DefaultSQLQueryParser();
        SQLQuery query= sqlQueryParser.createSQLQuery(em, SQL, params);
       return query.executeUpdate();

    }




}
