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
package cn.xphsc.jpamapper.core.parser;

import javax.persistence.Query;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class DefaultSQLParser implements SQLParser {
    private static String REGEX_HASORDERS="order\\s*by[\\w|\\W|\\s|\\S]*";
    private static String REGEX_REMOVEORDERS="order\\s*by[\\w|\\W|\\s|\\S]*";
    @Override
    public Boolean hasOrders(String hqlString) {
        Boolean flag = false;
        Pattern p = Pattern.compile(REGEX_HASORDERS, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hqlString);
        while (m.find()) {
            flag = true;
        }
        return flag;
    }


    @Override
    public String removeOrders(String hqlString) {
        Pattern p = Pattern.compile(REGEX_REMOVEORDERS, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hqlString);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    @Override
    public void setParameter(Query query, Object[] parameter) {
        if (Optional.ofNullable(parameter).isPresent()) {
            for (int i = 0; i < parameter.length; i++) {
                query.setParameter(i, parameter[i]);
            }
        }
    }

}
