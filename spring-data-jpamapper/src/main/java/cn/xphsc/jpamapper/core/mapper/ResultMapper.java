/*
 * Copyright (c) 2020 huipei.x
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
package cn.xphsc.jpamapper.core.mapper;


import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.cglib.beans.BeanMap;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Result conversion
 * @since 1.0.0
 */
public  final  class ResultMapper {

    public static List<Map<String,Object>> setResultMap(SQLQuery query){
        List<Map<String,Object>> queryList=query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return queryList;
    }


    public static  <S> List<S> setResultEntity(SQLQuery query,Class<S> resultClass){
        BeanTransformerAdapter beanTransformerAdapter= new BeanTransformerAdapter(resultClass);
        beanTransformerAdapter.setPrimitivesDefaultedForNullValue(true);
        List<S> queryList=query.setResultTransformer(beanTransformerAdapter).list();
        return queryList;
    }



    public static Map<String, Object> beanOf(Object bean) {
        Map<String,Object>  beanToMap= BeanMap.create((bean));
        return beanToMap;
    }

}
