package nss.common.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class ExcludeFilter extends ConfigurableSiteMeshFilter{

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		
		String uri = req.getRequestURI();
		
		if("/pdf/web/viewer.html".equals(uri)){
			filterChain.doFilter(servletRequest, servletResponse);
		}else{
			super.doFilter(servletRequest, servletResponse, filterChain);
		}
		
	}
	
	
	
}
