/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.springframework.cloud.gateway.handler.predicate;

import org.springframework.tuple.Tuple;
import org.springframework.web.reactive.function.server.PublicDefaultServerRequest;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;

/**
 * @author Spencer Gibb
 */
public class QueryRequestPredicateFactory implements RequestPredicateFactory {

	@Override
	public RequestPredicate apply(Tuple args) {
		validate(1, args);
		String param = args.getString(0);

		if (args.size() < 2) {
			return req -> {
				//TODO: ServerRequest support for query params with no value
				PublicDefaultServerRequest request = (PublicDefaultServerRequest) req;
				return request.getExchange().getRequest().getQueryParams().containsKey(param);
			};
		}

		String regexp = args.getString(1);

		return RequestPredicates.queryParam(param, value -> value.matches(regexp));
	}
}
