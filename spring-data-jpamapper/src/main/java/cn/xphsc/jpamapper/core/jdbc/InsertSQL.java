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

/**
 * {@link SQL}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Insert SQL to build
 * @since 1.0.0
 */
public class InsertSQL {

    private  SQL insertSQL;

    public static InsertSQL build(){
        return new InsertSQL();
    }

    public InsertSQL INSERT_INTO(String tableName) {
        insertSQL=new SQL().INSERT_INTO(tableName);
        return this;
    }

    public InsertSQL VALUES(String columns, String values) {
        insertSQL.VALUES(columns,values);
        return this;
    }


    public InsertSQL INTO_COLUMNS(String... columns) {
        insertSQL.INTO_COLUMNS(columns);
        return this;
    }


    public InsertSQL INTO_VALUES(String... values) {
        insertSQL.INTO_VALUES(values);
        return this;
    }

    @Override
    public String toString() {
        return insertSQL.toString();
    }

}
