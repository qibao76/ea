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
	//管理一个文件, 和一个阻塞队列
	private File file;
	/*从配置文件config中获取 logfile 属性
    //添加一个配置文件 config.properties
    //配置文件中包含属性 logfile
    //spring中读取配置文件为Bean config
	 * 
	 */
	@Value("#{config.logfile}")
	public void setFilename(String name){
		System.out.println(name);
	name.contains("fda");
		file=new File(name);
	}
	//阻塞队列
	private BlockingQueue<String> queue=new LinkedBlockingQueue<String>(500);
	@Around("bean(*Service)")
	public Object test(ProceedingJoinPoint joinPoint)throws Throwable{

		long t1=System.currentTimeMillis();
		//调用业务方法
		Object obj=joinPoint.proceed();
		long t2=System.currentTimeMillis();
		//计算耗时
		long t=t2-t1;
		Thread tx=Thread.currentThread();
		//	System.out.println(tx+" : "+t);
		//方法签名
		Signature s=joinPoint.getSignature();
		String str=tx+" : "+t+"，"+s;
		queue.offer(str);
		return obj;
	}
	//创建AOP Bean组件以后执行的方法
	@PostConstruct
	public void start(){
		//Bean组件TimerAspect以后立即启动线程
		t.start();
	}
	private Thread t=new Thread(){
		public void run(){
			System.out.println("ok");
			try {
				//打开文件写入数据（追加写模式）
				PrintWriter out =new PrintWriter(new FileOutputStream(file,true));
				while(true){
					//从队列中取出一个数据，如果没有数据则等到有数据为止
					String s=queue.take();
					out.println(s);	
					while(!queue.isEmpty()){
						//写数据
						s=queue.poll();
						System.out.println(s);
						out.println(s);	
					}
					//关闭文件
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
