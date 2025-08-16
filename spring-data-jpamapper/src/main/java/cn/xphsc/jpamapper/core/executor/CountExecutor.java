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

import cn.xphsc.jpamapper.core.lambda.LambdaSupplier;
import cn.xphsc.jpamapper.core.parser.DefaultSQLParser;
import cn.xphsc.jpamapper.core.parser.DefaultSQLQueryParser;
import cn.xphsc.jpamapper.core.parser.SQLParser;
import cn.xphsc.jpamapper.core.parser.SQLQueryParser;
import org.hibernate.SQLQuery;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;
import java.math.BigInteger;

/**
 * {@link Executor}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Count Executor
 * @since 1.0.0
 */
public class CountExecutor<T> extends AbstractExecutor<Object>  {
    private String SQL;
    private  Object params;
    public CountExecutor(LambdaSupplier<SimpleJpaRepository> jpaRepository, EntityManager entityManager, String sql, Object params) {
        super(jpaRepository, entityManager);
        this.SQL=buildCountSql(sql);
        this.params=params;
    }
    @Override
    protected Object doExecute() {
        long  result=0L;
        SQLQueryParser sqlQueryParser=new DefaultSQLQueryParser();
            SQLQuery query= sqlQueryParser.createSQLQuery(em,SQL.toString(),params);
            if(query!=null){
                result=  ((BigInteger)query.uniqueResult()).longValue();
            }
        return  result;
    }
    private String buildCountSql(String sql){
        String buildSql="";
        if(!sql.trim().toUpperCase().startsWith("SELECT COUNT")) {
            String countRexp = "(?i)^select (?:(?!select|from)[\\s\\S])*(\\(select (?:(?!from)[\\s\\S])* from [^\\)]*\\)(?:(?!select|from)[^\\(])*)*from";
            String replacement = "SELECT COUNT(1) AS COUNT FROM";
            buildSql = sql.replaceFirst(countRexp, replacement);
        } else {
            buildSql = sql;
        }
        SQLParser sqlParser=new DefaultSQLParser();
        if(sqlParser.hasOrders(buildSql)) {
            buildSql = sqlParser.removeOrders(buildSql);
        }
        return buildSql;
    }
}
