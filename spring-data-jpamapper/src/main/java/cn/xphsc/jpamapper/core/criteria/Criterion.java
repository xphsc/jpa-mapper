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
package cn.xphsc.jpamapper.core.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public interface Criterion {
	public enum Operator {
		/**
		 *
		 */
		EQ,OR_EQ, NOT_EQUAL,OR_NOT_EQUAL, LIKE,OR_LIKE,NOT_LIKE,OR_NOT_LIKE, GT,OR_GT, LT,OR_LT, GTE,OR_GTE, LTE,OR_LTE, AND, OR, BETWEEN,OR_BETWEEN,NOT_BETWEEN,OR_NOT_BETWEEN, IS_NULL,OR_IS_NULL, IS_NOT_NULL,OR_IS_NOT_NULL, IS_EMPTY,OR_IS_EMPTY, IS_NOT_EMPTY,OR_IS_NOT_EMPTY,IN,NOT_IN,OR_IN,OR_NOT_IN,MAX
    }

	public enum LIKEMode {
		/**
		 *
		 */
		LEFT, RIGHT, ANYWHERE,DEFAULT;
	}  
	
	public enum Projection {
		/**
		 *
		 */
		MAX, MIN, AVG, LENGTH, SUM, COUNT
	}
	
    public Predicate toPredicate(Root <?> root, CriteriaQuery <?> query,
                                 CriteriaBuilder builder);


}
