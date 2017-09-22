package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
//切面组建如果需要使用，必须在Spring配置文件配置
//aspect切面  横切面
@Component
@Aspect
public class AspectDemo {
	
	//切面切入位置为userService的所有方法之前 
	@Before("bean(userService)")
	public void hello(){
		System.out.println("hello world    1");
	}
	@AfterReturning("bean(userService)")
	public void test1(){
		System.out.println("方法正常结束     2");
	}
	@AfterThrowing("bean(userService)")
	public void test2(){
		System.out.println("方法意外结束      3" );
	}
	@After("bean(userService)")
	public void test3(){
		System.out.println("方法最终结束         4");
	}
	@Around("bean(userService)")
	public Object test4(ProceedingJoinPoint joinPoint)throws Throwable{
		//业务方法之前
		System.out.println("业务方法之前");
		//调用底层业务方法，包括login/regist等
		Object obj=joinPoint.proceed();
		//signature：签名 这里返回的是方法的签名
		Signature s=joinPoint.getSignature();
		//s是方法签名信息：包含方法名和参数类型
		System.out.println(s);
		//业务方法之后
		System.out.println("业务方法之后"+obj); 
		return obj;
	}
	
	
	
}
