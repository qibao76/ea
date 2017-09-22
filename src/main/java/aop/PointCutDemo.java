package aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PointCutDemo {
	
	@Before("execution(* service.UserService.login(..))")
	//@Before("execution(* service.*Service.*(..))")
	//@Before("within(service.UserServiceImpl)")
	public void test1(){
		System.out.println("pointcut");
	}
	
}
