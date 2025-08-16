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
package cn.xphsc.jpamapper.core.annotation;

import cn.xphsc.jpamapper.core.query.NativeSqlRepositoryQuery;

import java.lang.annotation.*;
/**
 * {@link NativeSqlRepositoryQuery}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:  Insert  Provider SQL annotations
 * the given repository interface extension of org.springframework.data.repository.Repository.JpaRepository or cn.xphsc.jpamapper.core.repository.BaseRepository
 * @since 2.0.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface NativeInsertProvider {
    Class<?> type();

    String method();
}
