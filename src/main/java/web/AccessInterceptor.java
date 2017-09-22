package web;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import service.UserService;
@Component
public class AccessInterceptor implements HandlerInterceptor {

	@Resource
	private  UserService userService;
	public AccessInterceptor() {

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		 
		/*
		 * ��ȡcookie  userId  token
		 * ��userId��token����ҵ���Ƚ�
		 * ���ͨ������return true  ��������
		 * �����ͬ��return false ����response����
		 * һ������������Ϣ��JSON����
		 */
		// ��ȡcookie  userId  token
		Cookie[] cookies=request.getCookies();
		String userId=null;
		String token=null;
		if(cookies!=null){
			for (Cookie c : cookies) {
				if("userId".equals(c.getName())){
					userId=c.getValue();
				}
				if("token".equals(c.getName())){
					token=c.getValue();
				}
			}
		}
		String json="{\"state\":1,\"data\":null,\"message\":\"�����µ�¼\"}";
		if(userId==null||token==null){
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(json);
			response.sendRedirect("/1608note/log_in.html");
			return false;
		}
		//��userId��token����ҵ���Ƚ�
		//���ͨ������return true  ��������
		//�����ͬ��return false ����response����
		// һ������������Ϣ��JSON����
		if(userService.checkToken(userId, token)){
			//���ͨ����
			return true;
		}else{
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(json);
			response.sendRedirect(request.getContextPath()+"/log_in.html");
			return false;
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
