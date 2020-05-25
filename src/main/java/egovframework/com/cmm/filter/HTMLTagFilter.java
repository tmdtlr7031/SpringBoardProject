/*
 * Copyright 2008-2009 MOPAS(MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION).
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
 */
package egovframework.com.cmm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class HTMLTagFilter implements Filter{

	/**
	 * XSS 방지
	 * 
	 * Wrpper를 쓰는 이유 : HttpServletRequest의 body(ServletInputStream의 내용)를 로깅을 위해
	 * 					: HttpServletRequest는 body의 내용을 한 번만 읽을 수 있는데 @Controller(Handler)에 요청이 들어오면서 body를 한 번 읽게 된다.
	 * 					: 그래서 Filter나 Interceptor에서는 body를 읽을 수 없게되어 IOException이 발생함
	 * 					: 따라서 body를 로깅하기 위해서는 HttpServletRequest를 감싸서 여러 번 inputStream을 열 수 있도록 커스터마이징 된 ServletRequest를 쓸 수밖에 없다.
	 * 					: 출처 https://meetup.toast.com/posts/151
	 * */
	@SuppressWarnings("unused")
	private FilterConfig config;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		chain.doFilter(new HTMLTagFilterRequestWrapper((HttpServletRequest)request), response);
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {

	}
}
