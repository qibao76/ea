package web;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import service.UserService;

public class AccessFilter implements Filter{

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) res;
		//getCookie方法返回客户端传送的所有cookie对象
		Cookie[] cookies=request.getCookies();
		String token=null;
		String userId=null;
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName()+"："+cookie.getValue());
				if("token".equals(cookie.getName())){
					token=cookie.getValue();
				}
				if("userId".equals(cookie.getName())){
					userId=cookie.getValue();
				}
			}
		}
		
		if(token==null){
			response.sendRedirect("log_in.html");
			return;
		}
		/* 
		 * 如果cookie中的token与数据库中的token不一致
		 * 则重定向到登陆页面
		 */
		UserService userService=ctx.getBean("userService",UserService.class);
		if(userService.checkToken(userId,token)){
			//执行后续的请求，也就是执行的edit.html
			chain.doFilter(request, response);
		}else{
			response.sendRedirect("log_in.html");
		};
		
	}
	private WebApplicationContext ctx;
	public void init(FilterConfig cfg) throws ServletException {
		//获取当前spring容器
		ctx=WebApplicationContextUtils.getWebApplicationContext(cfg.getServletContext());
	}



}
