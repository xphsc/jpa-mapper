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
 * @description: Delete SQL build
 * @since 1.0.0
 */
public class DeleteSQL {

   private  SQL deleteSQL;

    private Object parameters;
    public static DeleteSQL build(){
        return new DeleteSQL();
    }

    public DeleteSQL DELETE_FROM(String table) {
        deleteSQL=new SQL().DELETE_FROM(table);
        return this;
    }

    public DeleteSQL WHERE(String conditions) {
        deleteSQL.WHERE(conditions);
        return this;
    }

    public DeleteSQL WHERE(String... conditions) {
        deleteSQL.WHERE(conditions);
        return this;
    }
    /**
     * parameters  any JavaBean or POJO or Map or Object[]{}
     */
    public DeleteSQL parameters(Object parameters){
        this.parameters=parameters;
        return this;
    }

    public Object getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return deleteSQL.toString();
    }


}
