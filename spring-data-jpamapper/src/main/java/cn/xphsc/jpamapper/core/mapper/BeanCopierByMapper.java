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


import cn.xphsc.jpamapper.utils.Collects;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.Converter;
import java.util.*;


/**
 * {@link BeanCopier}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Object Copy
 * @since 1.0.0
 */
public final class BeanCopierByMapper {


    public static void  copyByCopierMapper(Object origClass,Object target ){
        BeanCopier copier = BeanCopier.create(origClass.getClass(), target.getClass(), false);
        copier.copy(origClass,target,null);
    }

    public static void copyByCopierMapper(Object origClass,Object target,Converter converter){
        BeanCopier copier = BeanCopier.create(origClass.getClass(), target.getClass(), true);
        copier.copy(origClass,target,converter);
    }

    public static void  copyByCopierMapper(Object origClass,Object target,Map<String,String> map){
        if(Collects.isNotEmpty(map)){
            BeanMap beanMap= BeanMap.create(target);
            beanMap.setBean(target);
            Map<String,Object>  beanToMap= BeanMap.create((origClass));
            for(Map.Entry entity:map.entrySet()){
                for(Map.Entry beanEntity:beanToMap.entrySet()){
                    if(entity.getKey().equals(beanEntity.getKey())){
                        beanMap.put(entity.getValue().toString(),beanEntity.getValue());
                    }else{
                        beanMap.put(beanEntity.getKey(),beanEntity.getValue());

                    }
                }

            }
        }else{
            copyByCopierMapper(origClass,target);
        }



    }








}
