package aop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimerAspect {
	//����һ���ļ�, ��һ����������
	private File file;
	/*�������ļ�config�л�ȡ logfile ����
    //���һ�������ļ� config.properties
    //�����ļ��а������� logfile
    //spring�ж�ȡ�����ļ�ΪBean config
	 * 
	 */
	@Value("#{config.logfile}")
	public void setFilename(String name){
		System.out.println(name);
	name.contains("fda");
		file=new File(name);
	}
	//��������
	private BlockingQueue<String> queue=new LinkedBlockingQueue<String>(500);
	@Around("bean(*Service)")
	public Object test(ProceedingJoinPoint joinPoint)throws Throwable{

		long t1=System.currentTimeMillis();
		//����ҵ�񷽷�
		Object obj=joinPoint.proceed();
		long t2=System.currentTimeMillis();
		//�����ʱ
		long t=t2-t1;
		Thread tx=Thread.currentThread();
		//	System.out.println(tx+" : "+t);
		//����ǩ��
		Signature s=joinPoint.getSignature();
		String str=tx+" : "+t+"��"+s;
		queue.offer(str);
		return obj;
	}
	//����AOP Bean����Ժ�ִ�еķ���
	@PostConstruct
	public void start(){
		//Bean���TimerAspect�Ժ����������߳�
		t.start();
	}
	private Thread t=new Thread(){
		public void run(){
			System.out.println("ok");
			try {
				//���ļ�д�����ݣ�׷��дģʽ��
				PrintWriter out =new PrintWriter(new FileOutputStream(file,true));
				while(true){
					//�Ӷ�����ȡ��һ�����ݣ����û��������ȵ�������Ϊֹ
					String s=queue.take();
					out.println(s);	
					while(!queue.isEmpty()){
						//д����
						s=queue.poll();
						System.out.println(s);
						out.println(s);	
					}
					//�ر��ļ�
					out.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};




}
