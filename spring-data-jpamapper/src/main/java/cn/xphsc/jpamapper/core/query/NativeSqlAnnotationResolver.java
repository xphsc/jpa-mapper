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
package cn.xphsc.jpamapper.core.query;

import cn.xphsc.jpamapper.core.annotation.*;

import java.lang.reflect.Method;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: NativeSql Annotation Resolver
 * @since 2.0.0
 */
public class NativeSqlAnnotationResolver {
    private String sql;
    private  Class<?> entityClass;
    private NativeSqlQuery nativeSqlQuery;

    private String providerMethodName;
    private Method providerMethod;
    private NativeSqlQueryProvider nativeSqlQueryProvider;
    private  Class <?> type;
    private Class<?> domainType;
    private  boolean nativeSqlAud;
    public NativeSqlAnnotationResolver(NativeSqlQuery nativeSqlQuery) {
        this.nativeSqlQuery = nativeSqlQuery;
    }

    public NativeSqlAnnotationResolver(Method method) {
        if (method.getAnnotation(NativeSqlQuery.class) != null) {
            nativeSqlQuery=method.getAnnotation(NativeSqlQuery.class);
            sql = nativeSqlQuery.value();
            entityClass=nativeSqlQuery.entityClass();
        }
        if (method.getAnnotation(NativeSqlQueryProvider.class) != null) {
            nativeSqlQueryProvider = method.getAnnotation(NativeSqlQueryProvider.class);
            providerMethodName = nativeSqlQueryProvider.method();
            type = nativeSqlQueryProvider.type();
            entityClass=nativeSqlQueryProvider.entityClass();
            if (this.type != null) {
                Method[] params = this.type.getMethods();
                int providerParams = params.length;
                for (int i = 0; i < providerParams; ++i) {
                    Method m = params[i];
                    if (this.providerMethodName.equals(m.getName()) && m.getReturnType() == String.class) {
                        if (this.providerMethod != null) {
                            throw new RuntimeException("Error creating Sql for SqlProvider. Method '" + this.providerMethodName + "' is found multiple in SqlProvider '" + this.type.getName() + "'. Sql provider method can not overload.");
                        }

                        this.providerMethod = m;
                    }
                }
            }
        }
    }
    public String sql() {
        return sql;
    }

    public Class<?> entityClass() {
        return entityClass;
    }

    public Class<?> type() {
        return type;
    }

    public Method providerMethod() {
        return providerMethod;
    }
    public String providerMethodName() {
        return providerMethodName;
    }

    public Class<?> domainType() {
        return domainType;
    }

    public boolean nativeSqlAud() {
        return nativeSqlAud;
    }

    private  NativeSqlAnnotationResolver(NativeSqlAnnotationResolver.Builder builder){
        this.nativeSqlQuery=builder.nativeSqlQuery;
        this.nativeSqlQueryProvider=builder.nativeSqlQueryProvider;
        this.sql=builder.sql;
        this.entityClass=builder.entityClass;
        this.providerMethod=builder.providerMethod;
        this.type=builder.type;
        this.providerMethodName=builder.providerMethodName;
        this.domainType=builder.domainType;
        this.nativeSqlAud=builder.nativeSqlAud;
    }

    public static NativeSqlAnnotationResolver.Builder builder(){
        return  new NativeSqlAnnotationResolver.Builder();
    }

    public static class Builder {
        public Builder(){}
        private NativeSqlQuery nativeSqlQuery;
        private NativeSqlQueryProvider nativeSqlQueryProvider;
        private String sql;

        private  Class<?> entityClass;
        private Class <?> type;

        private String providerMethodName;
        private Method providerMethod;
        private Class<?> domainType;
        private  boolean nativeSqlAud;
        public NativeSqlAnnotationResolver.Builder nativeSqlQuery(NativeSqlQuery nativeSqlQuery) {
            this.nativeSqlQuery = nativeSqlQuery;
            return this;
        }

        public NativeSqlAnnotationResolver.Builder nativeSqlQueryProvider(NativeSqlQueryProvider nativeSqlQueryProvider) {
            this.nativeSqlQueryProvider = nativeSqlQueryProvider;
            return this;
        }

        public NativeSqlAnnotationResolver.Builder method(Method method) {

            if (method.getAnnotation(NativeInsert.class) != null) {
                NativeInsert nativeSql= method.getAnnotation(NativeInsert.class);
                sql = nativeSql.value();
                nativeSqlAud=true;
            }

            if (method.getAnnotation(NativeInsertProvider.class) != null) {
                NativeInsertProvider nativeProvider = method.getAnnotation(NativeInsertProvider.class);
                providerMethodName = nativeProvider.method();
                type = nativeProvider.type();
                nativeSqlProvider();
                nativeSqlAud=true;
            }

            if (method.getAnnotation(NativeUpdate.class) != null) {
                NativeUpdate nativeSql = method.getAnnotation(NativeUpdate.class);
                sql = nativeSql.value();
                nativeSqlAud=true;
            }

            if (method.getAnnotation(NativeUpdateProvider.class) != null) {
                NativeUpdateProvider nativeProvider = method.getAnnotation(NativeUpdateProvider.class);
                providerMethodName = nativeProvider.method();
                type = nativeProvider.type();
                nativeSqlProvider();
                nativeSqlAud=true;
            }
            if (method.getAnnotation(NativeDelete.class) != null) {
                NativeDelete nativeSql = method.getAnnotation(NativeDelete.class);
                sql = nativeSql.value();
                nativeSqlAud=true;
            }

            if (method.getAnnotation(NativeDeleteProvider.class) != null) {
                NativeDeleteProvider nativeProvider = method.getAnnotation(NativeDeleteProvider.class);
                providerMethodName = nativeProvider.method();
                type = nativeProvider.type();
                nativeSqlProvider();
                nativeSqlAud=true;
            }

            if (method.getAnnotation(NativeSqlQuery.class) != null) {
                nativeSqlQuery = method.getAnnotation(NativeSqlQuery.class);
                sql = nativeSqlQuery.value();
                entityClass=nativeSqlQuery.entityClass();
            }
            if (method.getAnnotation(NativeSqlQueryProvider.class) != null) {
                nativeSqlQueryProvider = method.getAnnotation(NativeSqlQueryProvider.class);
                providerMethodName = nativeSqlQueryProvider.method();
                type = nativeSqlQueryProvider.type();
                entityClass=nativeSqlQueryProvider.entityClass();
                nativeSqlProvider();
            }
                return this;
            }

        private void  nativeSqlProvider(){
            if (this.type != null) {
                Method[] params = this.type.getMethods();
                int providerParams = params.length;
                for (int i = 0; i < providerParams; ++i) {
                    Method m = params[i];
                    if (this.providerMethodName.equals(m.getName()) && m.getReturnType() == String.class) {
                        if (this.providerMethod != null) {
                            throw new RuntimeException("Error creating Sql for SqlProvider. Method '" + this.providerMethodName + "' is found multiple in SqlProvider '" + this.type.getName() + "'. Sql provider method can not overload.");
                        }

                        this.providerMethod = m;
                    }
                }
            }
        }



        public NativeSqlAnnotationResolver.Builder domainType(Class<?> domainType) {
            this.domainType = domainType;
            return this;
        }
        public NativeSqlAnnotationResolver build() {
            return new NativeSqlAnnotationResolver(this);
        }
    }

}
