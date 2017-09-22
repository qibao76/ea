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
//�����齨�����Ҫʹ�ã�������Spring�����ļ�����
//aspect����  ������
@Component
@Aspect
public class AspectDemo {
	
	//��������λ��ΪuserService�����з���֮ǰ 
	@Before("bean(userService)")
	public void hello(){
		System.out.println("hello world    1");
	}
	@AfterReturning("bean(userService)")
	public void test1(){
		System.out.println("������������     2");
	}
	@AfterThrowing("bean(userService)")
	public void test2(){
		System.out.println("�����������      3" );
	}
	@After("bean(userService)")
	public void test3(){
		System.out.println("�������ս���         4");
	}
	@Around("bean(userService)")
	public Object test4(ProceedingJoinPoint joinPoint)throws Throwable{
		//ҵ�񷽷�֮ǰ
		System.out.println("ҵ�񷽷�֮ǰ");
		//���õײ�ҵ�񷽷�������login/regist��
		Object obj=joinPoint.proceed();
		//signature��ǩ�� ���ﷵ�ص��Ƿ�����ǩ��
		Signature s=joinPoint.getSignature();
		//s�Ƿ���ǩ����Ϣ�������������Ͳ�������
		System.out.println(s);
		//ҵ�񷽷�֮��
		System.out.println("ҵ�񷽷�֮��"+obj); 
		return obj;
	}
	
	
	
}
